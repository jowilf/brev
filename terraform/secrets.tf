resource "kubernetes_secret" "mysql-credentials" {
  metadata {
    name = "mysql-credentials"
  }
  data = {
    username = "root"
    password = "P4ssw0rd"
    host     = "%"
  }

  type = "kubernetes.io/basic-auth"
}
resource "kubernetes_secret" "clickhouse-credentials" {
  metadata {
    name = "clickhouse-credentials"
  }
  data = {
    username = "analytics"
    password = "password"
  }

  type = "kubernetes.io/basic-auth"
}

resource "kubernetes_secret" "jwt-secret" {
  metadata {
    name = "jwt-secret"
  }

  data = {
    secret = var.JWT_SECRET
  }
}
resource "kubernetes_secret" "users-service" {
  metadata {
    name = "users-service"
  }

  data = {
    "super-admin-password" = var.SUPER_ADMIN_PASSWORD
  }
}