resource "kubernetes_deployment" "users-service" {
  metadata {
    name = "users-service-deployment"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        "app.pod" = "users-service"
      }
    }

    template {
      metadata {
        labels = {
          "app.pod" = "users-service"
        }
      }

      spec {
        container {
          image             = "brev/users-service"
          name              = "users-service"
          image_pull_policy = "Never"

          env {
            name  = "SERVER_PORT"
            value = 80
          }

          env {
            name  = "SPRING_PROFILES_ACTIVE"
            value = var.PROD_PROFILE
          }

          env {
            name  = "SPRING_DATASOURCE_URL"
            value = "jdbc:mysql:loadbalance://mysql-cluster.mysql:3306/${var.APP_NAME}?createDatabaseIfNotExist=true"
          }

          env {
            name = "SPRING_DATASOURCE_USERNAME"
            value_from {
              secret_key_ref {
                name = "mysql-credentials"
                key  = "username"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_PASSWORD"
            value_from {
              secret_key_ref {
                name = "mysql-credentials"
                key  = "password"
              }
            }
          }

          env {
            name  = "SPRING_JPA_HIBERNATE_DDL_AUTO"
            value = "create"
          }

          env {
            name = "JWT_SECRET"
            value_from {
              secret_key_ref {
                name = "jwt-secret"
                key  = "secret"
              }
            }
          }

          env {
            name  = "JWT_ISSUER"
            value = var.JWT_ISSUER
          }

          env {
            name  = "JWT_TOKEN_TTL"
            value = var.JWT_TOKEN_TTL
          }

          env {
            name  = "app.super-admin.username"
            value = var.SUPER_ADMIN_USERNAME
          }

          env {
            name = "app.super-admin.password"
            value_from {
              secret_key_ref {
                name = "users-service"
                key  = "super-admin-password"
              }
            }
          }

          port {
            container_port = 80
            name           = "api"
          }

          liveness_probe {
            http_get {
              path = "/actuator/health"
              port = 80
            }

            initial_delay_seconds = 3
            period_seconds        = 3
          }
        }
      }
    }
  }

  depends_on = [helm_release.mysql-cluster]
}

resource "kubernetes_service" "users-service" {
  metadata {
    name = "users-service"
  }
  spec {
    selector = {
      "app.pod" = "users-service"
    }

    port {
      port        = 8083
      target_port = "api"
    }

    type = "LoadBalancer"
  }
}