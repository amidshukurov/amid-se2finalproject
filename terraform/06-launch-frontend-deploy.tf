data "template_file" "frontend" {
  template = file("files/frontend-deploy.sh")

  vars = {
    backend_url = aws_lb.auto-load-balancer-backend.dns_name
  }
  depends_on = [aws_lb.auto-load-balancer-backend]
}

resource "aws_launch_template" "launch-frontend" {
  name          = "lt-frontend"
  instance_type = "t2.micro"

  image_id = "ami-0ed961fa828560210"

  instance_initiated_shutdown_behavior = "terminate"

  update_default_version = true

  key_name = "DanitTerraformProject"

  network_interfaces {
    associate_public_ip_address = true
    subnet_id                   = aws_subnet.public-subnet-1.id
    security_groups = [
      aws_security_group.ec2_public_security_group.id,
      aws_security_group.allow-ssh.id
    ]
  }

  placement {
    availability_zone = "eu-west-1a"
  }

  tag_specifications {
    resource_type = "instance"

    tags = {
      Name = "my-frontend-app"
    }
  }

  user_data = base64encode(data.template_file.frontend.rendered)
  depends_on = ["aws_lb.auto-load-balancer-backend"]

}