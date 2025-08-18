import yaml

with open("/opt/configs/commands.yml") as stream:
  try:
    print(yaml.safe_load(stream))
  except yaml.YAMLERROR as exc:
    print(exc)