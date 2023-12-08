# Bare Maven template for AWS SDK for Java 2.x

This project includes AWS SDK 2 https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide dependencies and uses credentials from your default AWS profile.

## usage

`mvn exec:java` fetches the current user's ARN (`aws sts get-caller-identity`) and the access key.
