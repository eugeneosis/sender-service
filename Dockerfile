FROM adoptopenjdk/openjdk8
ADD target/education-rabbit-producer-0.0.1.jar producer.jar
ENTRYPOINT ["java","-jar","producer.jar"]

