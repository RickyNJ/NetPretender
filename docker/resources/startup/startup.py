import yaml

# with open("/opt/configs/commands.yml") as stream:
with open("/opt/configs/commands.yml") as stream:
  try:
    data = yaml.safe_load(stream)
  except yaml.YAMLERROR as exc:
    print(exc)

ports = []
for d in data.get("devices", []):
  p = d.get("port")
  if isinstance(p, list):
    ports.extend(p)
  elif p is not None:
    ports.append(p)

lines = []

# Header / static part
lines.append("# SSH Server Configuration\n")
lines.append("# Listen on ports " + " ".join(str(p) for p in ports))
for p in ports:
  lines.append(f"Port {p}")
lines.append("")

# Root login + password authentication
lines.append("# Allow root login (for testing purposes)")
lines.append("PermitRootLogin yes\n")
lines.append("# Allow password authentication (ensure security is handled via other means in production)")
lines.append("PasswordAuthentication yes\n")

# Match blocks
lines.append("# ForceCommand to run the Java CLI with the correct port for each connection")
for p in ports:
  lines.append(f"Match LocalPort {p}")
  lines.append(f"    ForceCommand /usr/local/bin/start-cli {p}\n")

# Logging & tty
lines.append("# LogLevel for debugging")
lines.append("LogLevel INFO\n")
lines.append("# Enable TTY allocation (necessary for the CLI to work)")
lines.append("PermitTTY yes\n")

# Optional
lines.append("# Other SSH Configurations (Optional)")
lines.append("AllowUsers cliuser\n")

sshd_config = "\n".join(lines)

with open("/etc/ssh/sshd_config", "w") as f:
  f.write(sshd_config)

print("sshd_config generated!")