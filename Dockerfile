FROM openjdk:11
EXPOSE 8080

ADD target/DeanerySimpleSpringBootApp-0.0.1-SNAPSHOT.jar DeanerySimpleSpringBootApp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "DeanerySimpleSpringBootApp-0.0.1-SNAPSHOT.jar"]