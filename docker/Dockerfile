# SETUP NATIVE BINARY

FROM ghcr.io/graalvm/native-image-community:21 AS build

WORKDIR /app

COPY ./app /app

RUN DB_URL=r2dbc:postgresql://localhost:5432/rinhadb DB_USER=rinha DB_PASS=rinha \
    CACHE_TYPE=redis REDIS_HOST=localhost REDIS_PORT=6379 \
    REDIS_DB=0 REDIS_TIMEOUT=60000 \
    ./mvnw -Pnative native:compile


# EXECUTE API

FROM alpine:3.19.1

ENV APP_HOME=/home/app/api

RUN addgroup -g 1001 app && adduser --disabled-password -G app -u 1000 app && \
    mkdir -p "$APP_HOME" && \
    chown -R app:app "$APP_HOME" && \
    apk --no-cache add libc6-compat

COPY --from=build /app/target/rinha-backend "$APP_HOME"/rinha-backend

EXPOSE ${SERVER_PORT:-8080}

USER app

WORKDIR "$APP_HOME"

ENTRYPOINT ["./rinha-backend"]