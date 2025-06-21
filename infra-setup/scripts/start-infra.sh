#!/bin/bash

NETWORK_NAME="infra-net"

# Kiểm tra network đã tồn tại chưa
if ! docker network ls --format '{{.Name}}' | grep -wq "$NETWORK_NAME"; then
  echo "Network '$NETWORK_NAME' chưa tồn tại. Đang tạo mới..."
  docker network create "$NETWORK_NAME"
  echo "Tạo network '$NETWORK_NAME' thành công."
else
  echo "Network '$NETWORK_NAME' đã tồn tại, bỏ qua tạo mới."
fi

# Chạy docker-compose
echo "Starting database services (MySQL, Redis)..."
docker compose -f ../docker-compose-database.yaml up -d

echo "Starting Jenkins..."
docker compose -f ../docker-compose-jenkins.yaml up -d

echo "Starting Kafka and Zookeeper..."
docker compose -f ../docker-compose-kafka.yaml up -d

echo "Starting UI tools (Redis Insight, Kafka-UI)..."
docker compose -f ../docker-compose-ui.yaml up -d

echo "All infrastructure services are starting up. Please allow some time for them to be fully operational."
echo "You can check their status using 'docker ps'."