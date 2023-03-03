FROM openjdk:19
COPY target/MyMetaServer-1.0-SNAPSHOT-jar-with-dependencies.jar /server2.jar
CMD java -jar /server2.jar
