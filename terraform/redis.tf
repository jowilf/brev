resource "helm_release" "redis" {
  name  = "redis"
  chart = "oci://registry-1.docker.io/bitnamicharts/redis-cluster"

  set {
    name  = "fullnameOverride"
    value = "redis-cluster"
  }

  set {
    name  = "cluster.nodes"
    value = "6"
  }

  set {
    name  = "cluster.replicas"
    value = "1"
  }

  set {
    name  = "redis.podLabels.app\\.pod"
    value = "redis"
  }

  set {
    name  = "persistence.size"
    value = "100Mi"
  }

  set {
    name  = "global.redis.password"
    value = "redis-password"
  }
  set {
    name  = "password"
    value = "redis-password"
  }
}