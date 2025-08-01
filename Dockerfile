# Use official OpenJDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy everything into the container
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Run the application
# Use official OpenJDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy everything into the container
COPY . .

# Build the application
RUN ./mvnw clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/ecommerce-backend-0.0.1-SNAPSHOT.jar"]

