FROM amazoncorretto:11-alpine-jdk

ARG SERVER_NAME
ARG SPRING_PROFILE
ENV TZ=Asia/Seoul
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","app.jar"]
