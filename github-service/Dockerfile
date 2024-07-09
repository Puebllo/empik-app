FROM maven:3.8.5-openjdk-17

WORKDIR /github-service
COPY . .
RUN mvn install -Dmaven.test.skip=true

CMD mvn spring-boot:run