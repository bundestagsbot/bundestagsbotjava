# BundestagsBot

### Discordbot for community discord of [BestOfBundestag](https://www.youtube.com/channel/UCkN8kMDOekn8uxxxsvwEnow).

Replacement for old Bundestagsbot written in Python.

See GitHub projects for a roadmap.<br/>
All help is welcome.<br/>
Please open a pull request on master if you want to implement something :)

## Setup

First setup your own config
```
git clone git@github.com:bundestagsbot/bundestagsbotjava.git
cd bundestagsbotjava
cp default-config.json config.json
nano config.json
```

## Build
```bash
mvn -f pom.xml clean package
cd target
java -jar bundestagsbot.jar
```

or all in one via:

```bash
mvn install exec:java
```

or use docker if you are familiar with it.
Using docker you will need to copy your own config using `docker cp`.


## Deployment
useful env vars:
- DISCORD_BOT_TOKEN
- CONFIG_PATH
