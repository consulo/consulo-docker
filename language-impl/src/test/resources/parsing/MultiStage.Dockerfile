FROM golang AS builder
RUN go build

FROM alpine
COPY --from=builder app app
EXPOSE 8080
ENTRYPOINT ["app"]
