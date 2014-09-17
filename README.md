phonebook
=========
**Demo for you guys who ask me if I have a GitHub account to show you some code ;-)**

## Description
Insanely complex Java implementation of a simple online multi-users phonebook.
Users need to log in to see and manage their contacts.

##Technology
* Java, Spring Framework, Spring Data/Hibernate, Spring Security
* Maven
* MySQL
* Jetty

## Prerequisites
JDK 1.6 or above, Maven, MySQL 5.x

## Repository structure
* **phonebook** Maven parent project
* **phonebook** Back-end project dealing with the database. It consists of 3 layers: the domain model (business entities), the service layer (business objects) and the data layer (data access object).
* **phonebook-web** Front-end project (website). Consists of controllers, forms and views. Relies on the core project to deal with data and present it to the user to interact with it.