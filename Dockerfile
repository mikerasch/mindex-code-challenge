FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y curl
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9092
ENTRYPOINT ["java", "-jar", "/app.jar"]
