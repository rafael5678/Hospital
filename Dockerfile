FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copiamos los archivos de configuracion de maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copiamos el codigo fuente
COPY src src

# Damos permisos de ejecucion al wrapper antes de correrlo
RUN chmod +x mvnw

# Construimos la app saltando los tests
RUN ./mvnw clean package -DskipTests

# Creamos la imagen final mas liviana
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/hospy-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
