# JAVA 21
FROM eclipse-temurin:21-jdk-alpine


# Set timezone environment variable
ENV TZ=America/Sao_Paulo

# Install tzdata and set timezone
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    apk del tzdata


ARG JAR_FILE=target/*.jar

# Copy JAR
COPY ${JAR_FILE} app.jar
COPY wait-for-postgres.sh wait-for-postgres.sh
RUN apk add --no-cache netcat-openbsd

RUN chmod +x wait-for-postgres.sh

# RUN JAR
ENTRYPOINT ["/wait-for-postgres.sh", "db", "5432", "java", "-jar", "app.jar"]
