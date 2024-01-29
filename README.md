# Kong API
The following document describes the deployment of a basic Spring API Service and MongoDB web stack on Kubernetes. Currently, the API is not exposed to the internet, but can be accessed via port-forwarding, and MongoDB does not use replica sets.

## Description
The API is a simple Spring Boot application that exposes a REST API to solve the problem proposed in the TechChallenge from the FIAP Software Architecture specialization course.

## Requirements
```
 Java 17
 Gradle 7.5.1
 Docker 4.23.0 or higher
 Minikube 1.32.0 or higher
```

### Kubernetes
Using Minikube: build on local

#### Minikube
```
minikube start
```

```
minikube stop
```

##### Create docker images for APIs Service and push Docker Hub
```
$ cd backend
$ docker build -t {YOUR_DOCKER_HUB_USER}/kongfood .
$ docker push {YOUR_DOCKER_HUB_USER}/kongfood
```
If you want to use the image from Docker Hub, you can skip this step. If you run this step, you need to change the image field in the API deployment, kongfood-deployment.yaml file.

```
...
spec:
    containers:
        - name: kongfood
          image: {YOUR_DOCKER_HUB_USER}/kongfood:latest
...
```

##### Create Persitant Volume
```
$ cd kube
$ kubectl create -f mongo-pv.yml
$ kubectl get pv
```

##### Create Persitant Volume Claim
```
$ cd kube
$ kubectl create -f mongo-pvc.yml
$ kubectl get pvc
```
##### Create MongoDB Deployment
```
$ cd kube
$ kubectl create -f mongo-deployment.yml
```

##### Create MongoDB Service
```
$ cd kube
$ kubectl create -f mongo-service.yml
```

##### Create API Service Deployment
```
$ cd kube
$ kubectl create -f kongfood-deployment.yml
```

##### Create API Service
```
$ cd kube
$ kubectl create -f kongfood-service.yml
```

##### Create API HPA
```
$ cd kube
$ kubectl create -f kongfood-hpa.yml
```

##### Check Services
```
$ kubectl describe services
```

##### GET API Service
```
$ minikube service kongfood --url
```

### Notes
- The url for the API service must be added to the variable `host` in the postman collection file [here](/backend/src/main/resources/Tech_Challenge.postman_collection.json)

## References
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)
- [Kubernetes](https://kubernetes.io/)
- [Minikube](https://minikube.sigs.k8s.io/docs/)
- [Docker](https://www.docker.com/)