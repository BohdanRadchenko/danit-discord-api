#### [README](./README.md)

host/swagger-ui/index.html
host/graphiql

git config --global user.email "you@example.com"
git config --global user.name "Your Name"

git config --list
export GPG_TTY=$(tty)

git commit -S -m "YOUR_COMMIT_MESSAGE"

docker-compose up -d --build
docker logs "container_name"
docker logs --follow "container_name"
docker exec -it "container_name" sh

### links

- [Signing commits](https://docs.github.com/ru/authentication/managing-commit-signature-verification/signing-commits)
- [Generating a new GPG key](https://docs.github.com/en/authentication/managing-commit-signature-verification/generating-a-new-gpg-key)
- [error: gpg failed to sign the data](https://gist.github.com/paolocarrasco/18ca8fe6e63490ae1be23e84a7039374)
- [GPG failed to sign the data](https://candid.technology/error-gpg-failed-to-sign-the-data/)
- [docker webinar](https://www.youtube.com/watch?v=qS9KYTcWMlY)