# Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/alerta360-1.0.0.jar app.jar

# O render.com injeta a variavel PORT; a aplicacao le-la via application.properties.
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
