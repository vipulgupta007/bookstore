FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
RUN apk update
RUN apk add curl
COPY target/bookstore-1.0.0.jar /var/opt/
CMD java -jar /var/opt/bookstore-1.0.0.jar