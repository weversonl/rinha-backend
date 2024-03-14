<h1 align="center">
   Rinha Backend
</h1>

Project developed to participate in the "Rinha de Backend" challenge. The aim of the challenge is to create an API for
manipulating people's data, with cache, database and a load balancer to manage two instances, all in a limited
environment with 1.5vCPUs and 3GB of memory.

## Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Boot Webflux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc)
- [Spring Data Redis](https://spring.io/projects/spring-data-redis)
- [Docker](https://docs.docker.com/get-started/)
- [PostgreSQL](https://www.postgresql.org/docs/13/index.html)
- [Nginx](https://nginx.org/en/docs/)

## Practices adopted

- SOLID
- API REST
- Resource optimization
- Queries with Spring Data R2DBC
- Data caching for faster queries
- Dependency Injection
- Automated tests
- Containerization with docker
- Load Distribution with Load Balancer

## Get started

In the project, an [example environment variables file](docker/config/.env) will be available. The project will start using it, if you
want to change the database user and password, or some other value, feel free

0. Clone git repository with modules

       git clone https://github.com/WeversonL/rinha-backend.git
       cd rinha-backend

### Running the application with docker-compose

1. Start with docker-compose

       docker-compose up -d

You can access the API via load balancer, sending requests to localhost:9999. Due to resource limitations, it is
necessary to wait a while for the entire application to be up and running.

## API Endpoints

**The endpoints offered are based on those requested by the challenge!!**

To make the HTTP requests below, the [httpie](https://httpie.io) tool was used:

- Create a person

```
$ http POST :9999/pessoas apelido="nickname" nome="My Name" nascimento="2003-01-22" stack:='["Java", "C", "Python"]'

[
    {
        "id": "b987f790-7c92-42ca-ab99-2358adcd5e38",
        "nome": "My Name",
        "apelido": "nickname",
        "nascimento": "2003-01-22",
        "stack": [
            "Java",
            "C",
            "Python"
        ]
    }
]
```

- Get person by id

```
$ http GET :9999/pessoas/{id}

[
    {
        "id": "b987f790-7c92-42ca-ab99-2358adcd5e38",
        "nome": "My Name",
        "apelido": "nickname",
        "nascimento": "2003-01-22",
        "stack": [
            "Java",
            "C",
            "Python"
        ]
    }
]
```

- Get person by term

In this endpoint you can search for people by any term. It will check whether the term entered appears in any of the
columns. For example, knowing if there is someone in my bank who has a STRING with the value "John"

```
$ http GET ':9999/pessoas?t=nickname'

[
    {
        "id": "b987f790-7c92-42ca-ab99-2358adcd5e38",
        "nome": "My Name",
        "apelido": "nickname",
        "nascimento": "2003-01-22",
        "stack": [
            "Java",
            "C",
            "Python"
        ]
    }
]

$ http GET ':9999/pessoas?t=2003-01-22'

[
    {
        "id": "b987f790-7c92-42ca-ab99-2358adcd5e38",
        "nome": "My Name",
        "apelido": "nickname",
        "nascimento": "2003-01-22",
        "stack": [
            "Java",
            "C",
            "Python"
        ]
    }
]

$ http GET ':9999/pessoas?t=python'

[
    {
        "id": "b987f790-7c92-42ca-ab99-2358adcd5e38",
        "nome": "My Name",
        "apelido": "nickname",
        "nascimento": "2003-01-22",
        "stack": [
            "Java",
            "C",
            "Python"
        ]
    }
]

```

## License

`Rinha Backend` is [MIT licensed](LICENSE).