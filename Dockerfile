FROM ubuntu:16.04
MAINTAINER SpaceInvasionLab <spaceinvasion@yandex.ru>

RUN apt update &&\
    apt-get install -y postgresql git

USER postgres

RUN service postgresql start &&\
    psql -c "CREATE ROLE space_invasion WITH SUPERUSER LOGIN ENCRYPTED PASSWORD 'space_invasion_admin_pass';" &&\
    psql -c "CREATE DATABASE space_invasion_db;" &&\
    psql -c "GRANT ALL ON DATABASE space_invasion_db TO space_invasion;" &&\
    service postgresql stop

USER root

RUN apt-get install -y openjdk-8-jdk-headless
RUN apt-get install -y maven

ENV PORT=80
ENV JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/space_invasion_db
ENV PGUSER=space_invasion PGPASSWORD=space_invasion_admin_pass PGHOST=127.0.0.1 PGPORT=5432 PGDATABASE=space_invasion_db
ENV SPACE_INVASION_ROOT=/var/www/SpaceInvasion

EXPOSE 5432
VOLUME  ["/etc/postgresql", "/var/log/postgresql", "/var/lib/postgresql"]

RUN mkdir -p $SPACE_INVASION_ROOT
COPY . $SPACE_INVASION_ROOT
WORKDIR $SPACE_INVASION_ROOT

RUN mvn package

EXPOSE 80
CMD service postgresql start && java -jar $SPACE_INVASION_ROOT/target/SpaceInvasion-1.0.0-SNAPSHOT.jar