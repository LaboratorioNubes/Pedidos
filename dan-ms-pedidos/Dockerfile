FROM openjdk:11.0.7-slim
LABEL maintainer="aleguiter9@gmail.com"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dan-ms-pedidos.jar
ENTRYPOINT ["java","-jar","/dan-ms-pedidos.jar"]