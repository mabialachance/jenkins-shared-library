### Jenkins CI/CD Pipeline
``Overview``
This project implements a Jenkins CI/CD pipeline for a Java Spring Boot application. The pipeline automatically increments the application version, builds and tests the application, creates a Docker image, publishes it to Docker Hub, and commits the version update back to GitHub.

## Pipeline Flow
GitHub Repository
|
v
Jenkins
|
+--> Increment Version
|
+--> Build & Test
|
+--> Build Docker Image
|
+--> Push Image to Docker Hub
|
+--> Commit Version Update
|
+--> Push Changes to GitHub

## Prerequisites
`Install the following tools:`
Jenkins
JDK 25
Maven 3.9
Docker


`You will also need:`
GitHub account
Docker Hub account

## Jenkins Configuration
``Maven``
Configure Maven in Jenkins:
Manage Jenkins -> Tools -> Maven Installations
Example:
Name: maven-3.9

## GitHub Credentials
Create GitHub credentials:
Manage Jenkins -> Credentials
Credential ID:

github-credentials
`Used for:`
Repository checkout
Shared library access
Git push operations

### Shared Library
Configure the shared library:

Manage Jenkins -> System -> Global Trusted Pipeline Libraries
Name: jenkins-shared-library
Default Version: master
Repository:
https://github.com/mabialachance/jenkins-shared-library.git
Load the library in the Jenkinsfile:
@Library('jenkins-shared-library') _

## Pipeline Stages
``Increment Version``
The application version is automatically updated in pom.xml.

Command:
mvn build-helper:parse-version \
versions:set \
-DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.nextIncrementalVersion} \
versions:commit
Example:
1.1.23 → 1.1.24

``Build and Test``
Compile, test, and package the application.
mvn clean package
Build artifact:
target/java-maven-app-<version>.jar

## Build Docker Image
Create a Docker image from the packaged application.

docker build -t mabialachance/my-demo-app:<version>-<build_number> .
Example:
docker build -t mabialachance/my-demo-app:1.1.24-41 .

```Push Docker Image```
Authenticate with Docker Hub and publish the image.

docker login -u <username> --password-stdin
docker push mabialachance/my-demo-app:1.1.24-41

## Deploy
The deploy stage is currently a placeholder for future deployment activities.
echo 'deploying docker image...'

## Commit Version Update
After a successful build, Jenkins commits the updated version.
git config --global user.email "jenkins@example.com"
git config --global user.name "jenkins"
git add Jenkins/pom.xml
git commit -m "ci: version bump"

## Push Changes to GitHub
Update the target branch with the new application version.
git fetch origin jenkins-jobs
git rebase FETCH_HEAD
git push origin HEAD:refs/heads/jenkins-jobs

## Shared Library Functions
The pipeline uses the following functions from the Jenkins shared library:

buildImage()
dockerLogin()
dockerPush()
These functions encapsulate the Docker build, login, and push operations to keep the Jenkinsfile concise and reusable.

## Technologies
Jenkins
Java 25
Maven 3.9
Spring Boot
Docker
Docker Hub
GitHub
Jenkins Shared Library

## Example Build
Application Version : 1.1.24
Build Number        : 41

Docker Image:
mabialachance/my-demo-app:1.1.24-41

Pipeline Status: SUCCESS