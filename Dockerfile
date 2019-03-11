FROM openjdk:11-jre-slim
LABEL "Mantainer"="slavko.recek@gmail.com"


COPY target/wealth-build-0.0.1-SNAPSHOT.jar .
EXPOSE "8080:8080"

ENTRYPOINT ["java", "-jar", "wealth-build-0.0.1-SNAPSHOT.jar"]