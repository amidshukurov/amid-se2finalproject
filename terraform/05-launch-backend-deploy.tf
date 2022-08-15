data "template_file" "backend" {
  template = file("files/backend-deploy.sh")

  vars = {
    msql_url = "jdbc:mysql://${aws_db_instance.mysql.address}:${aws_db_instance.mysql.port}/${aws_db_instance.mysql.name}"
    msql_username = aws_db_instance.mysql.username
    msql_password = aws_db_instance.mysql.password
  }
  depends_on = ["aws_db_instance.mysql"]
}

resource "aws_launch_template" "launch-backend" {
  name = "lt-backend"

  instance_type = "t2.micro"

  instance_market_options {
    market_type = "spot"
  }

  image_id = "ami-0ed961fa828560210"

  instance_initiated_shutdown_behavior = "terminate"

  update_default_version = true

  key_name = "DanitTerraformProject"

  network_interfaces {
    associate_public_ip_address = false
    subnet_id                   = aws_subnet.private-subnet-1.id
    security_groups             = [
      aws_security_group.ec2_private_security_group.id,
      aws_security_group.allow-ssh.id
    ]
  }

  placement {
    availability_zone = "eu-west-1a"
  }

  tag_specifications {
    resource_type = "instance"

    tags = {
      Name = "my-backend-app"
    }
  }

  user_data = base64encode(data.template_file.backend.rendered)
}
