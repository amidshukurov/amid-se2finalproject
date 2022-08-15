variable "region" {
  default     = "eu-west-1"
  description = "AWS Region"
}

variable "vpc_cidr" {
  description = "CIDR Block for VPC"
  default = "10.0.0.0/16"
}

variable "public_subnet_1_cidr" {
  description = "CIDR Block for Public Subnet 1"
  default = "10.0.1.0/24"
}

variable "public_subnet_2_cidr" {
  description = "CIDR Block for Public Subnet 2"
  default = "10.0.2.0/24"
}

variable "public_subnet_3_cidr" {
  description = "CIDR Block for Public Subnet 3"
  default = "10.0.3.0/24"
}

variable "private_subnet_1_cidr" {
  description = "CIDR Block for Private Subnet 1"
  default = "10.0.4.0/24"
}

variable "private_subnet_2_cidr" {
  description = "CIDR Block for Private Subnet 2"
  default = "10.0.5.0/24"
}

variable "private_subnet_3_cidr" {
  description = "CIDR Block for Private Subnet 3"
  default = "10.0.6.0/24"
}