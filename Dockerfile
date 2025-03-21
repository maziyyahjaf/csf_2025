FROM node:23 AS angular-builder

WORKDIR /angular-app

RUN npm i -g @angular/cli

COPY client/*.json /angular-app
COPY client/src /angular-app/src

RUN npm ci && ng build

FROM eclipse-temurin:23-jdk AS spring-builder

WORKDIR /code_folder

COPY server/pom.xml server/mvnw /code_folder/
COPY server/.mvn /code_folder/.mvn

COPY server/src /code_folder/src

COPY --from=angular-builder /angular-app/dist/client/browser/* /code_folder/src/main/resources/static/

RUN ./mvnw clean package -Dmaven.test.skip=true 

FROM eclipse-temurin:23-jdk

COPY --from=spring-builder /code_folder/target/server-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
ENV PORT=8080

# Expose the port
EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar