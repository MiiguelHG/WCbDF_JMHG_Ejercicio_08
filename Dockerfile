FROM openjdk:23-oracle
COPY target/EquiposDeportivos-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]