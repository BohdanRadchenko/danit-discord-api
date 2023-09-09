docker-compose up -d --build
docker logs "container_name"
docker logs --follow "container_name"
docker exec -it "container_name" sh

# DISCORD-API - [DANIT java spring final project](https://github.com/BohdanRadchenko/danit-discord-api)

## Requirements

* Java 17g 
* docker

## Process:

### Git clone

```
git clone git@github.com:BohdanRadchenko/danit-discord-api.git
```

Enable custom git config
```
sh .gitconfig.sh
```

## Tests

Run unit tests with the commands below

```
mvn clean test
```

Run code style test with the commands below

```
mvn validate
```

### Rules
$ git commit -S -m "YOUR_COMMIT_MESSAGE" - Creates a signed commit


## links
[Signing commits](https://docs.github.com/ru/authentication/managing-commit-signature-verification/signing-commits)
[Generating a new GPG key](https://docs.github.com/en/authentication/managing-commit-signature-verification/generating-a-new-gpg-key)
[GPG failed to sign the data](https://candid.technology/error-gpg-failed-to-sign-the-data/)