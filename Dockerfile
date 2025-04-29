# -----------------------------
# 🌱 BUILD STAGE
# -----------------------------
FROM gradle:8.10-jdk21 AS builder

# Set working directory
WORKDIR /app

# Copy only build files first for better layer caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Pre-download dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy the rest of the project
COPY . .

# Build the jar, skip tests for speed
RUN ./gradlew bootJar -x test --no-daemon

# -----------------------------
# 🚀 RUNTIME STAGE
# -----------------------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose default port (optional)
EXPOSE 8080

# Run the Spring Boot app
#CMD ["java", "-jar", "app.jar"]
# Or to append extra arguments at runtime
ENTRYPOINT ["java", "-jar", "app.jar"]