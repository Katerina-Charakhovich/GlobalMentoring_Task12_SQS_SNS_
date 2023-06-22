FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
COPY target/order-processor-0.0.1-SNAPSHOT.jar order-processor.jar
EXPOSE 8023
ENTRYPOINT ["java","-jar","/order-processor.jar"]
