FROM openjdk:11-jdk

# Convert build Args to runtime Env Vars
ARG VERSION
ENV version ${VERSION}

ARG SERVICE_NAME
ENV service_name ${SERVICE_NAME}

WORKDIR /
ARG JAR_FILE

ADD ${JAR_FILE} /app.jar

HEALTHCHECK \
	--interval=1m \
	--timeout=3s \
	--start-period=30s \
	--retries=2 \
	CMD wget -qO - localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT [ "java", "-server", "-jar", "app.jar"]
