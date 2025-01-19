package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {
    private final OpenAiChatModel openAiChatModel;
    private final OpenAiImageModel openAiImageApi;

    public void chatbot(Request request){

        PromptTemplate promptTemplate = new PromptTemplate(
                "당신은 의사야 그래서 환자의 증상을 바탕으로 환자의 병을 예측해줘\n"
                        + "환자가 자신의 증상을 말하면 너의 지식으로 최대한 친절하게 알려워.\n"
                        + "환자 : 배가 너무 아파요\n"
                        + "답변 : 배탈인 것 같습니다.\n"
                        + "환자 : 머리가 너무 아파요\n"
                        + "답변 : 두통인 것 같습니다.\n"
                        + "환자 : 기침을 해요\n"
                        + "답변 : 감기인 것 같습니다.\n"
                + "이런 형식으로 대답해줘."
                        +"답변: 다리 통증은 여러 가지 원인에 의해 발생할 수 있습니다. 근육통일 가능성이 있습니다. 증상이 지속되거나 악화되면 의료 전문가와 상담하는 것이 좋습니다. 이런식으로는 답변하지 말고 내가 앞서 말한 예시대로 짧게 말해줘\n"
                        +"(답변: ) 과 같은 맥락은 뺴줘."
                + "환자 : {message}\n"
        );

        Prompt prompt = promptTemplate.create(Map.of("message", request.text())); // request 가 recode가 아닌 경우 getText() 사용
        System.out.println(openAiChatModel.call(prompt).getResult().getOutput().getContent());

        //openAiChatModel.call(request.text());
    }

//    public void imageAi(Request request){
//        String prompt = "사진을 보고 뭔지 판단해줘";
//
//        UserMessage userMessage = new UserMessage(prompt, Collections.singletonList(new Media(MediaType.IMAGE_JPEG, request.text())));
//        System.out.println(openAiChatModel.call(userMessage));
//    }

    public void imageGenAi(Request request){
        String prompt = "사용자가 글을 입력하면 너는 최상의 퀄리티로 그림을 그려줘\n"
                +"그림은 꼭 만화그림처럼 그려야해.\n"
                +"그림에 사람이 꼭 2명은 있어야해.\n"
                +request.text();

        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withN(1)
                .withHeight(1024)
                .withWidth(1024)
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);

        String url = openAiImageApi.call(imagePrompt).getResult().getOutput().getUrl();

        System.out.println(url);
    }
}
