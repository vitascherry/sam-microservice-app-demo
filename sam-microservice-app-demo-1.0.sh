#!/usr/bin/env bash
export PROJECT=sam-microservice-app-demo
export STACK=SamMicroserviceAppStack

cd $HOME/IdeaProjects
git clone git@github.com:vitascherry/$PROJECT.git
cd $PROJECT

./mvnw clean package

aws s3 mb s3://$PROJECT
aws s3 cp $PROJECT_ROOT/accountservice/target/accountservice-1.0.jar s3://$PROJECT/target/accountservice-1.0.jar
aws s3 cp $PROJECT_ROOT/customerservice/target/customerservice-1.0.jar s3://$PROJECT/target/customerservice-1.0.jar
aws s3 cp $PROJECT_ROOT/orderservice/target/orderservice-1.0.jar s3://$PROJECT/target/orderservice-1.0.jar
aws s3 cp $PROJECT_ROOT/accountservice/AccountServiceStack.template s3://$PROJECT/resources/AccountServiceStack.template
aws s3 cp $PROJECT_ROOT/customerservice/CustomerServiceStack.template s3://$PROJECT/resources/CustomerServiceStack.template
aws s3 cp $PROJECT_ROOT/orderservice/OrderServiceStack.template s3://$PROJECT/resources/OrderServiceStack.template
aws s3 cp $PROJECT_ROOT/Main.template s3://$PROJECT/resources/Main.template
aws cloudformation create-stack --stack-name $STACK --template-url s3://$PROJECT/resources/Main.template --capabilities CAPABILITY_IAM --parameters S3Bucket=$PROJECT,S3Artifacts=accountservice-1.0.jar,customerservice-1.0.jar,orderservice-1.0.jar,S3Templates=AccountServiceStack.template,CustomerServiceStack.template,OrderServiceStack.template
aws cloudformation wait stack-create-complete --stack-name $STACK
aws cloudformation describe-stacks --stack-name $STACK --query Stacks[0].Outputs
