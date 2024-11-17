#Use an official OpenJDK runtime as a parent image
FROM maven:3.9.4-eclipse-temurin-21-alpine

#Set the working directory in the container
WORKDIR /app

##Copy the Spring boot application JAR file into the container
#COPY ./target/product-service-0.0.1-SNAPSHOT.jar .
#
##Expose the port the app runs on
#EXPOSE 8080
#
##Run the Spring Boot Application
#CMD ["java", "-jar", "product-service-0.0.1-SNAPSHOT.jar"]

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline

COPY src ./src
CMD ["./mvnw", "spring-boot:run"]