resource "aws_vpc" "main" {
  cidr_block = "192.168.00/16"

  tags {
    Name = 'hibicode'
  }
}