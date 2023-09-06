FROM amazoncorretto:11-alpine-jdk

ARG SPRING_PROFILE
ENV TZ=Asia/Seoul
RUN mkdir /home/${SERVER_NAME}
WORKDIR /home/${SERVER_NAME}
RUN pwd
COPY /build/libs/*.jar /home/${SERVER_NAME}/${SERVER_NAME}.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILE}","/home/${SERVER_NAME}/${SERVER_NAME}.jar"]
