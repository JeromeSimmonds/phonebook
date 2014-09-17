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
* **phonebook-core** Back-end project dealing with the database. It consists of 3 layers: the domain model (business entities), the service layer (business objects) and the data layer (data access object).
* **phonebook-web** Front-end project (website). Consists of controllers, forms and views. Relies on the core project to deal with data and present it to the user to interact with it.

Web service coming soon

## Installation
Install the parent project: with the command line go to the the phonebook directory (phonebook/phonebook), then run  
```mvn clean install```

Update your Maven settings, add the properties jdbc.phonebook.username, jdbc.phonebook.password and jdbc.phonebook.url. Ideally you should have at least 2 different profiles, test and development.  

Reset the test database: with the command line, in phonebook-core, run.  
```mvn db-migration:reset -Ptest```

Install the core project: with the command line, in phonebook-core, run  
```mvn clean install```

Tests will be run.

In progress...