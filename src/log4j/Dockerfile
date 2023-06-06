FROM maven as builder 

COPY . /usr/src/poc
WORKDIR /usr/src/poc
RUN mvn clean && mvn package

FROM golang:latest as entrypoint

WORKDIR /app
COPY src/entrypoint.go .
RUN go build entrypoint.go

FROM debian:9

COPY --from=entrypoint /app/entrypoint /bin
ENV exploit=false

USER 1001

COPY --from=builder /usr/src/poc/target/log4j-rce-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/app-root/src/

ENTRYPOINT /bin/entrypoint


