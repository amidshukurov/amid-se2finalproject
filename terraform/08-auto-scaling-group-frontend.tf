resource "aws_autoscaling_group" "autoscaling-group-frontend" {
  name = "autoscaling-group-frontend-app"

  vpc_zone_identifier = [aws_subnet.public-subnet-1.id, aws_subnet.public-subnet-2.id]
  desired_capacity    = 1
  max_size            = 10
  min_size            = 1

  target_group_arns = [
    aws_lb_target_group.frontend-target-group.arn
  ]

  health_check_grace_period = 60

  protect_from_scale_in = false

  launch_template {
    id      = aws_launch_template.launch-frontend.id
    version = "$Latest"
  }

  tag {
    key                 = "scaling-group"
    propagate_at_launch = true
    value               = "autoscaling-frontend-app"
  }

  depends_on = ["aws_lb_target_group.frontend-target-group"]

}


resource "aws_autoscaling_policy" "frontend_scale_up" {
  name = "scaleupfront"
  autoscaling_group_name = aws_autoscaling_group.autoscaling-group-frontend.name
  adjustment_type = "ChangeInCapacity"
  scaling_adjustment = "1"
  cooldown = "60"
  policy_type = "SimpleScaling"
}
resource "aws_cloudwatch_metric_alarm" "frontend_scaleup_alarm" {
  alarm_name = "scaleUpAlarmFront"
  alarm_description = "alarm for the scale up"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods = "2"
  metric_name = "RequestCount"
  namespace = "AWS/ApplicationELB"
  period = "60"
  statistic = "Average"
  threshold = "30"
  dimensions = {
    TargetGroup  = aws_lb_target_group.frontend-target-group.arn_suffix
    LoadBalancer = aws_lb.auto-load-balancer-frontend.arn_suffix
  }
  actions_enabled = true
  alarm_actions = [aws_autoscaling_policy.frontend_scale_up.arn]
}

resource "aws_autoscaling_policy" "frontend_scale_down" {
  name = "scaledownfront"
  autoscaling_group_name = aws_autoscaling_group.autoscaling-group-frontend.name
  adjustment_type = "ChangeInCapacity"
  scaling_adjustment = "-1"
  cooldown = "60"
  policy_type = "SimpleScaling"
}
resource "aws_cloudwatch_metric_alarm" "frontend-alarm" {
  alarm_name = "scaleDownAlarmFront"
  alarm_description = "alarm for the scale down"
  comparison_operator = "LessThanOrEqualToThreshold"
  evaluation_periods = "2"
  metric_name = "RequestCount"
  namespace = "AWS/ApplicationELB"
  period = "60"
  statistic = "Average"
  threshold = "10"
  dimensions = {
    TargetGroup  = aws_lb_target_group.frontend-target-group.arn_suffix
    LoadBalancer = aws_lb.auto-load-balancer-frontend.arn_suffix
  }
  actions_enabled = true
  alarm_actions = [aws_autoscaling_policy.frontend_scale_down.arn]
}

