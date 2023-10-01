resource "kubernetes_deployment" "kgs-service" {
  metadata {
    name = "kgs-service-deployment"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        "app.pod" = "kgs-service"
      }
    }

    template {
      metadata {
        labels = {
          "app.pod" = "kgs-service"
        }
      }

      spec {
        container {
          image             = "brev/kgs-service"
          name              = "kgs-service"
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
            name  = "APP_ZOOKEEPER_PATH"
            value = "/${var.APP_NAME}/kgs/range"
          }

          env {
            name  = "APP_RANGE_SIZE"
            value = var.APP_RANGE_SIZE
          }

          env {
            name  = "SPRING_CLOUD_ZOOKEEPER_CONNECT_STRING"
            value = "zookeeper.default.svc.cluster.local"

          }

#          resources {
#            limits = {
#              cpu    = "0.5"
#              memory = "128Mi"
#            }
#            requests = {
#              cpu    = "250m"
#              memory = "100Mi"
#            }
#          }

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
}

resource "kubernetes_service" "kgs-service" {
  metadata {
    name = "kgs-service"
  }
  spec {
    selector = {
      "app.pod" = "kgs-service"
    }

    port {
      port        = 8081
      target_port = "api"
    }

    type = "NodePort"
  }
}