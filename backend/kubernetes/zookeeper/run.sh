helm install zookeeper oci://registry-1.docker.io/bitnamicharts/zookeeper \
 --set replicaCount=2 \
 --set tickTime=2000 \
 --set "podLabels.app\.pod=zookeeper" \
 --set persistence.size=100Mi \
 --set persistence.dataLogDir.size=100Mi

# https://artifacthub.io/packages/helm/bitnami/zookeeper
#
# ZooKeeper can be accessed via port 2181 on the following DNS name from within your cluster:
#
#    zookeeper.default.svc.cluster.local
#
# To connect to your ZooKeeper server run the following commands:
#
#    export POD_NAME=$(kubectl get pods --namespace default -l "app.kubernetes.io/name=zookeeper,app.kubernetes.io/instance=zookeeper,app.kubernetes.io/component=zookeeper" -o jsonpath="{.items[0].metadata.name}")
#    kubectl exec -it $POD_NAME -- zkCli.sh
#
# To connect to your ZooKeeper server from outside the cluster execute the following commands:
#
#    kubectl port-forward --namespace default svc/zookeeper 2181:2181 &
#    zkCli.sh 127.0.0.1:2181
