resource "aws_lb" "load_balancer" {
  name = "hibicode-beerstore-alb"
  security_groups = ["${aws_security_group.allow_load_balancer.id}"]
  subnets = ["${flatten(chunklist(aws_subnet.public_subnet.*.id,1))}"]

  enable_deletion_protection = false

}

resource "aws_lb_target_group" "load_balancer_target_group" {
  name = "hibicode-beerstore"
  vpc_id = "${aws_vpc.main.id}"
  port = 8080
  protocol = "http"

  health_check {
    path = "/actuator/health"
    matcher = 200
    interval = 120
    healthy_threshold = 2
    unhealthy_threshold = 2
    timeout = 10
  }
}

resource "aws_lb_target_group_attachment" "load_balancer_target_group_attachmen" {
  count = 3
  target_group_arn = "${aws_lb_target_group.load_balancer_target_group.arn}"
  target_id = "${element(aws_instance.instances.*.id, count.index)}"
  port = 8080
}

resource "aws_lb_listener" "load_balancer_listener" {
  load_balancer_arn = "${aws_lb.load_balancer.arn}"
  port = 80
  protocol = "http"

  "default_action" {
    type = "forward"
    target_group_arn = "${aws_lb_target_group.load_balancer_target_group.arn}"
  }
}

output "loadbalancer" {
  value = "${aws_lb.load_balancer.dns_name}"
}