Start Local

mvn clean install && mvn spring-boot:run
mvn clean install && java -jar target/*.jar

Hello API
curl http://localhost:8080/api/hello

Docker
docker run --platform=linux/amd64 jordanurbaezlu/next-pokedex-java-backend
docker buildx build --platform=linux/amd64 -t jordanurbaezlu/next-pokedex-java-backend .
docker push jordanurbaezlu/next-pokedex-java-backend:latest
docker run -p 8080:8080 jordanurbaezlu/next-pokedex-java-backend:latest

