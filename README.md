# Scoreboard
Simple web application for keeping track of player scores.
<br>
<br>
Application is build using:
- MySQL database
- Java Spring Boot
- AngularJS, AngularUI and Twitter bootstrap CSS
- Gradle

Requirements for running the application:
- Docker, Docker Compose
- or MySQL database (tested with version 8.0.17), JDK 8 or higher

## How to run the application?
### Using Docker and Docker Compose
Clone or download this repository, go to the folder using command line and then go to the docker folder.
```
cd docker
```

Run application using docker-compose up command.
```
docker-compose up
```

After the server is started, point your web browser to {ipofmachinerunningdocker}:8080 to access the application.
```
{ipofmachinerunningdocker}:8080
```

### Using software istalled on local machine 
Access MySQL server (install MySQL server if needed) to create a database and a user for the application. Here root is your root user name and password is your root user password.
```
mysql -uroot -ppassword
```

Create a database called scoreboard_db for the application.
```
mysql> create database scoreboard_db;
```

Create a database user for the application.
```
mysql> create user 'springuser'@'%' identified by '1234';
```

Grant all accesses to the created user for the created database.
```
mysql> grant all on scoreboard_db.* to 'springuser'@'%';
```

Clone or download this repository and go to the folder using command line. Run the application server from command line using the Gradle wrapper.
```
./gradlew bootRun
```

After the server is started, point your web browser to localhost:8080 to access the application.
```
localhost:8080
```