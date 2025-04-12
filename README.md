Start Local

mvn clean install && mvn spring-boot:run
mvn clean install && java -jar target/*.jar

Hello API
curl http://localhost:8080/api/hello

curl http://localhost:8080/api/signup \    
  -H "Content-Type: application/json" \
  -d '{"email":"mixstys1@ceruleasn.com", "password":"starmie123", "name":"Misty"}' \
  -v

Docker
docker run --platform=linux/amd64 jordanurbaezlu/next-pokedex-java-backend

docker buildx build --platform=linux/amd64 -t jordanurbaezlu/next-pokedex-java-backend .

docker push jordanurbaezlu/next-pokedex-java-backend:latest

docker run -p 9090:8080 \
  --platform=linux/amd64 \
  --dns=8.8.8.8 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://db.gscfmyhzfecxdzgbeqod.supabase.co:5432/postgres?sslmode=require" \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=IA3WQM1dO1h5kZWX \
  jordanurbaezlu/next-pokedex-java-backend:latest