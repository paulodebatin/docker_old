# Dicas Docker:

1) Imagens

### listar 
docker image ls
docker images
docker images | grep "nome_imagem"


### Construi uma imagem a partir do Dockerfile
Criar uma pasta e entrar nela
Criar o arquivo Dockerfile
docker build -t <paulodebatin/nome_imagem:versao> .

Para subir para docker Hub:
docker login
	- informe user/pass
docker push <nome_imagem:versao>

### remover
docker image rmi <image_id>                         -- remover uma imagem
docker rmi $(docker images -q) -f                   -- remover todas as imagens

### renomear
docker tag <id da imagem> <novo nome da imagem> = renomear uma imagem

### ver imagens no docker hub
docker search paulodebatin

### detalhes da imagem
docker inspect <nome_imagem> 

### history da imagem
docker history <nome_imagem>

-- subir uma imagem para o dockerhub
docker login = logar no docker hub
docker push paulodebatin/<nome da imagem>

-- baixar uma imagem
docker login = logar no docker hub
docker pull paulodebatin/<nome da imagem>

-- executar uma imagem
docker run -ti <nome da imagem>


2) containers

### listar
docker container ls 	-- mosta container iniciados
docker container ls -a 	-- mosta container iniciados e parados	
docker ps -a                                    

### parar
docker stop <id_container>
docker stop $(docker ps -a -q)                  -- parar todos

### remover
docker container rm <id_container>
docker rm <id_container>
docker rm $(docker ps -a -q) -f                  -- remover todos
	
### ver os logs de aplicação rodando em um container
docker logs <id do container> -f
	