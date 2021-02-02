FROM maven:3.6-openjdk-8-slim as build
WORKDIR /opt/build
COPY pom.xml .
RUN mvn -B -f pom.xml dependency:go-offline

COPY src src
RUN mvn -B package -DskipTests

FROM openjdk:8-jre-slim
ARG JAR_FILE=/opt/build/target/*.jar
WORKDIR /opt/app/

RUN groupadd --gid 10001 liquibasemigration && \
  useradd --gid 10001 --uid 10001 liquibasemigration
USER liquibasemigration:liquibasemigration

EXPOSE 8080
COPY --from=build ${JAR_FILE} liquibase-migrations.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "liquibase-migrations.jar"]