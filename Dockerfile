FROM amazoncorretto:11-alpine-jdk

ARG SERVER_NAME
ARG SPRING_PROFILE
ENV TZ=Asia/Seoul
ENV SERVER_NAME=${SERVER_NAME}
RUN mkdir /home/${SERVER_NAME}
WORKDIR /home/${SERVER_NAME}
RUN pwd
COPY /build/libs/*.jar ${SERVER_NAME}.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILE}","/home/${SERVER_NAME}/${SERVER_NAME}.jar"]
