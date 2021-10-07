# minikube start

minikube is local Kubernetes, focusing on making it easy to learn and develop for Kubernetes.

All you need is Docker (or similarly compatible) container or a Virtual Machine environment, and Kubernetes is a single command away: `minikube start`

## Start your cluster
```
minikube start --kubernetes-version=v1.22.2 --memory=4096 --cpus=3 --disk-size=51200MB --extra-config=apiserver.Authorization.Mode=RBAC --image-mirror-country='cn' --image-repository='registry.cn-hangzhou.aliyuncs.com/google_containers'
```

## Interact with your cluster
```
minikube kubectl -- get po -A
alias kubectl="minikube kubectl --"
kubectl get po -A
```
```
kubectl proxy --port=8001 --address='0.0.0.0' --accept-hosts='^.*' &
minikube dashboard
```
[http://127.0.0.1:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/](http://127.0.0.1:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/)

## Deploy applications
```
#kubectl create deployment hello-minikube --image=k8s.gcr.io/echoserver:1.4
kubectl create deployment hello-minikube --image=registry.aliyuncs.com/google_containers/echoserver:1.4
kubectl expose deployment hello-minikube --type=NodePort --port=8080
kubectl get services hello-minikube
minikube service hello-minikube
kubectl port-forward service/hello-minikube 7080:8080
```

### LoadBalancer deployments
```
#kubectl create deployment balanced --image=k8s.gcr.io/echoserver:1.4
kubectl create deployment balanced --image=registry.aliyuncs.com/google_containers/echoserver:1.4
kubectl expose deployment balanced --type=LoadBalancer --port=8080
minikube tunnel
kubectl get services balanced
```

## Manage your cluster
```
minikube pause
minikube unpause
minikube stop
minikube config set memory 16384
minikube addons list
minikube start -p aged --kubernetes-version=v1.16.1
minikube delete --all
```

## Hello Minikube
### Create a Deployment
```
#kubectl create deployment hello-node --image=k8s.gcr.io/echoserver:1.4
kubectl create deployment hello-node --image=registry.aliyuncs.com/google_containers/echoserver:1.4
kubectl get deployments
kubectl get pods
kubectl get events
kubectl config view
```

### Create a Service
```
kubectl expose deployment hello-node --type=LoadBalancer --port=8080
kubectl get services
minikube service hello-node
```

### Enable addons
```
minikube addons list
minikube addons enable metrics-server
kubectl get pod,svc -n kube-system
minikube addons disable metrics-server
```

### Clean up
```
kubectl delete service hello-node
kubectl delete deployment hello-node
```
Optionally, stop the Minikube virtual machine (VM):
```
minikube stop
```
Optionally, delete the Minikube VM:
```
minikube delete
```

## References
- [minikube start](https://minikube.sigs.k8s.io/docs/start/)
- [Hello Minikube](https://kubernetes.io/docs/tutorials/hello-minikube/)