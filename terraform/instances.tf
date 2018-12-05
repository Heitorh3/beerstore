resource "aws_key_pair" "key_par" {
  public_key = "${file("key/beerstore_key.pub")}"
}

resource "aws_instance" "instances" {
  count = 3

  ami = "ami-009d6802948d06e52"
  instance_type = "t2.micro"

  subnet_id = "${element(aws_subnet.public_subnet.*.id, count.index )}"

  key_name = "${aws_key_pair.key_par.key_name}"

  tags {
    Name = "hibicode_instances"
  }
}

output "public_ips" {
  value = "${join(", ", aws_instance.instances.*.public_ip)}"
}

// ssh-keygen -t rsa -b 4096 -o -a 100 -f key/beerstore_key