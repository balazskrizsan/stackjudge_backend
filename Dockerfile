FROM maven:3.8.3-openjdk-17

ENV GOOGLE_MAPS_KEY=qwe
ENV HEALTH_CHECK_ENV_VAR_TEST="env var test"
ENV HTTP_CORS_ORIGINS=https://localhost:4200
ENV IS_SEARCH_BOX_LOG_ENABLED=false
ENV JAVAX_NET_SSL_TRUST_STORE_PASSWORD=password
ENV REDIS_ASPECT_CACHE_ENABLED=false
ENV SERVER_ENV=LOCAL
ENV SERVER_PORT=8181
ENV SERVER_SSL_ENABLED=true
ENV SERVER_SSL_KEY_STORE=classpath:keystore/v2/sjdev.p12
ENV SERVER_SSL_KEY_STORE_PASSWORD=password
ENV SITE_DOMAIN=https://localhost:8181
ENV SITE_FRONTEND_DOMAIN=http://localhost:4200
ENV SJ_IDS_FULL_HOST=https://localhost:5001
ENV SPRING_DATASOURCE_PASSWORD=admin_pass
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:54320/stackjudge
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_REDIS_HOST=localhost
ENV SPRING_REDIS_PASSWORD=
ENV SPRING_REDIS_PORT=63790

COPY ./pom.xml /project/pom.xml
COPY ./settings.xml /project/settings.xml

RUN  cd /project && mvn verify clean --fail-never --settings settings.xml

COPY ./ /project

RUN  cd /project && mvn clean install --settings settings.xml -DskipTests=true -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

EXPOSE 8181

ENTRYPOINT ["java", "--enable-preview", "-jar", "/project/snapshot/stackjudge-snapshot.jar"]