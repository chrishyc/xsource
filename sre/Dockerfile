FROM prom/prometheus

USER root


ENV SERVICE_HOME=/etc/prometheus/

RUN chown -R nobody:nobody /etc/prometheus/

RUN addgroup -S -g 1000 work \
    && adduser -S -D -u 1000 -G work work

RUN mkdir /home/work/prometheus

VOLUME /home/work/prometheus

ENV TARGETS_DIR /home/work/prometheus

COPY ./docker_startup.sh /bin/
RUN chmod 755 /bin/docker_startup.sh

ENTRYPOINT ["docker_startup.sh"]

CMD [ "--config.file=/etc/prometheus/prometheus.yml", \
                 "--storage.tsdb.path=/prometheus", \
                 "--web.console.libraries=/etc/prometheus/console_libraries", \
                 "--web.console.templates=/etc/prometheus/consoles" ]

WORKDIR $SERVICE_HOME

USER nobody
