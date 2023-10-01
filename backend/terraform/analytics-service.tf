resource "kubernetes_deployment" "analytics-service" {
  metadata {
    name = "analytics-service-deployment"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        "app.pod" = "analytics-service"
      }
    }

    template {
      metadata {
        labels = {
          "app.pod" = "analytics-service"
        }
      }

      spec {
        container {
          image             = "brev/analytics-service"
          name              = "analytics-service"
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
            value = "jdbc:ch://clickhouse-clickhouse-cluster.clickhouse/${var.APP_NAME}?createDatabaseIfNotExist=true"
          }

          env {
            name = "SPRING_DATASOURCE_USERNAME"
            value_from {
              secret_key_ref {
                name = "clickhouse-credentials"
                key  = "username"
              }
            }
          }

          env {
            name = "SPRING_DATASOURCE_PASSWORD"
            value_from {
              secret_key_ref {
                name = "clickhouse-credentials"
                key  = "password"
              }
            }
          }

          env {
            name  = "APP_KAFKA_TOPIC"
            value = var.KAFKA_METRIC_TOPICS
          }

          env {
            name  = "spring.kafka.bootstrap-servers"
            value = "kafka.default.svc.cluster.local:9092"
          }
          env {
            name  = "spring.kafka.consumer.security.protocol"
            value = "SASL_PLAINTEXT"
          }
          env {
            name  = "spring.kafka.consumer.properties.sasl.mechanism"
            value = "SCRAM-SHA-256"
          }

          env {
            name  = "spring.kafka.consumer.properties.sasl.jaas.config"
            value = "org.apache.kafka.common.security.scram.ScramLoginModule required username=kafka-user password=kafka-password;"
          }

          env {
            name  = "app.kgs-base-url"
            value = "kgs-service.default.svc.cluster.local"
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

resource "kubernetes_service" "analytics-service" {
  metadata {
    name = "analytics-service"
  }
  spec {
    selector = {
      "app.pod" = "analytics-service"
    }
    session_affinity = "ClientIP"

    port {
      port        = 8083
      target_port = "api"
    }

    type = "LoadBalancer"
  }
}