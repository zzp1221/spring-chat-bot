package com.example.springchatbot.entity;

import lombok.Data;
import org.springframework.ai.chat.messages.Message;

@Data
public class messageVO {
    String role;
    String content;
    public messageVO(Message message) {
        switch (message.getMessageType()){
            case USER -> {this.role = "user";break;}
            case ASSISTANT -> {this.role = "assistant";break;}
            case SYSTEM -> {this.role = "system";break;}
            case TOOL -> {this.role = "tool";break;}
        }
        this.content = message.getText();
    }
}
