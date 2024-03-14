# SETUP NATIVE BINARY

FROM ghcr.io/graalvm/native-image-community:21 AS build

WORKDIR /app

COPY . /app

RUN DB_PASS="$DB_PASS" DB_URL="$DB_URL" DB_USER="$DB_USER" \
    CACHE_TYPE="$CACHE_TYPE" REDIS_HOST="$REDIS_HOST" REDIS_PORT="$REDIS_PORT" \
    REDIS_DB="$REDIS_DB" REDIS_TIMEOUT="$REDIS_TIMEOUT" \
    ./mvnw -Pnative native:compile


# EXECUTE API

FROM alpine:3.19.1

ENV APP_HOME=/home/app/api

RUN addgroup -g 1001 app && adduser --disabled-password -G app -u 1000 app && \
    mkdir -p "$APP_HOME" && \
    chown -R app:app "$APP_HOME" \
    apk --no-cache add libc6-compat

COPY --from=build /app/target/rinha-backend "$APP_HOME"/rinha-backend

EXPOSE ${SERVER_PORT:-8080}

USER app

WORKDIR "$APP_HOME"

ENTRYPOINT ["./rinha-backend"]