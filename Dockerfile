FROM adoptopenjdk/openjdk11-openj9:debian-slim

WORKDIR /app/dostock

RUN apt-get update \
    && apt-get install -y curl

COPY ../build/libs/dostock-0.0.1-SNAPSHOT.jar /app/dostock/dostock.jar

EXPOSE 8080

CMD ["java", "-jar", "dostock.jar", "--db.host=psql", "--db.user=dostock", "--db.pass=dostock"]