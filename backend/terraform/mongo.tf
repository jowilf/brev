resource "kubernetes_namespace" "mongo" {
  metadata {
    name = "mongo"
  }
}
resource "helm_release" "mongodb-kubernetes-operator" {
  name       = "mongodb-kubernetes-operator"
  repository = "https://mongodb.github.io/helm-charts"
  chart      = "community-operator"
  namespace  = kubernetes_namespace.mongo.metadata[0].name

  set {
    name  = "operator.name"
    value = "mongo-operator"
  }

  set {
    name  = "operator.resources.limits.cpu"
    value = "100m"
  }

  set {
    name  = "operator.resources.limits.memory"
    value = "100Mi"
  }

  set {
    name  = "operator.resources.requests.cpu"
    value = "100m"
  }

  set {
    name  = "operator.resources.requests.memory"
    value = "100Mi"
  }
  set {
    name  = "operator.namespace"
    value = "mongo"
  }
}
resource "kubectl_manifest" "mongo-cluster" {
  override_namespace = kubernetes_namespace.mongo.metadata[0].name
  depends_on         = [helm_release.mongodb-kubernetes-operator]
  count              = length(data.kubectl_filename_list.mongo.matches)
  yaml_body          = file(element(data.kubectl_filename_list.mongo.matches, count.index))
}