FROM openjdk:11 as rabbitmq

EXPOSE 8081

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

# Copy pom.xml
# COPY pom.xml .

# Copy Project Source
COPY ./src ./src
COPY ./pom.xml ./pom.xml

RUN chmod 775 /app/mvnw

RUN ./mvnw package -DskipTests
ENTRYPOINT [ "java","-jar","target/spring-rabbitmq-0.0.1-SNAPSHOT.jar" ]
