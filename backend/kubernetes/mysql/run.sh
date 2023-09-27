helm uninstall mysql
helm install mysql mysql-innodbcluster --repo https://mysql.github.io/mysql-operator/ \
        --namespace mysql
        --set credentials.root.user='root' \
        --set credentials.root.password='password' \
        --set credentials.root.host='%' \
        --set serverInstances=1 \
        --set routerInstances=1 \
        --set tls.useSelfSigned=true \
        --set datadirVolumeClaimTemplate.resources.requests.storage=100Mi



