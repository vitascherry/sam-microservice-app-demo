#!/usr/bin/env bash
export PROJECT=sam-microservice-app-demo
export STACK=SamMicroserviceAppStack
export REGION=eu-central-1

./mvnw clean package

pip install --user --upgrade awscli

aws s3 mb s3://$PROJECT

aws s3 cp accountservice/target/accountservice-1.0.jar s3://$PROJECT/target/accountservice-1.0.jar
aws s3 cp customerservice/target/customerservice-1.0.jar s3://$PROJECT/target/customerservice-1.0.jar
aws s3 cp orderservice/target/orderservice-1.0.jar s3://$PROJECT/target/orderservice-1.0.jar
aws s3 cp accountservice/AccountServiceStack.template s3://$PROJECT/resources/AccountServiceStack.template
aws s3 cp customerservice/CustomerServiceStack.template s3://$PROJECT/resources/CustomerServiceStack.template
aws s3 cp orderservice/OrderServiceStack.template s3://$PROJECT/resources/OrderServiceStack.template
aws s3 cp Main.template s3://$PROJECT/resources/Main.template

aws cloudformation create-stack --stack-name $STACK --template-url http://s3-$REGION.amazonaws.com/$PROJECT/resources/Main.template --capabilities CAPABILITY_IAM --parameters ParameterKey=S3Bucket,ParameterValue=$PROJECT
aws cloudformation wait stack-create-complete --stack-name $STACK
aws cloudformation describe-stacks --stack-name $STACK --query Stacks[0].Outputs

#aws cloudformation update-stack --stack-name $STACK --template-url http://s3-$REGION.amazonaws.com/$PROJECT/resources/Main.template --parameters ParameterKey=S3Bucket,ParameterValue=$PROJECT
#aws cloudformation wait stack-update-complete --stack-name $STACK
#aws cloudformation describe-stacks --stack-name $STACK --query Stacks[0].Outputs

#aws cloudformation delete-stack --stack-name $STACK
#aws cloudformation wait stack-delete-complete --stack-name $STACK
#aws cloudformation describe-stacks
