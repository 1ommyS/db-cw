cd migrations
goose -dir . postgres "user=myuser password=secret dbname=mydb host=localhost port=5432 sslmode=disable" up