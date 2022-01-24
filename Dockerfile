FROM openjdk:11
COPY assignment-api/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

