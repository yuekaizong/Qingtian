FROM java:openjdk-8-alpine
VOLUME /tmp
ADD ./build/libs/cloud-eureka-server-1.0.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 18761