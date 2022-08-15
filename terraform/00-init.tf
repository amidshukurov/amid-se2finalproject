//Connecting to aws
//Shared credentials file can changes from computer to computer
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

provider "aws" {
  profile = "default"
  region  = "eu-west-1"
  shared_credentials_file = "~/.aws/credentials"
}

