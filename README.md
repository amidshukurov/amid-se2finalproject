1.Run jenkins inside minikube
~minikube start
~$eval $(minikube driver docker)

it will create everything in minikube

~docker image build -t myjenkins .

Name {myjenkins} will use it for k8s pod .
~ kubectl appl -f jenkinf.yaml (inside this yaml myjenkins image will ve used)

2. Run run inlocal create myslq database inside kubernetes cluste by running :

~kubectl apply -f mysql-deployment.yaml

This will create mysql service and we will be able to connect to later.

We can connect to db by running following command :

~kubectl run -it --rm --image=mysql:8.0 --restart=Never mysql-client -- mysql -h mysql -ppassword

In the Jenkins cretae multi branching pipeline. Pipeline will be created for eac Backend and Frontend app.