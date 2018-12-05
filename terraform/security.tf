resource "aws_security_group" "allow_ssh" {
  vpc_id = "${aws_vpc.main.id}"
  name = "allow_ssh"

  ingress {
    from_port = 22
    protocol = "tcp"
    to_port = 22

    cidr_blocks = ["187.32.231.216/32"]
  }
}