resource "kubernetes_deployment" "url-service" {
  metadata {
    name = "url-service-deployment"
  }

  spec {
    replicas = 2

    selector {
      match_labels = {
        "app.pod" = "url-service"
      }
    }

    template {
      metadata {
        labels = {
          "app.pod" = "url-service"
        }
      }

      spec {
        container {
          image             = "brev/url-service"
          name              = "url-service"
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
            name  = "spring.data.mongodb.host"
            value = "mongodb-svc.mongo.svc.cluster.local"
          }


          env {
            name  = "spring.data.mongodb.port"
            value = "27017"
          }

          env {
            name  = "spring.data.mongodb.database"
            value = var.APP_NAME
          }

          env {
            name  = "spring.data.mongodb.username"
            value = "url-service"
          }

          env {
            name  = "spring.data.mongodb.password"
            value = "mongo-password"
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
            name  = "spring.kafka.producer.security.protocol"
            value = "SASL_PLAINTEXT"
          }
          env {
            name  = "spring.kafka.producer.properties.sasl.mechanism"
            value = "SCRAM-SHA-256"
          }

          env {
            name  = "spring.kafka.producer.properties.max.poll.records"
            value = "5"
          }

          env {
            name  = "spring.kafka.producer.properties.sasl.jaas.config"
            value = "org.apache.kafka.common.security.scram.ScramLoginModule required username=kafka-user password=kafka-password;"
          }


          env {
            name  = "spring.kafka.admin.security.protocol"
            value = "SASL_PLAINTEXT"
          }
          env {
            name  = "spring.kafka.admin.properties.sasl.mechanism"
            value = "SCRAM-SHA-256"
          }

          env {
            name  = "spring.kafka.admin.properties.sasl.jaas.config"
            value = "org.apache.kafka.common.security.scram.ScramLoginModule required username=kafka-user password=kafka-password;"
          }


          env {
            name  = "spring.data.redis.cluster.nodes"
            value = "redis-cluster.default.svc.cluster.local:6379"
          }

          env {
            name  = "spring.data.redis.password"
            value = "redis-password"
          }

          env {
            name  = "app.kgs-base-url"
            value = "http://kgs-service.default.svc.cluster.local:8081"
          }
          env {
            name  = "appi.kgs-base-url"
            value = "http://kgs-service.default.svc.cluster.local:8081"
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

  depends_on = [helm_release.kafka, helm_release.redis, kubectl_manifest.mongo-cluster]
}

resource "kubernetes_service" "url-service" {
  metadata {
    name = "url-service"
  }
  spec {
    selector = {
      "app.pod" = "url-service"
    }

    port {
      port        = 8082
      target_port = "api"
    }

    type = "LoadBalancer"
  }
}