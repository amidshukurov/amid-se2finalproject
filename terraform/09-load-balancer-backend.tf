resource "aws_lb" "auto-load-balancer-backend" {
  name               = "auto-load-balancer-backend"
  internal           =  true
  load_balancer_type = "application"
  security_groups    = [
    aws_security_group.private_alb_security_group.id,
    aws_security_group.allow-ssh.id
  ]

  subnets = [
    aws_subnet.private-subnet-1.id,
    aws_subnet.private-subnet-2.id
  ]

  enable_deletion_protection = false

  tags = {
    Name        = "auto-load-balancer-backend"
    Environment = "production"
  }
  depends_on = [aws_db_instance.mysql]
}

resource "aws_lb_listener" "http1" {
  load_balancer_arn = aws_lb.auto-load-balancer-backend.arn
  port              = 80

  default_action {
    type = "fixed-response"

    fixed_response {
      content_type = "text/plain"
      message_body = "There's nothing here"
      status_code  = "404"
    }
  }
}

resource "aws_lb_listener_rule" "my_app1" {
  listener_arn = aws_lb_listener.http1.arn

  action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.backend-target-group.arn
  }

  condition {
    path_pattern {
      values = ["/*"]
    }
  }
}