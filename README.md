# BundestagsBot
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

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

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="https://github.com/Mk-arc"><img src="https://avatars0.githubusercontent.com/u/15717249?v=4" width="100px;" alt=""/><br /><sub><b>Mark</b></sub></a><br /><a href="https://github.com/bundestagsbot/bundestagsbotjava/commits?author=Mk-arc" title="Code">💻</a> <a href="https://github.com/bundestagsbot/bundestagsbotjava/pulls?q=is%3Apr+reviewed-by%3AMk-arc" title="Reviewed Pull Requests">👀</a></td>
    <td align="center"><a href="http://me.zaanposni.com"><img src="https://avatars0.githubusercontent.com/u/24491035?v=4" width="100px;" alt=""/><br /><sub><b>Yannick</b></sub></a><br /><a href="https://github.com/bundestagsbot/bundestagsbotjava/commits?author=zaanposni" title="Code">💻</a> <a href="#projectManagement-zaanposni" title="Project Management">📆</a> <a href="#infra-zaanposni" title="Infrastructure (Hosting, Build-Tools, etc)">🚇</a></td>
  </tr>
</table>

<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!