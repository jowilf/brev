#resource "helm_release" "zookeeper" {
#  name  = "zookeeper"
#  chart = "oci://registry-1.docker.io/bitnamicharts/zookeeper"
#
#  set {
#    name  = "replicaCount"
#    value = "1"
#  }
#
#  set {
#    name  = "tickTime"
#    value = "2000"
#  }
#
#  set {
#    name  = "podLabels.app\\.pod"
#    value = "zookeeper"
#  }
#
#  set {
#    name  = "persistence.size"
#    value = "100Mi"
#  }
#
#  set {
#    name  = "persistence.dataLogDir.size"
#    value = "100Mi"
#  }
#}