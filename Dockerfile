FROM --platform=linux/arm64 maven:3.9.7-eclipse-temurin-21-alpine as build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests -Dspring.profiles.active=prod

FROM --platform=linux/arm64 eclipse-temurin:21-jre-alpine

COPY --from=build /app/target/my-site-api-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]