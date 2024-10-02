# online-book-store-bnpf

# Technologies Used :
  - Java 21
  - Spring boot 3.3.4
  - Spring data jpa
  - Rest
  - H2 in-memory databse
  - Lombok
  - Mockito

# Description 

online-book-store-bnpf is a spring boot application to simulate the online book shoping process , the project is depending on in-memory h2 database which automatically initilize during the application startup with new schema and data , you can find the database schema and data in schema.sql and data.sql.
PS: react ui part and authentication still in progress due to time limitation.

# Steps to Run 

- Clone the project and checkout main branch, you can just import the project in Inteliij or any preferable IDE.
- build the project using build tool in IDE or you can build it using maven by ````mvn clean install```` command, then run it using run option in the IDE or you can run it by ````mvn spring-boot:run````
- After running the application you can test the Api's by importing the postman collection included under the root folder with name  ````bnpf.postman_collection.json````
- To track the in-memory database , access http://localhost:8080/h2-console and make sure that you are using ````org.h2.Driver```` as a driver class and ````jdbc:h2:mem:online-book-store-db```` as a JDBC URL with username ````sa```` with no password
- Following are the operations that can be done by the system:
  1- 
