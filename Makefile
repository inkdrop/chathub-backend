test:
	mvn clean test -Dspring.profiles.active=test

build:
	mvn package -DskipTests

deploy:
	docker build . -t luizkowalski/chathub:qa
 	docker push luizkowalski/chathub:qa

clean:
	mvn clean