FROM maven:3.6.0-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn -f /app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /app/target/ /app/bin

WORKDIR /app/bin
ENTRYPOINT ["java","-jar","bundestagsbot-jar-with-dependencies.jar"]
