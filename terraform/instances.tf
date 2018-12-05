resource "aws_instance" "instances" {
  count = 3

  ami = "ami-009d6802948d06e52"
  instance_type = "t2.micro"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index )}"

  tags {
    Name = "hibicode_instances"
  }
}