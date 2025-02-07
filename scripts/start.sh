echo "--------------- 서버 배포 시작 -----------------"
cd /home/ubuntu/Spoteditor-Backend
docker compose down || true
docker pull 891612578477.dkr.ecr.ap-northeast-2.amazonaws.com/spoteditor:latest
docker compose up -d --build
echo "--------------- 서버 배포 끝 -----------------"