FROM maven:3.8.1-openjdk-15

#RUN apt-get update
#COPY . /project
#RUN  cd /project && mvn clean install -DskipTests=true
#RUN  mv /root/.m2/repository/com/kbalazsworks/stackjudge/0.0.1-SNAPSHOT/stackjudge-0.0.1-SNAPSHOT.jar /root/build-0.0.1-SNAPSHOT.jar
COPY /target/stackjudge-0.0.1-SNAPSHOT.jar /root/

#run the spring boot application
ENTRYPOINT ["java", "--enable-preview", "-DSERVER_PORT=8183", "-jar","/root/stackjudge-0.0.1-SNAPSHOT.jar"]
