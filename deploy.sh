cd backend && mvn package jib:dockerBuild -DskipTests && cd ..
cd web && ./build.sh && cd ..
docker compose -f compose/docker-compose.yml up -d
open http://localhost