FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/sofp-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} sofp.jar

CMD ["java", "-jar", "-Duser.timezone=Asia/Seoul", "sofp.jar"]