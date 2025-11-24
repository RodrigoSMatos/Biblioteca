# Estágio 1: build com Maven e JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia o pom primeiro (ajuda no cache de dependências)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copia o código fonte
COPY src ./src

# Gera o jar do Spring Boot
RUN mvn -q -DskipTests clean package

# Estágio 2: imagem final só com JRE
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o jar gerado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Profile padrão (o Render ainda pode sobrescrever pela env SPRING_PROFILES_ACTIVE)
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
