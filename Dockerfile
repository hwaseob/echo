FROM java:8
RUN ["mvn", "package"]

WORKDIR /usr/local/netty_echo

COPY lib/*.jar ./lib/
COPY target/netty_echo-1.0.jar ./lib

EXPOSE 1111
ENTRYPOINT ["java","-classpath","lib/netty_echo-1.0.jar","EchoServer"]
