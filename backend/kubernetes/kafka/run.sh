helm install kafka oci://registry-1.docker.io/bitnamicharts/kafka \
  --set controller.replicaCount=2 \
  --set broker.replicaCount=0 \
  --set "podLabels.app\.pod=kafka" \
  --set controller.persistence.size=100Mi \
  --set broker.persistence.size=100Mi \
  --set controller.logPersistence.size=100Mi \
  --set broker.logPersistence.size=100Mi \
  --set listeners.client.client.protocol=SASL \
  --set "sasl.client.users[0]=kafka-user" \
  --set "sasl.client.passwords[0]=kafka-password"

 # https://github.com/bitnami/charts/tree/main/bitnami/kafka

 #  kafka.default.svc.cluster.local
#
#
#** Please be patient while the chart is being deployed **
#
#Kafka can be accessed by consumers via port 9092 on the following DNS name from within your cluster:
#
#    kafka.default.svc.cluster.local
#
#Each Kafka broker can be accessed by producers via port 9092 on the following DNS name(s) from within your cluster:
#
#    kafka-controller-0.kafka-controller-headless.default.svc.cluster.local:9092
#    kafka-controller-1.kafka-controller-headless.default.svc.cluster.local:9092
#    kafka-controller-2.kafka-controller-headless.default.svc.cluster.local:9092
#
#The CLIENT listener for Kafka client connections from within your cluster have been configured with the following security settings:
#    - SASL authentication
#
#To connect a client to your Kafka, you need to create the 'client.properties' configuration files with the content below:
#
#security.protocol=SASL_PLAINTEXT
#sasl.mechanism=SCRAM-SHA-256
#sasl.jaas.config=org.apache.kafka.common.security.scram.ScramLoginModule required \
#    username="user1" \
#    password="$(kubectl get secret kafka-user-passwords --namespace default -o jsonpath='{.data.client-passwords}' | base64 -d | cut -d , -f 1)";
#
#To create a pod that you can use as a Kafka client run the following commands:
#
#    kubectl run kafka-client --restart='Never' --image docker.io/bitnami/kafka:3.5.1-debian-11-r61 --namespace default --command -- sleep infinity
#    kubectl cp --namespace default /path/to/client.properties kafka-client:/tmp/client.properties
#    kubectl exec --tty -i kafka-client --namespace default -- bash
#
#    PRODUCER:
#        kafka-console-producer.sh \
#            --producer.config /tmp/client.properties \
#            --broker-list kafka-controller-0.kafka-controller-headless.default.svc.cluster.local:9092,kafka-controller-1.kafka-controller-headless.default.svc.cluster.local:9092,kafka-controller-2.kafka-controller-headless.default.svc.cluster.local:9092 \
#            --topic test
#
#    CONSUMER:
#        kafka-console-consumer.sh \
#            --consumer.config /tmp/client.properties \
#            --bootstrap-server kafka.default.svc.cluster.local:9092 \
#            --topic test \
#            --from-beginning
