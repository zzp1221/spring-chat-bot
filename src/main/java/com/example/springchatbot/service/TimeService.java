package com.example.springchatbot.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeService {
    @Tool(description = "Get the current date and time in the user's timezone")
    public String getLocalTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }
}
