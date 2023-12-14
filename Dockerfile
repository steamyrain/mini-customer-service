FROM eclipse-temurin:17-alpine

ARG JAR

WORKDIR /app

COPY ./build/libs/$JAR ./mini_employee.jar
COPY ./docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh
RUN ln -s /usr/local/bin/docker-entrypoint.sh .

EXPOSE 8080

ENTRYPOINT ["docker-entrypoint.sh"]
CMD java \
	-jar ./mini_employee.jar 
