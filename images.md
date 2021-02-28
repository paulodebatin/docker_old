# Images Docker:

1) Postgres
- Baixar a imagem: docker pull postgres
- Criar o diretório de dados: mkdir ${HOME}/database-dados
- cd ${HOME}/database-dados
- mkdir postgres
- Criar um container: sudo docker run -d --name dev-postgres -e POSTGRES_PASSWORD=123456 -v ${HOME}/database-dados/postgres/:/var/lib/postgresql/data  -p 5432:5432 postgres
- Com isso já pode ser usado no DBeaver com os dados:
	* localhost:5432
	* user: postgres
	* pass: 123456
	
- Você pode entrar no modo interativo:
	* Dentro do container: sudo docker exec -it dev-postgres bash
	* Entrando no Postgres: psql -h localhost -U postgres 
	* listando os databases: \l
	
- Para parar o container: sudo docker container stop dev-postgres
- Iniciar o container: sudo docker container start dev-postgres

	
	
2) Jenkis
sudo docker pull jenkins/jenkins:lts
sudo docker run -p 8080:8080 -p 50000:50000 --name dev-jenkis jenkins/jenkins:lts
http://localhost:8080 -> primeira vez irá solicitar password initial.... vai mostar essa senha no console
Depois instalar os plugins desejados
Por último, criar o usuário inicial. Informe: admin/123456


3) Mongo
docker pull mongo
docker run --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=123456 mongo
Connection String: mongodb://admin:123456@localhost:27017/admin
Para uma ide client: https://www.mongodb.com/try/download/compass


4) RabbitMQ
docker pull rabbitmq
mkdir -p /docker/rabbitmq/data
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 --restart=always  --hostname rabbitmq-master \
 -v /docker/rabbitmq/data:/var/lib/rabbitmq \
 rabbitmq:$VERSAO
 
Para acessar a interface: http://localhost:15672/
Usuário/Senha: guest
 
 
5) Elasticsearch
docker pull docker.elastic.co/elasticsearch/elasticsearch:7.10.1
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.10.1
Documentação: https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html

6) Redis
docker pull redis
docker run -d -p 6379:6379 --name redis1 redis
Para entrar no modo interativo: docker exec -it redis1 sh

7) Consul
docker pull consul
docker run  -p 8500:8500 -p 8600:8600/udp --name=consul consul:latest agent -server -bootstrap -ui -client=0.0.0.0
Para acessar a interface: http://localhost:8500/

8) Kafka + Zookeeper
docker-compose.yml:

version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    build: .
    ports:
      - "9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 0.0.0.0
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

docker-compose up
Material de: https://jaceklaskowski.gitbooks.io/apache-kafka/content/kafka-docker.html

