FROM openjdk:21-slim

ARG JAR_FILE=spring-boot-test-app/build/libs/spring-boot-test-app.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
