FROM amazoncorretto:11-alpine-jdk

ARG SERVER_NAME
ARG SPRING_PROFILE
ENV TZ=Asia/Seoul
RUN mkdir /home/${SERVER_NAME}
WORKDIR /home/${SERVER_NAME}
RUN pwd
COPY /build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
