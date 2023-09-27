helm uninstall mysql-operator
helm install mysql-operator mysql-operator --repo https://mysql.github.io/mysql-operator/ \
  --namespace mysql --create-namespace
