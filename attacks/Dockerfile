FROM golang:alpine as builder

WORKDIR /app
COPY qrcode.go .
RUN go build qrcode.go

FROM scratch

COPY --from=builder /app/qrcode /entrypoint

USER 1001

entrypoint ["/entrypoint"]


