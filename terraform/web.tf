resource "kubernetes_deployment" "web" {
  metadata {
    name = "web-deployment"
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        "app.pod" = "web"
      }
    }

    template {
      metadata {
        labels = {
          "app.pod" = "web"
        }
      }

      spec {
        container {
          image             = "brev/web"
          name              = "web"
          image_pull_policy = "Never"




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
          }

          liveness_probe {
            http_get {
              path = "/"
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

resource "kubernetes_service" "web" {
  metadata {
    name = "web"
  }
  spec {
    selector = {
      "app.pod" = "web"
    }

    port {
      port        = 80
      target_port = 80
    }

    type = "LoadBalancer"
  }
}