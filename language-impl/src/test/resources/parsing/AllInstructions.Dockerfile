# Base image
FROM ubuntu AS base

MAINTAINER test

ARG VERSION
ENV HOME="app"

LABEL name="value"

USER root
WORKDIR app

RUN echo hello
ADD src dest
COPY src dest

EXPOSE 8080

VOLUME ["/data"]
STOPSIGNAL SIGTERM

HEALTHCHECK NONE

ONBUILD RUN echo test

SHELL ["/bin/bash"]
ENTRYPOINT ["app"]
CMD ["start"]
