FROM debian:bookworm-slim
RUN apt update && apt install -y openssh-server openjdk-17-jre && \
    useradd -m cliuser && echo 'cliuser:secret' | chpasswd && \
    mkdir /run/sshd

COPY ./resources/sshd_config /etc/ssh/sshd_config
COPY ./resources/start-cli /usr/local/bin/start-cli
COPY ./resources/commands.yml /opt/configs/commands.yml
COPY ./resources/NetPretender-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/cli/NetPretender-1.0-SNAPSHOT-jar-with-dependencies.jar
RUN chmod +x /usr/local/bin/start-cli

EXPOSE 22 23
CMD ["/usr/sbin/sshd", "-D"]
