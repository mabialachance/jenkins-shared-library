#!/user/bin/env groovy

def call() {
  echo "building the docker image..."
  withCredentials([usernamePassword(credentialsId: 'docker-hub-repos', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]){
     sh "docker build -t mabialachance/my-demo-app:${IMAGE_NAME} ."
     sh 'echo $PASSWORD | docker login -u $USERNAME --password-stdin'
     sh "docker push mabialachance/my-demo-app:${IMAGE_NAME}"
    }
}
