FROM maven:3.8.5-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package -DskipTests=true

FROM eclipse-temurin:17-jdk-alpine as prod
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
ENV SERVER_PORT=6061
EXPOSE 6061
ENTRYPOINT ["java","-jar","/app/app.jar"]