FROM openjdk:11-jre-alpine3.9

COPY Application-0.1.jar /target/Application.jar

CMD ["java", "jar", "/target/Application.jar"]

