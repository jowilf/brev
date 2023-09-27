helm uninstall clickhouse-operator
helm install clickhouse-operator altinity-clickhouse-operator --repo https://docs.altinity.com/clickhouse-operator/ \
  --set fullnameOverride=clickhouse-operator
kubectl apply -f deployment.yaml