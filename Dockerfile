FROM openjdk:17.0.1
WORKDIR /opt/onlinequeue
COPY target/online-queue-0.0.1-SNAPSHOT.jar online-queue.jar
ENTRYPOINT [ "java", "-jar",  "online-queue.jar"]