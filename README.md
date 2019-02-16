[![Build Status](https://travis-ci.org/h4ck4life/slack-post-cli.svg?branch=master)](https://travis-ci.org/h4ck4life/slack-post-cli)

### Description
The main motivation of this jar program is to be used within cli environment to post build status message to slack channel. Though however it could be used in any purposes as seem to fit.

### How to use
* Download latest release of jar file [from here](https://github.com/h4ck4life/slack-post-cli/releases/download/v0.0.2/slack-cli-0.0.2.jar)
* Before running the jar, please make sure to configure below environment variables:
	*  `SLACK_WEBHOOK_URL=https://hooks.slack.com/services/xxxxx/xxxxx/xxxxxxx`
	*  `SLACK_TOKEN=xoxp-xxxxxxxxxxxxxx`
* Run the jar file like below:
	* `java -jar slack-cli-0.0.1.jar`

### Example
* Simple text message:
```sh
java -jar slack-cli-0.0.1.jar -c dev -m "test message"
```
* Text message with attachment:
```sh
java -jar slack-cli-0.0.1.jar -c dev -m "test message" -f ./demo.txt
```
