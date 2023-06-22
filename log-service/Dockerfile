FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
RUN mkdir /log
RUN cat > /log/order.log
COPY target/log-service-0.0.1-SNAPSHOT.jar log_service.jar
EXPOSE 8021
ENTRYPOINT ["java","-jar","/log_service.jar"]
