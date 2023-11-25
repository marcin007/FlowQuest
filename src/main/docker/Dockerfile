FROM mysql:8.0.30
ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=flowquest
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password
ADD mysql/schema1.txt /docker-entrypoint-initdb.d
EXPOSE 3306
