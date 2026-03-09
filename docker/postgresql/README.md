## Database Development Environment

The project has a docker compose file ready to start an instance of postgresql 14.3:

In the docker path exec:

```console
$ docker compose -f docker-compose.database.yml up sisu-db -d
```

Depending of your local docker version the command to start the containers is, see [docker compose](https://docs.docker.com/compose/):


```console
$ docker-compose -f docker-compose.database.yml up sisu-db -d
```

To start mock server , in the docker path exec:

```console
$ docker compose -f docker-compose.mock.yml up -d
```

This will pull and get up a postgres instance accessible in localhost 5432 and as sisu-db host in the docker network sisu.

Credentials:

    - Username: postgres
    - Password: sisu
    - Database: sisu
    - Docker JDBC url: jdbc:postgresql://sisu-db:5432/sisu?user=postgres&password=sisu
    - Local JDBC url: jdbc:postgresql://localhost:5432/sisu?user=postgres&password=sisu


To create the initial database structure and populate with some data it can be created with docker:

```console
$ docker compose -f docker-compose.liquibase.yml up sisu-liquibase

Attaching to docker-sisu-liquibase-1
docker-sisu-liquibase-1  | ####################################################
docker-sisu-liquibase-1  | ##   _     _             _ _                      ##
docker-sisu-liquibase-1  | ##  | |   (_)           (_) |                     ##
docker-sisu-liquibase-1  | ##  | |    _  __ _ _   _ _| |__   __ _ ___  ___   ##
docker-sisu-liquibase-1  | ##  | |   | |/ _` | | | | | '_ \ / _` / __|/ _ \  ##
docker-sisu-liquibase-1  | ##  | |___| | (_| | |_| | | |_) | (_| \__ \  __/  ##
docker-sisu-liquibase-1  | ##  \_____/_|\__, |\__,_|_|_.__/ \__,_|___/\___|  ##
docker-sisu-liquibase-1  | ##              | |                               ##
docker-sisu-liquibase-1  | ##              |_|                               ##
docker-sisu-liquibase-1  | ##                                                ## 
docker-sisu-liquibase-1  | ##  Get documentation at docs.liquibase.com       ##
docker-sisu-liquibase-1  | ##  Get certified courses at learn.liquibase.com  ## 
docker-sisu-liquibase-1  | ##  Free schema change activity reports at        ##
docker-sisu-liquibase-1  | ##      https://hub.liquibase.com                 ##
docker-sisu-liquibase-1  | ##                                                ##
docker-sisu-liquibase-1  | ####################################################
docker-sisu-liquibase-1  | Starting Liquibase at 15:09:15 (version 4.11.0 #2708 built at 2022-05-23 15:17+0000)
docker-sisu-liquibase-1  | Liquibase Version: 4.11.0
docker-sisu-liquibase-1  | Liquibase Community 4.11.0 by Liquibase
docker-sisu-liquibase-1  | Running Changeset: scripts/v1/v1_1_create_packing_order_table.sql::raw::includeAll
docker-sisu-liquibase-1  | Running Changeset: scripts/v1/v1_2_create_packing_order_line_table.sql::raw::includeAll
docker-sisu-liquibase-1  | Liquibase command 'update' was executed successfully.
docker-sisu-liquibase-1 exited with code 0

```

## Deployment procedure
>  🔴  TODO:
