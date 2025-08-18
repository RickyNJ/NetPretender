FROM debian:bookworm-slim

# Dependencies
RUN apt update && apt install -y openssh-server openjdk-17-jre python3 python3-yaml&& \
    useradd -m cliuser && echo 'cliuser:secret' | chpasswd && \
    mkdir /run/sshd

# Configuration
COPY resources/redisson.yaml /opt/configs/redisson.yaml
COPY ./resources/commands.yml /opt/configs/commands.yml
COPY ./resources/responses/ /opt/configs/responses

COPY resources/startup/startup.py /usr/local/scripts/startup.py
COPY resources/startup/start-cli /usr/local/bin/start-cli
COPY resources/startup/NetPretender-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/cli/NetPretender-1.0-SNAPSHOT-jar-with-dependencies.jar

RUN python3 /usr/local/scripts/startup.py && chmod +x /usr/local/bin/start-cli

CMD ["/usr/sbin/sshd", "-D"]