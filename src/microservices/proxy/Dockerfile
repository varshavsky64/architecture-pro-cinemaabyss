# The jar does not depend on the CPU architecture, so Maven always runs natively,
# also when the image is built for another platform (linux/arm64 in CI)
FROM --platform=$BUILDPLATFORM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Download dependencies first, so they are cached between builds
COPY pom.xml ./
RUN mvn -B -q dependency:go-offline

# Build the application
COPY src ./src
RUN mvn -B -q package

# Use a smaller JRE image for the final application
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/proxy-service.jar app.jar

EXPOSE 8000

CMD ["java", "-jar", "app.jar"]
