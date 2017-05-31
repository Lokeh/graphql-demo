FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/graphql-demo-0.0.1-SNAPSHOT-standalone.jar /graphql-demo/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/graphql-demo/app.jar"]
