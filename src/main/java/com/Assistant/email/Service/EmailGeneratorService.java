package com.Assistant.email.Service;


import com.Assistant.email.DTO.EmailResponse;
import com.Assistant.email.DTO.RequestEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailGeneratorService {

    private final OpenAIService openAIService;


    public String generateEmailReply(RequestEmail requestEmail){
        String reply=openAIService.generateReply(
                requestEmail.getEmailContent(),
                requestEmail.getTone()
        );

        return reply;


    }

}
