#!/bin/sh
echo "⏳ Waiting for Redis Cluster to be ready..."

while true; do
    CLUSTER_STATUS=$(redis-cli -h redis-node-1 -p 6379 cluster info | grep -c "cluster_state:ok")
    if [ "$CLUSTER_STATUS" -eq 1 ]; then
        echo "✅ Redis Cluster is ready!"
        break
    else
        echo "❌ Redis Cluster is not ready yet. Retrying in 5 seconds..."
        sleep 5
    fi
done

echo "🚀 Redis Cluster is ready. Proceeding with Spring Boot application..."
exec java -jar -Dspring.profiles.active=prod /spoteditor.jar
