FROM openjdk:8u92-jdk-alpine
ADD target/myfitnessnote-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar myfitnessnote-0.0.1-SNAPSHOT.jar