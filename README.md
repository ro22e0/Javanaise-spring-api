ServerRSS
=========

Table of Contents
=================

  * [ServerRSS](#serverrss)
  * [Table of Contents](#table-of-contents)
  * [Data Model](#data-model)

Data Model
==========
Le modèle de données que l'on va utilisé ressemblera à celui-ci ( des modifications peuvent opérées durant le process )

| User     | RSS         |
| -------- | ----------- |
| UserName | Name        |
| Email    | Description |
| Password | Link        |
| Rss      | Users       |

Les utilisateurs possèdent une liste de flux rss qui leurs sont associés et un flux rss est lié à plusieurs utilisateurs.
