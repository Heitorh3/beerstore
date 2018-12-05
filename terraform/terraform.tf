terraform {
  backend "s3" {
    bucket = "beerstore01"
    key = "beerstore01-curso-hibicode"
    region = "us-east-1"
    profile = "terraform"
  }
}