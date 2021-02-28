1) Entrar na pasta onde está o Dockerfile

2) Criar a imagem
docker build -t paulodebatin/spring-boot-docker .

3) Executar um container da imagem
docker run -p 8080:8080 paulodebatin/spring-boot-docker 