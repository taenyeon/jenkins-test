FROM amazoncorretto:11-alpine-jdk

ENV TZ=Asia/Seoul
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","app.jar"]
