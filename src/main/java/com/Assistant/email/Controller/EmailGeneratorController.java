package com.Assistant.email.Controller;


import com.Assistant.email.Service.EmailGeneratorService;
import com.Assistant.email.DTO.RequestEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")

@Data
public class EmailGeneratorController {
   private final EmailGeneratorService emailGeneratorService;




   @PostMapping("/generate")
   public ResponseEntity<String> generateEmail(@RequestBody RequestEmail requestEmail){
       String response=emailGeneratorService.generateEmailReply(requestEmail);
       return ResponseEntity.ok(response);
   }






}
