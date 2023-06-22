package com.epam.sns.logs.service;

import com.epam.sns.logs.model.MessageSqs;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificationQueueListener {

    @Value("${sqs.log.url}")
    private String urlQueueLogs;

    @Value("${path.logs}")
    private String path;

    @Autowired
    private final S3Service S3Service;

    @Autowired
    private final NotificationService notificationService;
    static final Logger logger = LogManager.getLogger(S3Service.class);

    @Scheduled(fixedRate = 3000)
    public void readMessagesFromQueue() {
        var messages = notificationService.readMessagesFromQueue(urlQueueLogs);
        messages.stream().forEach(s->{
            try {
                MessageSqs message = new Gson().fromJson(s.getBody(), MessageSqs.class);
                addLog(LocalDateTime.now()+":" + message.getMessage()+System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
     private void addLog(String message) throws IOException {
         Files.write(Paths.get(path), message.getBytes(), StandardOpenOption.APPEND);
     }
}

