# Dockerfile - totp service
FROM java:8-jre

ENV JAR_NAME customerweb-1.0-all.jar

COPY libs/$JAR_NAME /$JAR_NAME

EXPOSE 8080 8080

ENTRYPOINT ["java", "-jar", "/customerweb-1.0-all.jar"]
CMD []
