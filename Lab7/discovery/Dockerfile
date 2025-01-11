#Cars dockerfile
FROM maven:3.9-eclipse-temurin AS build

WORKDIR /build

COPY pom.xml .
COPY src /build/src

RUN mvn clean install

FROM eclipse-temurin:17-jdk-jammy AS app

WORKDIR /app

COPY --from=build /build/target/*.jar /app/discovery.jar

ENTRYPOINT ["java", "-jar", "discovery.jar"]

EXPOSE 8761
