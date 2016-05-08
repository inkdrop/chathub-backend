FROM java:8-alpine
VOLUME /chathub
ADD target/chathub-backend-0.0.3.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=docker","-jar","/app.jar"]
