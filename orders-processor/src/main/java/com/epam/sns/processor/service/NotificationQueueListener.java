package com.epam.sns.processor.service;

import com.epam.sns.processor.dto.MessageSqs;
import com.epam.sns.processor.dto.OrderDto;
import com.epam.sns.processor.processor.OrderProcessor;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationQueueListener {

    @Value("${sqs.countable.url}")
    private String urlQueueCountable;

    @Value("${sqs.liquids.url}")
    private String urlQueueLiquids;

    @Autowired
    private final NotificationService notificationService;

    @Autowired
    private final OrderProcessor orderProcessor;

    @Scheduled(fixedRate = 3000)
    public void readBatchFromCountableQueue() {
        var messages = notificationService.readMessagesFromQueue(urlQueueCountable);
        messages.stream().forEach(s->{
            MessageSqs message = new Gson().fromJson(s.getBody(), MessageSqs.class);
            OrderDto orderDto = new Gson().fromJson(message.getMessage(), OrderDto.class);
            orderProcessor.processCountableOrder(orderDto);
            notificationService.sendMessageToTopic(orderDto);
        });
    }
    @Scheduled(fixedRate = 3000)
    public void readBatchLiquidsQueue() {
        var messages = notificationService.readMessagesFromQueue(urlQueueLiquids);
        messages.stream().forEach(s->{
            MessageSqs message = new Gson().fromJson(s.getBody(), MessageSqs.class);
            OrderDto orderDto = new Gson().fromJson(message.getMessage(), OrderDto.class);
            orderProcessor.processLiquidsOrder(orderDto);
            notificationService.sendMessageToTopic(orderDto);
        });
    }
}

