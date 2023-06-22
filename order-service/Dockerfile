FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
EXPOSE 8022
ENTRYPOINT ["java","-jar","/order-service.jar"]