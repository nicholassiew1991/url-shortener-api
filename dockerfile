ARG jdk_version=21.0.1_12

FROM eclipse-temurin:${jdk_version}-jdk-alpine AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

RUN ./mvnw test package

FROM eclipse-temurin:${jdk_version}-jre-alpine

COPY --from=build /app/target/url-shortener-api-1.0.0.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "/app/url-shortener-api-1.0.0.jar"]