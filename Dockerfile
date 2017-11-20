FROM ubuntu:16.04
MAINTAINER SpaceInvasionLab <spaceinvasion@yandex.ru>

RUN apt update &&\
    apt-get install -y git openjdk-8-jdk-headless maven

ENV PORT=80 \
    JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/space_invasion_db \
    PGUSER=space_invasion PGPASSWORD=space_invasion_admin_pass PGHOST=127.0.0.1 PGPORT=5432 PGDATABASE=space_invasion_db \
    SPACE_INVASION_ROOT=/var/www/SpaceInvasion

RUN mkdir -p $SPACE_INVASION_ROOT
COPY . $SPACE_INVASION_ROOT
WORKDIR $SPACE_INVASION_ROOT

RUN mvn package
EXPOSE 80
