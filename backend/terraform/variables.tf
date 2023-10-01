variable "APP_NAME" {
  default = "brev"
}
variable "APP_RANGE_SIZE" {
  default = "1000"
}
variable "PROD_PROFILE" {
  default = "production"
}

variable "KAFKA_METRIC_TOPICS" {
  default = "metrics"
}

variable "JWT_ISSUER" {
  default = "brev"
}
variable "JWT_TOKEN_TTL" {
  default = 3600
}
variable "JWT_SECRET" {
  default = "secreti"
}

variable "SUPER_ADMIN_USERNAME" {
  default = "admin@brev.com"
}
variable "SUPER_ADMIN_PASSWORD" {
  default = "admin1234"
}