data "kubectl_filename_list" "clickhouse" {
  pattern = "./manifests/clickhouse/*.yaml"
}

data "kubectl_filename_list" "mongo" {
  pattern = "./manifests/mongo/*.yaml"
}