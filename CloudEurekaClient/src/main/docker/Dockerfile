FROM java:openjdk-8-alpine
VOLUME /tmp
#ADD cloud-eureka-client-1.0.jar app.jar
ADD ./docker/cloud-eureka-client-1.0.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]