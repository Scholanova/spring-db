FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY ./ ./

# set build ENV
ARG DATABASE_URL
ENV ARTIFACT_ID="project-db"
ENV VERSION="0.0.1"

ENV DATABASE_URL=$DATABASE_URL
RUN mvn install -Dspring-boot.run.profiles=prod -DskipTests

FROM openjdk:8-jre-alpine as deploy

ARG DATABASE_URL
ENV ARTIFACT_ID="project-db"
ENV VERSION="0.0.1"

ENV ARTIFACT="${ARTIFACT_ID}-${VERSION}.jar"

WORKDIR /app

COPY --from=build /app/target/${ARTIFACT} /app
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar ${ARTIFACT}"]