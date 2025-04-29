FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and pom.xml first for better layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Use a smaller runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=0 /app/target/*.jar app.jar

# Environment variables that can be overridden at runtime
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/finance-manager
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=root
ENV SERVER_PORT=8080

# Expose the port the app runs on
EXPOSE ${SERVER_PORT}

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
