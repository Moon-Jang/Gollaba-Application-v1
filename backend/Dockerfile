FROM arm64v8/adoptopenjdk:16.0.1_9-jre-hotspot
# FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
COPY keystore.p12 keystore.p12
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-jar", "app.jar"]