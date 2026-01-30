package com.Assistant.email.Service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${azure.openai.key}")
    private String apiKey;

    @Value("${azure.openai.endpoint}")
    private String endpoint;

    @Value("${azure.openai.deployment}")
    private String deployment;

    @Value("${azure.openai.api-version}")
    private String apiVersion;

    RestTemplate restTemplate=new RestTemplate();
    HttpHeaders headers=new HttpHeaders();

    private final ObjectMapper objectMapper=new ObjectMapper();

    public String generateReply(String emailContent, String tone){

        String url = endpoint + "/openai/deployments/" + deployment +
                "/chat/completions?api-version=" + apiVersion;

        Map<String,Object> systemInstruction= Map.of(
                "role","system",
                "content","You are an AI email assistant. Write professional email replies."

        );

        Map<String, Object> userMsg=Map.of(
                "role","user",

                "content","Reply to the following email  in a "+tone+ "tone:\n\n"+emailContent
        );

        Map<String , Object> body=new HashMap<>();
        body.put("messages", List.of(systemInstruction,userMsg));
        body.put("temperature",0.7);

        //HttpHeaders headers = new HttpHeaders(MediaType.APPLICATION_JSON,set);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key",apiKey);

        HttpEntity<Map<String,Object>>entity=new HttpEntity<>(body,headers);

        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

        JsonNode jsonNode=objectMapper.readTree(response.getBody());

        return jsonNode.get("choices").get(0).get("message").get("content").asString();




    }

}

