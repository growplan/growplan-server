package com.growplan.chatgpt.dto.response;

import com.growplan.chatgpt.dto.request.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatGPTResponse {

    private List<Choice> choices;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Choice {
        private int index;
        private Message message;

    }
}