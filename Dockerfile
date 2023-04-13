FROM openjdk:18-alpine3.13

EXPOSE 5050

ADD target/DiplomaWork-0.0.1-SNAPSHOT.jar diploma.jar

ENTRYPOINT ["java", "-jar", "diploma.jar"]