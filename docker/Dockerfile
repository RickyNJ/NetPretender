FROM debian:bookworm-slim
RUN apt update && apt install -y openssh-server openjdk-17-jre python3 && \
    useradd -m cliuser && echo 'cliuser:secret' | chpasswd && \
    mkdir /run/sshd

COPY resources/startup/startup.py /usr/local/scripts/startup.py
COPY resources/startup/sshd_config /etc/ssh/sshd_config
COPY resources/startup/start-cli /usr/local/bin/start-cli
COPY resources/startup/NetPretender-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/cli/NetPretender-1.0-SNAPSHOT-jar-with-dependencies.jar
COPY resources/redisson.yaml /opt/configs/redisson.yaml
COPY ./resources/commands.yml /opt/configs/commands.yml
COPY ./resources/responses/ /opt/configs/responses
RUN chmod +x /usr/local/bin/start-cli && pip install pyyaml

EXPOSE 22 23
CMD ["/usr/sbin/sshd", "-D"]