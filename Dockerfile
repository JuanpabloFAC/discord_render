# Etapa 1: build da aplicação
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven && \
    apt-get clean

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: imagem leve para execução
FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 8080

# Altere o nome do .jar abaixo se o seu for diferente
COPY --from=build /app/target/discord-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
