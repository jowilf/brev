helm install redis oci://registry-1.docker.io/bitnamicharts/redis-cluster \
  --set cluster.nodes=6 \
  --set cluster.replicas=1 \
  --set "redis.podLabels.app\.pod=redis" \
  --set persistence.size=100Mi \
  --set fullnameOverride=redis-cluster \
  --set global.redis.password=redis-password

#
#To get your password run:
#    export REDIS_PASSWORD=$(kubectl get secret --namespace "default" redis-cluster -o jsonpath="{.data.redis-password}" | base64 -d)
#
#You have deployed a Redis&reg; Cluster accessible only from within you Kubernetes Cluster.INFO: The Job to create the cluster will be created.To connect to your Redis&reg; cluster:
#
#1. Run a Redis&reg; pod that you can use as a client:
#kubectl run --namespace default redis-cluster-client --rm --tty -i --restart='Never' \
# --env REDIS_PASSWORD=$REDIS_PASSWORD \
#--image docker.io/bitnami/redis-cluster:7.2.1-debian-11-r0 -- bash
#
#2. Connect using the Redis&reg; CLI:
#
#redis-cli -c -h redis-cluster -a $REDIS_PASSWORD
