# Estágio de build
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Estágio final
FROM openjdk:17-jdk-slim

WORKDIR /app

# Instalar bibliotecas necessárias para JasperReports funcionar corretamente (libfreetype e libfontconfig)
RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libfontconfig1 \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

EXPOSE 8080

# Copiar o artefato gerado no estágio de build
COPY --from=build /app/target/discord-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

