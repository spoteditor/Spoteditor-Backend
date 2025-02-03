docker compose down || true
docker pull ${{ steps.login-ecr.outputs.registry }}/spoteditor:latest
docker compose up -d --build