FROM eclipse-temurin:21-jdk-jammy

RUN useradd -rm -d /home/ubuntu -s /bin/bash -g root -G sudo -u 1001 spring

USER spring

WORKDIR /

COPY /build/libs/*.jar /app.jar

ENV SERVER_PORT=8080

ENTRYPOINT ["java", \
            "-Dserver.port=${SERVER_PORT}", \
            "-jar", \
            "/app.jar"]