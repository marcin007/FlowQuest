FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=admin
ENV MYSQL_DATABASE=flowquest
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password
ADD mysql/schema.sql /docker-entrypoint-initdb.d
EXPOSE 3306
