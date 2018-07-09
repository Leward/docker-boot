This application shows how to deploy a Spring Boot application with Docker (**17.06.0+**) using: 
- Tomcat as a base image
- Docker Swarm
- Configuration

## Note on Oracle

This app uses Spring Data JPA. To use it with Oracle, [a JDBC driver](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html)
 is needed but available in the Maven Central. 
There two ways of addressing the issue: 
1. Put the ojdbc.jar in the project folder and copy it in the tomcat lib/ folder
2. Put the ojdbc.jar in the enterprise maven repo and add it as a dependency.

## Build and Run

### Build

```bash
mvn clean package
docker build . -t leward/docker-boot 
```

### Run

```bash
docker run --rm -p 8080:8080 --name=docker-boot leward/docker-boot
```

## Run with custom configuration

### As a regular container

#### With environment variables

```bash
docker run --rm \
    -p 8080:8080 \
    -e app_world=monde \
    --name=docker-boot \
    leward/docker-boot
```

### With an `application.properties` file

```bash
echo "app.hello=bonjour" > application.properties
docker run --rm \
    -p 8080:8080 \
    -v $(pwd)/application.properties:/usr/local/tomcat/application.properties \
    --name=docker-boot \
    leward/docker-boot
```

### With docker swarm

(Use `docker swarm init` to enable swarm mode)

Read: 
- [Swarm - How Docker manages configs](https://docs.docker.com/engine/swarm/configs/#how-docker-manages-configs)
- [Swarm - Manage sensitive data with Docker secrets](https://docs.docker.com/engine/swarm/secrets/)

Config vs Secrets

```bash
docker stack deploy -c config/dev/docker-compose.yml docker-boot
```

Verify the config has been loaded:

```
$ curl -s http://localhost:8080/hello
{"cwd":"/usr/local/tomcat","world":"dev","greeting":"bonjour dev!","hello":"bonjour"}
```

