#resource "helm_release" "kafka" {
#  name  = "kafka"
#  chart = "oci://registry-1.docker.io/bitnamicharts/kafka"
#
#  set {
#    name  = "controller.replicaCount"
#    value = "2"
#  }
#
#  set {
#    name  = "broker.replicaCount"
#    value = "0"
#  }
#
#  set {
#    name  = "podLabels.app\\.pod"
#    value = "kafka"
#  }
#
#  set {
#    name  = "controller.persistence.size"
#    value = "100Mi"
#  }
#
#  set {
#    name  = "broker.persistence.size"
#    value = "100Mi"
#  }
#
#  set {
#    name  = "controller.logPersistence.size"
#    value = "100Mi"
#  }
#
#  set {
#    name  = "broker.logPersistence.size"
#    value = "100Mi"
#  }
#
#  set {
#    name  = "listeners.client.client.protocol"
#    value = "SASL"
#  }
#
#  set {
#    name  = "sasl.client.users[0]"
#    value = "kafka-user"
#  }
#
#  set {
#    name  = "sasl.client.passwords[0]"
#    value = "kafka-password"
#  }
#}