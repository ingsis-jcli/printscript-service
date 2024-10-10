FROM gradle:8.10.1-jdk21-jammy AS build
COPY  . /home/gradle/src
WORKDIR /home/gradle/src
ARG USERNAME
ARG TOKEN
ENV  USERNAME=$USERNAME
ENV TOKEN=$TOKEN
RUN gradle assemble
FROM openjdk:21-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production", "/app/spring-boot-application.jar"]