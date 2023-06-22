package com.epam.sns.order.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.AmazonSNSException;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.epam.sns.order.dto.OrderDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Value("${sqs.queueUrl}")
    private String queueUrl;

    @Value("${sns.topic-arn}")
    private String snsTopicArn;

    @Autowired
    private final AmazonSNS snsClient;
    @Autowired
    private final AmazonSQS sqsClient;

    public void sendMessageToTopic(OrderDto orderDto) {
        try {
            MessageAttributeValue orderType = new MessageAttributeValue();
            orderType.setStringValue(orderDto.getType().toString());
            final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
            messageAttributes.put("goods_type", new MessageAttributeValue()
                    .withDataType("String")
                    .withStringValue(orderType.getStringValue()));
            String message = new Gson().toJson(orderDto);
            var publishRequest = new PublishRequest()
                    .withMessage(message)
                    .withMessageAttributes(messageAttributes)
                    .withTopicArn(snsTopicArn);
            snsClient.publish(publishRequest);
        } catch (AmazonSNSException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    public List<Message> readMessagesFromQueue() {
        try {
            var request = new ReceiveMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withWaitTimeSeconds(10)
                    .withMaxNumberOfMessages(10);
            var messages = sqsClient.receiveMessage(request).getMessages();
            messages.stream()
                    .map(Message::getReceiptHandle)
                    .forEach(receipt -> sqsClient.deleteMessage(queueUrl, receipt));
            return messages;
        } catch (AmazonSQSException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
