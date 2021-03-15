FROM adoptopenjdk/openjdk11-openj9:debian-slim

WORKDIR /app/dostock

RUN apt-get update \
    && apt-get install -y curl \
    && URL_DOWNLOAD_LATEST_RELEASE=$(curl -L https://api.github.com/repos/matheuslucena/dostock/releases | grep -i browser_download_url | head -n 1 | cut -d '"' -f 4) \
    && curl -o dostock.jar -L $URL_DOWNLOAD_LATEST_RELEASE \
    && chmod u+x dostock.jar

EXPOSE 8080

CMD ["java", "-jar", "dostock.jar", "--db.host=psql", "--db.user=dostock", "--db.pass=dostock", "--storage.path='/storage/files'"]