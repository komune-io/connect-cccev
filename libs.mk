VERSION = $(shell cat VERSION)

.PHONY: lint build test publish promote

lint:
	@echo 'No Lint'

build:
	./gradlew build publishToMavenLocal -Dorg.gradle.parallel=true -x test

test-pre:
	sudo echo "127.0.0.1 ca.bc-coop.bclan" | sudo tee -a /etc/hosts
	sudo echo "127.0.0.1 peer0.bc-coop.bclan" | sudo tee -a /etc/hosts
	sudo echo "127.0.0.1 orderer.bclan" | sudo tee -a /etc/hosts
	@make dev pull
	@make dev up
	@make dev c2-sandbox-ssm logs
	@make dev up
	@make dev c2-sandbox-ssm logs

test:
	./gradlew test

test-post:
	@make dev down

publish:
	VERSION=$(VERSION) PKG_MAVEN_REPO=github ./gradlew publish -Dorg.gradle.parallel=true --info

promote:
	VERSION=$(VERSION) PKG_MAVEN_REPO=sonatype_oss ./gradlew publish
