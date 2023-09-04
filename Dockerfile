FROM amazoncorretto:11-alpine-jdk

ENV TZ=Asia/Seoul
ENV SERVICE_PORT=${SERVER_PORT}
RUN mkdir /home/${SERVER_NAME}
WORKDIR /home/${SERVER_NAME}
RUN pwd
COPY /build/libs/*.jar ${SERVER_NAME}.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILE}","/home/${SERVER_NAME}/${SERVER_NAME}.jar"]
