ServerRSS
=========

ServerRSS is an application server made in Java (J2EE, 1.8.0_92).

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

| User      | RSS         |
| --------- | ----------- |
| UserName  | Name        |
| Email     | Description |
| Password  | Link        |
| Rss       | Users       |
| FirstName |
| LastName  |

Les utilisateurs possèdent une liste de flux rss qui leurs sont associés et un flux rss est lié à plusieurs utilisateurs.

REStApi
=======

We will use a REStApi to communicate between all our application, i will explain how to use it and some exemple.

User Management
---------------

The application include some user management actually we can create and delete a user all the user management will be at this addresse localhost:8443/users/SOMETHING
(SOMETHING will be replace with your action, it will be explain just below).

*Create User*
To create an user you show use a http request post at the address localhost:8443/users/create with user information in the body (it will be in JSON)

I.E: I want to create a user with the username: User1, email: User1@user.com, password: User

You should send on a http POST request this :

{
    "username" : "User1",
    "email" : User1@user.com,
    "password": User
}

This is the minimal required fields to create a user you can add 2 fields (firstname, lastname).

To know if it works i will send something like this "Success : user created" or "Failed: cannot create user" or "Failed: some fields may be empty : mandatory field : email, password, username";

Maybe in the feature i will update this response to something better and do it in the JSON format.

*Delete User*

To delete an user you need to send a http DELETE request at this address localhost:8443/users/delete/{username} (replace {username} by the user username)

I.E : I want to delete the user i previously create (User1)

I will send a http DELETE request at this address localhost:8443/users/delete/User1

You should have a response like this "Failed: {user} not found" or "Success: User deleted"