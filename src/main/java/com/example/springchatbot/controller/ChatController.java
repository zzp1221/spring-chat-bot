package com.example.springchatbot.controller;

import com.example.springchatbot.entity.ChatInfo;
import com.example.springchatbot.entity.messageVO;
import com.example.springchatbot.repository.ChatHistory;
import com.example.springchatbot.service.TimeService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private  ChatHistory chatHistory;

    @Autowired
    private ChatMemory chatMemory;

    private ChatClient chatClient;
    public ChatController(ChatClient ollamaChatClient)  {
        this.chatClient = ollamaChatClient;
    }

    @RequestMapping(value = "/stream",produces ="text/html;charset = utf-8" )
    public Flux<String> chat(String prompt,String chatId) {
        chatHistory.save(chatId, prompt);
        return this.chatClient.prompt()
                .tools(new TimeService())
                .user(prompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }

    @RequestMapping("/getChatIds")
    public List<ChatInfo> chatInfos(){
        return chatHistory.getChats();
    }

    @RequestMapping("/getChatHistory")
    public List<messageVO> getChatHistory(String chatId){
        List<Message> messages = chatMemory.get(chatId);
        return messages.stream().map(messageVO::new ).collect(Collectors.toList());
    }
    @RequestMapping("/deleteChat")
    public Boolean deleteChat(String chatId){
        try {
            chatHistory.clearByChatId(chatId);
            chatMemory.clear(chatId);
        }catch (Exception e){
            return false;
        }

        return true;
    }
}

























