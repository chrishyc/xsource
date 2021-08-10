FROM prom/prometheus
USER root
COPY docker_startup.sh /bin/
RUN addgroup -S -g 1000 work \
    && adduser -S -D -u 1000 -G work work
RUN chmod 755 /bin/docker_startup.sh

WORKDIR /etc/prometheus

ENTRYPOINT ["/bin/docker_startup.sh"]
# USER会对ENTRYPOINT中sh生效
#USER nobody
