## geekday-Microservices setup.
This is a skeleton used by geekday to try out various scenarios to be implemeneted as microservices.
The codebase has basic structure to try out various scenarios of microservice implementations with services communicating using events. The setup uses HsqlDb as in memory database and JeroMQ (Java implementation of ZeroMQ) for event collaboration. As an example this has a basic example of AccountService and CustomerService communicating with each other.
I hope this setup will be helpful for people to try out different scenarios of their interest quickly.


## How to use.

* To build use
  ./gradlew clean build shadowJar
  
* To run 
   java -jar build/libs/geekday-microservices-1.0-all.jar
   
* Test it with 
  curl --data "name=name&address=address" -i http://localhost:8080/customer
  
  It should return
  
  HTTP/1.1 201 Created
  Date: Sat, 16 Jul 2016 05:36:02 GMT
  Location: http://localhost:8080/profile
  Content-Length: 0
  Server: Jetty(9.4.z-SNAPSHOT
  
  ## Use Cases Implemented.
  
# Account and Customer Systems

* Customer registers.
* Account is notified.
* Account creates a new account and notifies customer service.
* Customer should be updated with account id.

# Flight Booking System.

* Customer searches for flight availability with SearchFlights service. 
* Customer books a flight with FlightBooking service.
* FlightBooking service published event for successful booking.
* TicketsService issues tickets. 
* FlightSearchService decrements available seats count from inventory.  (Depending on rules like 10% overbooking)
