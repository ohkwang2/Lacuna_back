FROM amazoncorretto:11-alpine-jdk
ARG JAR_FILE=target/*.jar
ARG PROFILES
COPY ${JAR_FILE} app.jar
#ARG는 서버 실행 시 변수로 넣어줄 변수명을 지정
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-jar", "app.jar"]