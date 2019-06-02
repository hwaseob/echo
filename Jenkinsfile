node {
    //Define all variables
    def project = 'netty_echo'
    def appName = 'echo'
    //def serviceName = "${appName}-backend"
    def imageVersion = '1'
    def namespace = 'dev'
    def registry = "reg.svc.uangel.com:5000"
    def imageTag = "${registry}/${project}:${imageVersion}.${env.BUILD_NUMBER}"

    //Checkout Code from Git
    checkout scm

    stage('Build') {
        sh("/usr/local/bin/mvn clean package")
    }

    //Stage 1 : Build the docker image.
    stage('Build image') {
        sh("docker build -t ${imageTag} .")
    }

    //Stage 2 : Push the image to docker registry
    stage('Push image to registry') {
        sh("docker push ${imageTag}")
    }

    //Stage 3 : Deploy Application
    stage('Deploy Application') {
        sh("kubectl get ns ${namespace} || kubectl create ns ${namespace}")
        sh("sed -i.bak 's#${registry}/${project}.*#${imageTag}#' ./k8s/*.yaml")
        sh("kubectl --namespace=${namespace} apply -f k8s/echo.yaml")
        //sh("echo http://`kubectl --namespace=${namespace} get service/${feSvcName} --output=json | jq -r '.status.loadBalancer.ingress[0].ip'` > ${feSvcName}")

    }

}