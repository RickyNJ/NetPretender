This project is still in WIP

# 🧪 NetPretender - Single Container, Multi SSH Device Mocking.

Mock multiple ssh servers from a single docker container, by changing the behaviour of the container based on the port on which you connect to.

---

## 📜 Features

### ✅ Implemented

- [x] YAML-based command mappings
- [x] Multiple devices mocking at the same time
- [x] Custom output formatting
  - Delays
  - Multi-part output
- [x] Support for external response files
- [x] Variables in commands
- [x] Variables in output
- [x] State referencing in outputs

### 🔧 In Progress / Planned

- [ ] Dynamically assign and listen to ports defined in the YAML.
- [ ] Conditional outputs based on variables/state.
- [ ] Handle scenario variable has wrong value.
- [ ] Allowed values per variable instead of per command.
- [ ] Use configuration values as allowed_value for variables.
- [ ] Scripting support.

### 🔧 Other stuff:

- [ ] Docker compose for easier deployment.
- [ ] Easier setup.
- [ ] Maven setup to include new builds to the docker image.

---

## 🧪 Example Command Mapping (commands.yml)
```yaml
# List of devices.
devices:
  - name: modem
    # Dynamically assign ports, has to be unique to the device.
    port: 22
    # Configuration of the device. Works on a key, value basis.
    vars:
      ${deviceId}: "modem1"
      ${state}: "enabled"
    # Mocked commands.
    commands:
      # Use configurations in output.
      - command: show name
        response: hi my name is ${deviceId}
        # Set the response delay in seconds. Default is no delay.
        delay: 1
      # Print out the reponse in parts by defining a multiPartResponse
      - command: show interfaces --all
        multiPartResponse:
          - "SHOWING INTERFACES"
          - "INTERFACE 1"
          - "INTERFACE 2"
          - "INTERFACE 3"
          - "INTERFACE 4"
      # Use text files to store larger responses. You can reference configuration in here too.
      - command: print interfaces --all
        responseFile: ./responses/show_interface_all.txt
  # New device mocked on port 23.
  - name: modem2
    port: 23
    commands:
      # Use variables in commands and the responses, assign possible values.
      - command: print interfaces ${var}
        response: showing variable ${var} 
        allowed_values:
          - "hello"
          - "hey there"
          - "false"
```

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

## 🛠 How It Works

- The SSH server listens on multiple ports.
- Each port is matched in sshd_config to launch the mock app with specific arguments (e.g., device type).
- The Java CLI reads commands.yml and responds accordingly.
- The user interacts with the CLI over a TTY-enabled SSH session.

---

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

