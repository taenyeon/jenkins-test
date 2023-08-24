FROM amazoncorretto:11-alpine-jdk

ENV TZ=Asia/Seoul
ENV SERVICE_PORT=8080
RUN mkdir /home/testServer
RUN pwd
WORKDIR /home/testServer
COPY /build/libs/*.jar test.jar
ENTRYPOINT ["java","-jar","/home/testServer/test.jar"]
