FROM java:8

VOLUME /ROOT

ADD ddemo.jar ddemo.jar

RUN bash -c 'touch /ddemo.jar'

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo 'Asia/Shanghai' >/etc/timezone

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ddemo.jar" ]
