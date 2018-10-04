export POD_NAME=$(kubectl get pods --namespace default -l "app=my-concourse-web" -o jsonpath="{.items[0].metadata.name}")
kubectl port-forward --namespace default $POD_NAME 8080:8080 &
fly -t k8s login -c http://127.0.0.1:8080/ -k
fly -t k8s sp -p processor -c pipeline.yml -l credentials.yml
fly -t k8s up -p processor

app list --id processor:procs
╔══════╤════════════════════════╤════╤════╗
║source│       processor        │sink│task║
╠══════╪════════════════════════╪════╪════╣
║      │> procs-0.0.1-SNAPSHOT <│    │    ║
║      │procs-0.0.3-SNAPSHOT    │    │    ║
╚══════╧════════════════════════╧════╧════╝
