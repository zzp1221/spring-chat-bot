package com.example.springchatbot.repository;

import com.example.springchatbot.entity.ChatInfo;

import java.util.List;

public interface MemoryRepository {
    void save(String chatId, String title);

    void clearByChatId(String chatId);

    List<ChatInfo> getChats();
}
