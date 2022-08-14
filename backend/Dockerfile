FROM arm64v8/adoptopenjdk:11-jre-hotspot
# FROM adoptopenjdk/openjdk11:alpine-jre
CMD ["./gradlew", "clean", "build"]
ARG JAR_FILE_PATH=build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-jar", "app.jar"]