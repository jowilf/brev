#resource "kubernetes_namespace" "clickhouse" {
#  metadata {
#    name = "clickhouse"
#  }
#}
#resource "helm_release" "clickhouse-operator" {
#  name       = "clickhouse-operator"
#  repository = "https://docs.altinity.com/clickhouse-operator"
#  chart      = "altinity-clickhouse-operator"
#  namespace  = kubernetes_namespace.clickhouse.metadata[0].name
#
#  set {
#    name  = "fullnameOverride"
#    value = "clickhouse-operator"
#  }
#}
#resource "kubectl_manifest" "clickhouse-cluster" {
#  override_namespace = kubernetes_namespace.clickhouse.metadata[0].name
#  depends_on         = [helm_release.clickhouse-operator]
#  count              = length(data.kubectl_filename_list.clickhouse.matches)
#  yaml_body          = file(element(data.kubectl_filename_list.clickhouse.matches, count.index))
#}