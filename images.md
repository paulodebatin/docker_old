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
- mkdir -p /docker/rabbitmq
- docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 --restart=always  --hostname rabbitmq-master -v ~/docker/rabbitmq:/var/lib/rabbitmq rabbitmq:3-management
 
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
Depois executar: redis-cli
Alguns comandos:
- Limpar todas as keys: flushall
- Retornar todas as keys: KEYS *
- Retornar o valor de uma key: get <nome da key>
- Setar o valor de uma key: set <nome da key> "valor"
- Deletar uma(s) key(s): del key1 key2 key3
https://redis.io/commands/set


7) Consul
docker pull consul
docker run  -p 8500:8500 -p 8600:8600/udp --name=consul consul:latest agent -server -bootstrap -ui -client=0.0.0.0
Para acessar a interface: http://localhost:8500/


8) MemCached
docker run --name my-memcache -d memcached

Para testar: 
- ver o ip: docker inspect <número container>
- telnet <ip> 11211
	set Test 0 100 10 <enter>
		Digitar um valor para a chave "TEST", exemplo: JournalDev <enter> msg dada: STORED
    get Test 
		VALUE Test 0 10
		JournalDev
	
	quit <enter, para finalizar>
	
	https://www.journaldev.com/16/memcached-telnet-commands-example



9) MSSQL
sudo docker pull mcr.microsoft.com/mssql/server:2019-latest
sudo docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=9qW@1jyT#" -p 1433:1433 --name sql1 -h sql1 -d mcr.microsoft.com/mssql/server:2019-latest

Para entrar dentro do container: sudo docker exec -it sql1 "bash" 
Depois para entrar no mssql: /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "9qW@1jyT#"
Dai em diante por usar qualquer comando sql, por exemplo: SELECT Name from sys.Databases <enter> go <enter>

Sem ser docker:
sqlcmd -S localhost -U SA -P '9qW@1jyT#' RESTORE FILELISTONLY from DISK = '/home/paulo/Downloads/BancodadosApi/DES_EDUSOFTAPI_FILES.BAK'

sqlcmd -S localhost -U SA -Q "RESTORE DATABASE [DES_EDUSOFTAPI_FILES] FROM DISK = N'/home/paulo/Downloads/BancodadosApi/DES_EDUSOFTAPI_FILES.BAK' WITH FILE = 1, NOUNLOAD, REPLACE, RECOVERY, STATS = 5"

sqlcmd -S localhost -U SA -Q "RESTORE DATABASE [DES_PADRAO_MSG] FROM DISK = N'/home/paulo/Downloads/BancodadosApi/DES_PADRAO_MSG.BAK' WITH FILE = 1, NOUNLOAD, REPLACE, RECOVERY, STATS = 5"

	


sqlcmd -S localhost -U SA -Q "RESTORE DATABASE [DES_PADRAO_MSG] FROM DISK = N'/home/paulo/Downloads/BancodadosApi/DES_PADRAO_MSG.BAK' WITH FILE = 1, NOUNLOAD, REPLACE, NORECOVERY, STATS = 5"

	
10) Mysql
docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=9qW@1jyT# -d mysql:5.6
	


11) Kafka + Zookeeper
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


12) Kafka + Zookeeper + Control Center
Material de: https://github.com/codeedu/nest-kafka
Canal falando bastante sobre Kafka: https://www.youtube.com/c/DXLab/featured
docker-compose.yml:
version: '3'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
      - "9094:9094"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_INTER_BROKER_LISTENER_NAME: INTERNAL
      KAFKA_LISTENERS: INTERNAL://:9092,OUTSIDE://:9094
      KAFKA_ADVERTISED_LISTENERS: INTERNAL://kafka:9092,OUTSIDE://host.docker.internal:9094
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: INTERNAL:PLAINTEXT,OUTSIDE:PLAINTEXT
    extra_hosts:
      - "host.docker.internal:172.17.0.1"

  control-center:
    image: confluentinc/cp-enterprise-control-center:6.0.1
    hostname: control-center
    depends_on:
      - kafka
    ports:
      - "9021:9021"
    environment:
      CONTROL_CENTER_BOOTSTRAP_SERVERS: 'kafka:9092'
      CONTROL_CENTER_REPLICATION_FACTOR: 1
      PORT: 9021


13) Portainer
https://documentation.portainer.io/v2.0/deploy/ceinstalldocker/
https://www.youtube.com/watch?v=hz5F8vyTJr4
docker volume create portainer_data
docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce

A interface roda na porta 9000 e a api na 8000
Acessando: localhost:9000

14) SonarKube 
Material de: https://www.youtube.com/watch?v=VBjAf6KUhzY
docker.compose.yml:
version: "3"

services:
  sonarqube:
    image: sonarqube:8-community
    depends_on:
      - db
    environment:
      SONAR_JDBC_URL: jdbc:postgresql://db:5432/sonar
      SONAR_JDBC_USERNAME: sonar
      SONAR_JDBC_PASSWORD: sonar
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_extensions:/opt/sonarqube/extensions
      - sonarqube_logs:/opt/sonarqube/logs
    ports:
      - "9000:9000"
  db:
    image: postgres:12
    environment:
      POSTGRES_USER: sonar
      POSTGRES_PASSWORD: sonar
    volumes:
      - postgresql:/var/lib/postgresql
      - postgresql_data:/var/lib/postgresql/data

volumes:
  sonarqube_data:
  sonarqube_extensions:
  sonarqube_logs:
  postgresql:
  postgresql_data:

  --> Para acessar, htpp://localhost:9000

14) Nginx
docker pull nginx
docker run --name web-nginx -p 8083:80 -v /home/paulo/projetos/nginx:/usr/share/nginx/html:ro -d nginx