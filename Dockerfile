FROM openjdk:11-jdk-slim
COPY pom.xml /opt/app/
COPY mvnw /opt/app/
COPY .mvn /opt/app/.mvn
COPY src /opt/app/src
RUN cd /opt/app && ./mvnw -f pom.xml clean package