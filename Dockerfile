FROM java:8-jre
MAINTAINER MY <mgrafx7@gmail.com>
ADD ./target/smlr.jar /app/
CMD ["java", "-jar", "/app/smlr.jar"]
EXPOSE 8080