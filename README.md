### Dictionary app 

Deployable Dictionary app to be used as part of [Writing Test-Driven Apps with http4k](/http4k/Writing_Test_Driven_Apps_with_http4k) workshop.

## Pre-requisites

* Working AWS account
* Pulumi [installed](https://www.pulumi.com/docs/get-started/install/)
* A [user](https://aws.amazon.com/iam/) with permissions to manage resources
* User credentials configured in a `http4k-lambda-demo` [CLI profile](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-profiles.html): 

*~/.aws/config*:

```
[profile http4k-lambda-demo]
region = eu-west-2
output = json
```
*~/.aws/credentials*:

```
[http4k-lambda-demo]
aws_access_key_id = <your key>
aws_secret_access_key = <your secret>
```

## Running it

```bash
./gradlew dictionary-function:buildLambdaZip
pulumi up --stack dev
```

The deployed URL will be printed at the end of the run. You can test the deployed lambda with: 
```bash
curl $(pulumi stack --stack dev output publishedUrl) 
```

## Cleaning up

```bash
pulumi destroy --stack dev
```
