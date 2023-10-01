resource "kubernetes_namespace" "mysql" {
  metadata {
    name = "mysql"
  }
}
resource "helm_release" "mysql-operator" {
  name       = "mysql-operator"
  repository = "https://mysql.github.io/mysql-operator"
  chart      = "mysql-operator"
  namespace  = kubernetes_namespace.mysql.metadata[0].name
}


resource "helm_release" "mysql-cluster" {
  name       = "mysql-cluster"
  repository = "https://mysql.github.io/mysql-operator/"
  chart      = "mysql-innodbcluster"
  namespace  = kubernetes_namespace.mysql.metadata[0].name

  depends_on = [helm_release.mysql-operator]

  set {
    name  = "credentials.root.user"
    value = kubernetes_secret.mysql-credentials.data.username
  }

  set {
    name  = "credentials.root.password"
    value = kubernetes_secret.mysql-credentials.data.password
  }

  set {
    name  = "credentials.root.host"
    value = kubernetes_secret.mysql-credentials.data.host
  }

  set {
    name  = "serverInstances"
    value = "1"
  }

  set {
    name  = "routerInstances"
    value = "1"
  }

  set {
    name  = "tls.useSelfSigned"
    value = "true"
  }

  set {
    name  = "datadirVolumeClaimTemplate.resources.requests.storage"
    value = "100Mi"
  }
}
