package com.epam.sns.logs.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AwsConfig {
    @Value("${aws_access_key_id}")
    private String awsKeyId;

    @Value("${aws_secret_access_key}")
    private String accessKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonSQS sqsClient(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKeyId, accessKey);
        return AmazonSQSClient
                .builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public AmazonS3 awsS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsKeyId, accessKey);
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

}
