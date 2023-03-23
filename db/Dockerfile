FROM mysql:8.0

ARG MYSQL_ROOT_PASSWORD

ENV MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD

ADD ./user-service/ddl/user-service.sql /etc/ddl/user-service.sql

RUN cp /etc/ddl/user-service.sql /docker-entrypoint-initdb.d