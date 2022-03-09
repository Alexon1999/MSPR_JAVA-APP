# Jenkins with Docker

This setup will create a container with an isolated docker engine and expose it.
The custom image is based on jenkins lts, will download the docker cli and connect to the docker engine.

# Apache Webserver

This custom image is based on ubuntu. It will do all the installations and configurations (apache2 and ssh).
```shell
docker ps
# go inside the ubuntu container
docker exec -it <container_id> bash
docker inspect <container_id>
```

## How to run

```shell
docker-compose up
```

🐀🐀🐀🐀🐀🐀