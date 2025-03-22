FROM openjdk:21-jdk


WORKDIR /app

EXPOSE 8080

COPY ./target/HelloService-0.0.1-SNAPSHOT.jar .

CMD [ "java", "-jar","HelloService-0.0.1-SNAPSHOT.jar" ]