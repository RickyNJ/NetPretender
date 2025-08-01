# 🧪 Network Device Mocking over SSH

This project runs a Java-based mock network device inside a Docker container that you can SSH into. The behavior is controlled via a YAML configuration file.

## 🚀 Getting Started

To start the SSH-enabled container:

    docker-compose up -d

You can then SSH into it via:

    ssh cliuser@localhost -p 22

Each exposed SSH port can correspond to a different mock device or behavior, configured using sshd_config with Match LocalPort.

---

## ⚙️ Live Configuration Updates

To update the command mappings without rebuilding the image, run:

    docker cp ./path/to/commands.yml <container-id>:/opt/configs/commands.yml

Replace <container-id> with your actual container name or ID (e.g., ssh-cli).

---

## 📁 File Structure

resources/
├── commands.yml         # Defines command-response mappings
├── start-cli            # Launches the CLI logic
├── sshd_config          # SSH server config with ForceCommand rules
└── target/
└── your-app.jar     # The compiled Java application

---

## 📜 Features

### ✅ Implemented

- [x] YAML-based command mappings
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

commands:
show version:
output:
- "Device OS v1.2.3"
- "Serial: ABCD1234"
delay: 500  # in milliseconds

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

