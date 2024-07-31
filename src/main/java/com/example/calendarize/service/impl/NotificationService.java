package com.example.calendarize.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    public void sendNotification(Long userId ,String message){
        messagingTemplate.convertAndSend("/topic/notifications/"+userId.toString() ,message);

    }

}
