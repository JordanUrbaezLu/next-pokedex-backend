## Running Local
mvn clean package
docker build -t next-pokedex-java-backend .
docker run -p 8080:8080 next-pokedex-java-backend

## DOCKER: PUSH, PULL, TEST
docker tag next-pokedex-java-backend jordanurbaezlu/next-pokedex-java-backend:latest
docker push jordanurbaezlu/next-pokedex-java-backend:latest
docker run -p 8080:8080 jordanurbaezlu/next-pokedex-java-backend:latest

## NEXT_POKEDEX_CONSUMER_ID
npci_d8f71b9d4ab643f8
