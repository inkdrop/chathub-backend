FROM openjdk:8-alpine
VOLUME /chathub
COPY target/chathub-0.0.1.jar app.jar
RUN sh -c 'touch app.jar'
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=cloud", "-jar","/app.jar"]
