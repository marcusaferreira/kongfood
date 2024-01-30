# Kong API
O seguinte documento descreve a implantação de um serviço básico de API Spring e pilha da web MongoDB no Kubernetes. Atualmente, a API não é exposta à Internet, mas pode ser acessada via encaminhamento de porta e o MongoDB não usa replica sets. As requisições para o MongoDB são feitas somente através de serviços internos do cluster Kubernetes.

## Descrição
A API é uma aplicação Spring Boot simples que expõe uma API REST para resolver o problema proposto no TechChallenge do curso de especialização FIAP em Arquitetura de Software.

## Requisitos
```
 Java 17
 Gradle 7.5.1
 Docker 4.23.0 ou superior
 Minikube 1.32.0 ou superior
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
Se você quiser usar a imagem do Docker Hub, pode pular esta etapa, caso execute esse passo é necessário alterar o campo image no deployment da API, arquivo kongfood-deployment.yaml.

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
$ kubectl create -f mongo-pv.yaml
$ kubectl get pv
```

##### Create Persitant Volume Claim
```
$ cd kube
$ kubectl create -f mongo-pvc.yaml
$ kubectl get pvc
```
##### Create MongoDB Deployment
```
$ cd kube
$ kubectl create -f mongo-deployment.yaml
```

##### Create MongoDB Service
```
$ cd kube
$ kubectl create -f mongo-service.yaml
```

##### Create API Service Deployment
```
$ cd kube
$ kubectl create -f kongfood-deployment.yaml
```

##### Create API Service
```
$ cd kube
$ kubectl create -f kongfood-service.yaml
```

##### Create API HPA
```
$ cd kube
$ kubectl create -f kongfood-hpa.yaml
```

##### Check Services
```
$ kubectl describe services
```

##### GET API Service
```
$ minikube service kongfood --url
```

### Notas
- A url para o serviço da API deve ser adicionada à variável `host` no arquivo de coleção do postman [aqui](/backend/src/main/resources/Tech_Challenge.postman_collection.json)

## Referências
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)
- [Kubernetes](https://kubernetes.io/)
- [Minikube](https://minikube.sigs.k8s.io/docs/)
- [Docker](https://www.docker.com/)

## Arquitetura
![Arquitetura](/backend/src/main/resources/arquitetura-infraestrutura-tech-challenge-fase-2.jpg)
[VIDEO] (https://youtu.be/_pq6CPiOJUc)