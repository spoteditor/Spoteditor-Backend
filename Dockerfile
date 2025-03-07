FROM openjdk:17-jdk

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

COPY spoteditor.jar spoteditor.jar

ENTRYPOINT ["/bin/sh", "-c", "/entrypoint.sh"]
