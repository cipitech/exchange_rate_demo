# exchange_rate_demo

### Live Demo ###

You can view a live demo of this repository here: http://rates.cipitech.com/api/swagger-ui/index.html

### What is this repository for? ###

An API that provides functionality for exchange rates and currency conversions.
It loads up-to-date exchange rate data from two types of datasource depending on the
spring-boot profile that you choose. You can only choose one profile at a time.
If you add both profiles then by default only the "third-party" will be activated.

### Profiles ###

#### "third-party" ####

When you use this profile then the currencies and the exchange rates are retrieved from
a third party API (https://exchangerate.host). In order to use this public API first you must
register and obtain your API key. Then you must put your API key in the spring boot configuration properties.

#### "offline" ####

When you use this profile then the currencies and the exchange rates are retrieved from .json
files from the filesystem. Only two json files are included (EUR and USD) so if you want extra
currencies you must create additional files. Make sure that you follow the syntax from the existing files.

### Configuration ###

All the configuration is done at application-*.yml properties. In order to override the configuration
you must either edit these files or add VM options before you run the application.

**Note**: The same applies if you want to run the tests included. You must set the necessary properties.

The 3 properties that MUST be set before you run (because i have put dummy data and you won't be able to run) are:

* **spring.profiles.active**  *#Choose either "third-party" OR "offline"*
* **third-party.access-key** *#The API key that you obtained when you registered to https://exchangerate.host*
* **offline.data-path** *#The absolute path of your workspace (the path where you cloned this repository) followed by "/docker/offline_data"*

You can also change some other configuration properties if you want to customise your application or you can just leave
the default values:

* Postgres Configuration
    * **spring.datasource.url**
    * **spring.datasource.username**
    * **spring.datasource.password**
* Tomcat Configuration
    * **server.port**

### How do I get set up? ###

First you need to clone the repository to your PC.
Then you need to have Java 17+ installed and Apache Maven. Finally if you choose options 1, 2 and 3 from below, you need to have Postgresql installed (preferably running on port 5434) and a database named "exchange_rate" already created (if you choose your own port and database name make sure to put your changes in the application.yml file).

There are four approaches:

1. Intellij or Eclipse IDE: Open project as Maven project and start main class. A default Spring boot run configuration
   must have already been created by the IDE, so you can select it and run it.

   **Note**: You must edit the run configuration and add the following VM options in order to override some spring boot
   properties
   that need to be changed:

       -Dspring.profiles.active=third-party
       -Dthird-party.access-key=<INSERT YOUR API KEY HERE>
       -Doffline.data-path=<INSERT YOUR WORKSPACE ABSOLUTE PATH HERE>/docker/offline_data
   Alternatively you can just edit the application-*.yml files and put your desired configuration.


2. Use maven to build and then run the jar executable
    * Run in project root dir (where pom.xml is located): $ mvn clean install
    * go to "/target" dir and run: 
   
   $ java -jar "-Dspring.profiles.active=third-party -Dthird-party.access-key=< INSERT YOUR API KEY HERE > -Doffline.data-path=< INSERT YOUR WORKSPACE ABSOLUTE PATH HERE >/docker/offline_data" exchange-rate-converter-[version].jar


3. Use maven to run as a Spring Boot application
    * Run in the project root dir: 
   
   $ mvn spring-boot:run "-Dspring-boot.run.jvmArguments=-Dspring.profiles.active=third-party -Dthird-party.access-key=< INSERT YOUR API KEY HERE > -Doffline.data-path=< INSERT YOUR WORKSPACE ABSOLUTE PATH HERE >/docker/offline_data"


4. Use Docker and docker-compose
    * Run in project dir: $ mvn clean install
    * Go to "target" dir and copy the jar file exchange-rate-converter-[version].jar into the "docker" folder.
    * Go to a linux system (or windows) where you have installed docker and docker-compose.
    * Choose a folder and copy all the contents from the "docker" folder.
    * Edit the .env file and put your preferred configuration.
    * The properties that MUST be set are "ACTIVE_PROFILES" and "ACCESS_KEY"
    * You can leave everything else as it is.
    * Run the command $ docker-compose up -d --build

### How to use the service? ###

1. run application by using one of the above methods and go to http://localhost:8082/api/swagger-ui/index.html
2. try all the endpoints via swagger and have fun.

### Who do I talk to? ###

Written by Altin Cipi: haris.tsipis (at) gmail.com

### Working hours Logs ###
* 18/10/2023: 5h [6pm-11pm]
* 19/10/2023: 2h [6pm-8pm]
* 20/10/2023: 2h [6pm-8pm]
* 21/10/2023: 9h [1pm-10pm]
* 22/10/2023: 5h [6pm-11pm]
* 23/10/2023: 5h [6pm-11pm]
