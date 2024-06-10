FROM openjdk:21

WORKDIR /

COPY target/*.jar /simple-spring-webflux-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "simple-spring-webflux-app.jar"]
