docker compose down || true
docker pull 891612578477.dkr.ecr.ap-northeast-2.amazonaws.com/spoteditor/latest
docker compose up -d --build
