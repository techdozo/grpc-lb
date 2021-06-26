FROM openjdk:11-jre-slim
RUN mkdir /app
WORKDIR /app

ADD ./build/libs/grpc-server-svc-1.0.0.jar /app/app.jar

EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]