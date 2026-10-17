FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY customer-service/pom.xml customer-service/pom.xml
COPY customer-service/src customer-service/src
WORKDIR /app/customer-service
RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/customer-service/target/customer-service-1.0.0.jar app.jar
ENV SPRING_PROFILES_ACTIVE=portfolio
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
