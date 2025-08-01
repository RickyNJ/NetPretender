# 🧪 NetPretender - Network Device Mocking over SSH

This project runs a Java-based mock network device inside a Docker container that you can SSH into. The behavior is controlled via a YAML configuration file.


## 🚀 Getting Started

First clone this repository.<br>
Build the java application using maven, jdk17.
To start the SSH-enabled container:

    docker build path/to/dockerfile -t netpretender
    docker run --name netpretendercontainer -p <DESIRED_PORT>:<DESIRED_PORT>

You can then SSH into it with password "secret" via:

    ssh cliuser@localhost -p 22

Each exposed SSH port can correspond to a different mock device or behavior, configured using sshd_config with Match LocalPort.

---

## ⚙️ Live Configuration Updates

To update the command mappings without rebuilding the image, run:

    docker cp ./path/to/commands.yml <container-id>:/opt/configs/commands.yml

Replace <container-id> with your actual container name or ID (e.g., ssh-cli).

---

## 📁 File Structure

resources/<br>
├── commands.yml         # Defines command-response mappings<br>
├── start-cli            # Launches the CLI logic<br>
├── sshd_config          # SSH server config with ForceCommand rules<br>
└── target/<br>
└── app.jar     # The compiled Java application

---

## 📜 Features

### ✅ Implemented

- [x] YAML-based command mappings
- [x] Multiple devices mocking at the same time
- [x] Custom output formatting
    - Delays
    - Multi-part output
- [x] Variables in commands

### 🔧 In Progress / Planned

- [ ] Support for external response files
- [ ] Variables in output
- [ ] Mock device internal state
- [ ] Conditional outputs based on variables/state
- [ ] State referencing in outputs

---

## 🛠 How It Works

- The SSH server listens on multiple ports.
- Each port is matched in sshd_config to launch the mock app with specific arguments (e.g., device type).
- The Java CLI reads commands.yml and responds accordingly.
- The user interacts with the CLI over a TTY-enabled SSH session.

---

## 🧪 Example Command Mapping (commands.yml)

## 🧪 Example Command Mapping (commands.yml)
```yaml
devices:
  - name: modem
    port: 22
    vars:
    deviceId: "modem1"
    state: "enabled"
    commands:
      - command: show name
        response: hi my name is modem1
        delay: 1

      - command: show interfaces --all
        multiPartResponse:
          - "SHOWING INTERFACES"
          - "INTERFACE 1"
          - "INTERFACE 2"
          - "INTERFACE 3"
          - "INTERFACE 4"
        delay: 1

      - command: print interfaces --all
        responseFile: ./responses/show_interface_all.txt
        delay: 1

  - name: modem2
    port: 23
    vars:
    deviceId: "modem2"
    state: "enabled"
    commands:
      - command: show name
        response: hi my name is modem2

      - command: show interfaces --all
        responseFile: ./responses/show_interface_all.txt
        delay: 1

      - command: print interfaces --all
        responseFile: ./responses/show_interface_all.txt
        delay: 1

      - command: print interfaces ${var}
        response: showing variable interface
        allowed_values:
          - "hello"
          - "hey there"
          - "false"
```
---

### 🧩 Supported Features Demonstrated

- `response`: Simple static response
- `multiPartResponse`: Outputs multiple lines, simulating streaming or step-by-step device replies
- `responseFile`: Reads output from an external file (useful for large responses)
- `${var}`: Variable placeholder in the command input
- `allowed_values`: Validates that the variable input matches one of the defined values
- `delay`: Simulates response latency (in seconds)
- `$command`: (Proposed) Reserved variable for referencing the full raw user input — not implemented yet but planned

---

## 🧯 Troubleshooting

- Make sure your sshd_config is actually applied (check logs).
- Port conflicts on Windows may require adjusting exposed ports in docker-compose.yml.

---

## 📬 Contributions

Feature ideas and PRs are welcome! Especially around:
- State modeling
- Conditional logic
- More robust output templating

---

