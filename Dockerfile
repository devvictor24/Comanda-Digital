# Imagem com Java 17
FROM eclipse-temurin:17-jdk

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia tudo para o container
COPY . .

# Dá permissão ao Maven Wrapper
RUN chmod +x mvnw

# Build do projeto
RUN ./mvnw clean package -DskipTests

# Expõe a porta usada pelo Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/Comanda-Digital-0.0.1-SNAPSHOT.jar"]
