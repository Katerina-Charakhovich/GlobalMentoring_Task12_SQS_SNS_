package com.epam.sns.logs.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final AmazonSQS sqsClient;

    public List<Message> readMessagesFromQueue(String queueUrl) {
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
