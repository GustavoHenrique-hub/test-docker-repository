# Use uma imagem base do Java
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR do build do projeto para o contêiner
COPY target/products_api.jar /app/app.jar

# Exponha a porta usada pela aplicação
EXPOSE 8080

# Comando para rodar o JAR
CMD ["java", "-jar", "app.jar"]
