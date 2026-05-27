FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /src
COPY . .
RUN mvn -B -DskipTests -pl server/zanata-server-spring -am package

FROM eclipse-temurin:25-jre
WORKDIR /opt/verbaria
COPY --from=build /src/server/zanata-server-spring/target/zanata-server-spring-*.jar /opt/verbaria/verbaria-server.jar
EXPOSE 8080
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/verbaria"
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /opt/verbaria/verbaria-server.jar"]
