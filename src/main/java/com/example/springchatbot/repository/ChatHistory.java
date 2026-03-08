package com.example.springchatbot.repository;

import com.example.springchatbot.entity.ChatInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Repository
public class ChatHistory implements MemoryRepository{
    private Map<String,String> chatHistory = new LinkedHashMap<>();

    @Override
    public void save(String chatId, String title) {
        chatHistory.put(chatId,title);
    }

    @Override
    public List<ChatInfo> getChats() {
        return chatHistory.entrySet().stream().map(entry -> new ChatInfo(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    @Override
    public void clearByChatId(String chatId) {
        chatHistory.remove(chatId);
    }
}
