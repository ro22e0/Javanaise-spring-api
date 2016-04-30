ServerRSS
=========

ServerRSS is an application server made in Java with Spring Framework using J2EE and Java Sdk 1.8.0_92.

Table of Contents
=================

  * [ServerRSS](#serverrss)
  * [Table of Contents](#table-of-contents)
  * [How to start the server] (#how-to-start-the-server)
  * [Data Model](#data-model)
  * [REStApi](#restapi)

How to start the server
=======================

To start the server you need to have a MYSQL server installed in your host machine with this username and password "root"/"root".
After the mysql-server installed with the good username and password, you will have to create a database "rssflux_dev".
After all this steps done you can execute this command in a terminal `./gradlew bootrun` and connect at localhost:8443

Data Model
==========

Le modèle de données que l'on va utilisé ressemblera à celui-ci ( des modifications peuvent opérées durant le process )

| User      | RSS                    |
| --------- | ---------------------- |
| UserName  | Name                   |
| Email     | Description <Optional> |
| Password  | Link                   |
| Rss       | Users                  |
| FirstName |
| LastName  |

Les utilisateurs possèdent une liste de flux rss qui leurs sont associés et un flux rss est lié à plusieurs utilisateurs.

REStApi
=======

We will use a REStApi to communicate between all our application. All the request to the web api will be at this address `localhost:8443`.
