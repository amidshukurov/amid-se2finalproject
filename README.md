README details for terraform will be added later.

First of all we need to run jenkins inside minikube. This will help us to run k8s commands from jenkins.

docker image build -t myjenkins .

Name {myjenkins} is important because we will use it for k8s pod .

Then we should craete myslq database inside kubernetes cluste by running :

kubectl apply -f mysql-deployment.yaml

This will create mysql service which we will be able to connect to later.

We can connect to db by runnin following command :
kubectl run -it --rm --image=mysql:8.0 --restart=Never mysql-client -- mysql -h mysql -ppassword 
