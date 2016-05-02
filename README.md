Javanaise
=========

Javanaise is an application server (RestApi) made in Java with Spring Framework using J2EE and Java Sdk 1.8.0_92.

Table of Contents
=================

  * [Javanaise](#javanaise)
  * [Table of Contents](#table-of-contents)
  * [How to start the server] (#how-to-start-the-server)
  * [Data Model](#data-model)
  * [REStApi](#restapi)

How to start the server
=======================

To start the server you need to have a MYSQL server installed in your host machine with this username and password "javanaise"/"javanaise".

After the mysql-server installed with the good username and password, you will have to create a database "rssflux_dev".

When all this steps done you can execute this command in a terminal `./gradlew bootrun` and connect at localhost:8080

RestApi
=======

We will use a REStApi to communicate between all our applications.
All the requests to the API are describe on the [wiki](https://github.com/ro22e0/Javanaise-spring-api/wiki).

Data Model
==========

Le modèle de données que l'on va utilisé ressemblera à celui-ci ( des modifications peuvent opérées durant le process )

| user      | feed        |
| --------- | ----------- |
| firstname | title       |
| lastname  | description |
| email     | link        |
| password  | users       |
| feeds     |

Les utilisateurs possèdent une liste de flux rss qui leurs sont associés et un flux rss est lié à plusieurs utilisateurs.

Using Docker
============
By clonning the repository

Open a terminal and go into javanaise-spring-api/src/main/docker.

Run `docker-compose up` to start and `docker-compose down`to stop.

---

Running inside docker container linked with MySQL container

Run MySQL 5.6 in Docker container :
~~~
docker run --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=rssflux -e MYSQL_USER=javanaise -e MYSQL_PASSWORD=javanaise -d mysql
~~~

You can modify the value of `MYSQL_ROOT_PASSWORD`, `MYSQL_DATABASE`, ` MYSQL_USER`, `MYSQL_PASSWORD`

Run the rss server in Docker container and link it to mysqldb :
~~~
docker run --name javanaise --link mysqldb:mysql -p 8443:8080 -d ro22e0/javanaise
~~~

You can modify the bind port on the local machine like `8082:8080`, `5445:8080`, etc.

Remove the `-d` option if you don't want it to run in background.
