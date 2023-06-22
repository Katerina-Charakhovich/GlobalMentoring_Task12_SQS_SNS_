package com.epam.sns.logs.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Autowired
    private final AmazonS3 s3Client;

    @Value("${s3.bucket}")
    private String bucketName;

    @Value("${s3.folder}")
    private String folder;

    @Value("${path.logs}")
    private String path;

    static final Logger logger = LogManager.getLogger(S3Service.class);

    @Scheduled(fixedRate = 5000)
    public void moveLogsToS3() {
        try {
            Path logsFile = Paths.get(path);
            String key = folder + "/" + logsFile.getFileName();
            s3Client.putObject(new PutObjectRequest(bucketName, key, logsFile.toFile()));
            logger.info("Log files have updated on S3! : {}");
        } catch (
                Exception e) {
            logger.error("Error in moving log files! : {}", e);
        }
    }
}
