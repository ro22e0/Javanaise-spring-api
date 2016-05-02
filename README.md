Javanaise
=========

Javanaise is an application server (RestApi) made in Java with Spring Framework using J2EE and Java Sdk 1.8.0_92.

Table of Contents
=================

  * [Javanaise](#Javanaise)
  * [Table of Contents](#table-of-contents)
  * [How to start the server] (#how-to-start-the-server)
  * [Data Model](#data-model)
  * [REStApi](#restapi)

How to start the server
=======================

To start the server you need to have a MYSQL server installed in your host machine with this username and password "root"/"root".
After the mysql-server installed with the good username and password, you will have to create a database "rssflux_dev".
After all this steps done you can execute this command in a terminal `./gradlew bootrun` and connect at localhost:8443

REStApi
=======

We will use a REStApi to communicate between all our application. All the request to the web api will be at this address `localhost:8443`.


Using Docker
============
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
