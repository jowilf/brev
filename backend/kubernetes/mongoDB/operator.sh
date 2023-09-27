helm uninstall mongodb-operator
helm install mongodb-operator community-operator --repo https://mongodb.github.io/helm-charts \
 --namespace mongo --create-namespace
