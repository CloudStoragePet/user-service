FROM openjdk:17-jdk-slim
LABEL authors="Vitalii Seniuk"

ARG JAR_FILE=target/*.jar
ARG JWT_SECRET
ARG JWT_LIFETIME
ARG JWT_ISSUER
ARG DB_HOST
ARG DB_USER
ARG DB_PASSWORD

# Set environment variables from build-time arguments
ENV JWT_SECRET=$JWT_SECRET \
    JWT_LIFETIME=$JWT_LIFETIME \
    JWT_ISSUER=$JWT_ISSUER \
    DB_HOST=$DB_HOST \
    DB_USER=$DB_USER \
    DB_PASSWORD=$DB_PASSWORD

COPY ${JAR_FILE} user_service.jar

ENTRYPOINT ["java", "-jar", "/user_service.jar"]