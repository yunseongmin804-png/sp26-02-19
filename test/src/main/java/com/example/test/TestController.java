package com.example.test;

import org.springframework.beans.factory.annotation.Autowired; // 추가됨
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestController {

    // 1. EmailUtil을 주입받아야 메서드 내부에서 사용할 수 있습니다.
    @Autowired
    private EmailUtil emailUtil;

    @GetMapping("/email")
    public String hello() {
        return "email-form";
    }
    
    // 2. 일반 이메일 전송 처리
    @PostMapping("/send-email")
    public String sendEmail(@RequestParam(value="to") String to,
                            @RequestParam(value="subject") String subject,
                            @RequestParam(value="content") String content,
                            RedirectAttributes redirectAttributes) {
        
        emailUtil.sendTextEmail(to, subject, content);
        
        // 성공 메시지를 담아서 보낼 수 있습니다.
        redirectAttributes.addFlashAttribute("msg", "이메일 전송 성공!");
        return "redirect:/email";
    }
    
    // 3. 웰컴 이메일 전송 처리 (내용 구성 추가)
    @PostMapping("/send-welcome-email")
    public String sendWelcomeEmail(@RequestParam(value="userEmail") String userEmail,
                                   @RequestParam(value="userName") String userName,
                                   RedirectAttributes redirectAttributes) {
        
        String subject = "환영합니다, " + userName + "님!";
        String content = userName + "님의 가입을 진심으로 축하드립니다.";
        
        emailUtil.sendTextEmail(userEmail, subject, content);
        
        redirectAttributes.addFlashAttribute("msg", "웰컴 메일 전송 완료!");
        return "redirect:/email";
    }
}