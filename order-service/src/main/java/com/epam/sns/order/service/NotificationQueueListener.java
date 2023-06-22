package com.epam.sns.order.service;

import com.epam.sns.order.dto.MessageSqs;
import com.epam.sns.order.dto.OrderDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationQueueListener {

    private final NotificationService notificationService;
    private final OrderService orderService;

    @Scheduled(fixedRate = 3000)
    public void readBatchFromQueue() {
        var messages = notificationService.readMessagesFromQueue();

        messages.stream().forEach(s->{
            MessageSqs message = new Gson().fromJson(s.getBody(), MessageSqs.class);
            OrderDto orderDto = new Gson().fromJson(message.getMessage(), OrderDto.class);
            orderService.update(orderDto);
        });
    }
}

