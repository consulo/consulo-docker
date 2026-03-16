FROM ubuntu:latest
RUN echo hello
COPY src dest
WORKDIR app
CMD ["python", "app.py"]
