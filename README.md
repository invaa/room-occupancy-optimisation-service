# Description
This project serves as a room occupancy optimization tool for one of our hotel clients! Our customer has a certain number of free rooms each night, as well as potential guests that would like to book a room for that night.

Our hotel clients have two different categories of rooms: Premium and Economy. Our hotels want their customers to be satisfied: they will not book a customer willing to pay EUR 100 or more for the night into an Economy room. But they will book lower paying customers into Premium rooms if these rooms are empty and all Economy rooms are occupied with low paying customers. The highest paying customers below EUR 100 will get preference for the “upgrade”. Customers always only have one specific price they are willing to pay for the night and if there are more guests than available rooms, the highest paying ones will be booked.

This is a small API that provides an interface for hotels to enter the numbers of Premium and Economy rooms that are available for the night and then tells them immediately how many rooms of each category will be occupied and how much money they will make in total. Potential guests are represented by an array of numbers that is their willingness to pay for the night.

Use the following raw JSON file/structure as mock data for potential guests in your tests (see testing manually for more details):

```json
[23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
```

This project has an intentional overhead to demonstrate best practices for microservice backends: 
- it uses micrometer standard logging the MDC with trace ids and is ready to be integrated with some external tracing tool like Datadog, Zipkin or something else
- the project contains the Dockerfile docker definitions to ease the deployments to common cloud providers (see `Running the project with docker` section)
- it contains postman tests for simplifying the manual validation if needed
- the service provides health check endpoint
- the api definition is available in openapi format in /openapi/api.yaml and is used for generating the controller interface ensuring api design is in line with the implementation

# Running the project without docker
`./gradlew bootRun`

# Testing
In order to run unit tests and component tests run ./gradlew check

# Testing manually
Please use the postman collection from /postman folder

# Running the project with docker
Please use the Dockerfile image. First, build the jar using ./gradlew check bootJar, then build the image using the Dockerfile and then run it.
`docker build . -t roos`
`docker run -p 8080:8080 roos`

# Emulating the building and running using the run.sh on eclipse-temurin:21-jdk-jammy container as per the requirement.
Since it's not clear how that container and context looks like (see the discussion below), I'm assuming the image pulls the code from git and builds it when being built.

To build the image:
`docker build . --no-cache -f Dockerfile_test --build-arg GH_PAT=<token> -t roos_test`

To run on port 8080:
`docker run -p 8080:8080 roos_test` 
