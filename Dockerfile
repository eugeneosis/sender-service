FROM bellsoft/liberica-openjdk-alpine-musl
COPY ./target/sender-service-1.0.0.jar .
CMD ["java","-jar","sender-service-1.0.0.jar"]

