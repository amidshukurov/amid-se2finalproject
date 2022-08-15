//Creating subnet group for database
resource "aws_db_subnet_group" "database-subnet-group" {
  name       = "database-subnet-group"
  subnet_ids = [aws_subnet.private-subnet-1.id, aws_subnet.private-subnet-2.id]

  tags = {
    Name = "database-subnet-group"
  }
}


//Create mysql database instance
resource "aws_db_instance" "mysql" {
  identifier             = "mysql-project-app"
  allocated_storage      = 10
  engine                 = "mysql"
  engine_version         = "5.7"
  instance_class         = "db.t2.micro"
  name                   = "phonebookDb"
  username               = "phonebookuser"
  password               = "phonebookpass"
  parameter_group_name   = "default.mysql5.7"
  skip_final_snapshot    = true
  db_subnet_group_name   = "database-subnet-group"
  publicly_accessible    = false

  vpc_security_group_ids = [aws_security_group.db_private_security_group.id, aws_security_group.allow-ssh.id]
}