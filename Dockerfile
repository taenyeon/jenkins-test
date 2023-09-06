FROM amazoncorretto:11-alpine-jdk

ENV SPRING_PROFILES_ACTIVE local
ENV TZ=Asia/Seoul
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","app.jar"]
