## Running Local
mvn clean package
docker build -t next-pokedex-java-backend .
docker run -p 8080:8080 next-pokedex-java-backend

## DOCKER: PUSH, PULL, TEST
docker tag next-pokedex-java-backend jordanurbaezlu/next-pokedex-java-backend:latest
docker push jordanurbaezlu/next-pokedex-java-backend:latest
docker run -p 8080:8080 jordanurbaezlu/next-pokedex-java-backend:latest

## Hello API
curl http://localhost:8080/api/hello

## Goodbye API
curl http://localhost:8080/api/goodbye

## Signup API
curl -v -X POST http://localhost:8080/api/signup -H "Content-Type: application/json" -d '{"email":"mixstysa1@ceruleasn.com", "password":"starmie123", "name":"Misty"}'

