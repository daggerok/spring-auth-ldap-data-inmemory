docker-compose
==============

build and start LDAP server

```sh
docker-compose -f src/main/docker/docker-compose.yml up -d --force-recreate --build
```

stop

```sh
docker stop ldap-server
```

cleanup

```sh
docker-compose -f src/main/docker/docker-compose.yml down
```
