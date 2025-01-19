package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai")
@RequiredArgsConstructor
public class AiController {
    //private final OpenAiChatModel openAiChatModel;
    private final AiService aiService;

//    @PostMapping()
//    void test(@RequestBody Request request){
//        System.out.println(openAiChatModel.call(request.text()));
//    }

    @PostMapping()
    void chatbot(@RequestBody Request request){
        aiService.chatbot(request);
    }

//    @PostMapping("/image")
//    void imageAI(@RequestBody Request request){
//        aiService.imageAi(request);
//    }

    @PostMapping("/gen")
    void imageGenAi(@RequestBody Request request){
        aiService.imageGenAi(request);
    }
}


