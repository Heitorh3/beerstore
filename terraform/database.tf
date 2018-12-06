module "rds" {
  source  = "terraform-aws-modules/rds/aws"
  version = "1.22.0"

  # insert the 10 required variables here
  identifier = "hibicode-beerstore-rds"

  engine = "postgres"
  engine_version = "10.14"
  instance_class =  "db.t2.micro"
  allocated_storage = "20"

  name = "beerstore"
  username = "beerstore"
  password = "beerstore"
  port = "5432"

  vpc_security_group_ids = ["${aws_security_group.security_group_database.id}"]

  maintenance_window = "Thu:03:30-Thu:05:30"
  backup_window = "05:30-06:30"
  storage_type = "gp2"
  multi_az = "false" // Habilita a criação de replicas em diferentes zonas Não esta no plano free
  family = "postgres10"

  subnet_ids = "${flatten(chunklist(aws_subnet.private_subnet.*.id,1))}"
}

