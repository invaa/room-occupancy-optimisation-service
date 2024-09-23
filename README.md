# Running the project without docker
`./gradlew bootRun`

# Running the project with docker
Please use the Dockerfile image. First, build the jar using ./gradlew check bootJar, then build the image using the Dockerfile and then run it.
`docker build . -t roos`
`docker run -p 8080:8080 roos`

# Emulating the building and running using the run.sh on eclipse-temurin:21-jdk-jammy container as per the requirement.
Since it's not clear how that container and context looks like (see the discussion below), I'm assuming the image pulls the code from git and builds it when being built.

To build the image:
`docker build . --no-cache -f Dockerfile_test --build-arg GH_PAT=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 -t roos_test`

To run on port 8080:
`docker run -p 8080:8080 roos_test` 