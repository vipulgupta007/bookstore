FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
RUN echo "https://dl-cdn.alpinelinux.org/alpine/v3.19/main" > /etc/apk/repositorie
RUN apk add curl
COPY target/bookstore-1.0.0.jar /var/opt/
CMD java -jar /var/opt/bookstore-1.0.0.jar