# Step 1
FROM eclipse-temurin:21-jdk-alpine AS build

RUN apk add --no-cache bash curl git unzip

COPY . /app
WORKDIR /app

RUN chmod +x mvnw

RUN ./mvnw clean package

# Step 2
FROM eclipse-temurin:21-jdk-alpine

# Set timezone
ENV TZ=America/Sao_Paulo

RUN apk add --no-cache tzdata netcat-openbsd && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    apk del tzdata

COPY --from=build /app/target/*.jar app.jar

COPY wait-for-postgres.sh wait-for-postgres.sh
RUN chmod +x wait-for-postgres.sh

# RUN JAR
ENTRYPOINT ["/wait-for-postgres.sh", "db", "5432", "java", "-jar", "app.jar"]
