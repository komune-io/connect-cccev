FRONT_CCCEV_DOCKERFILE	:= infra/docker/cccev-web-app/Dockerfile
FRONT_CCCEV_NAME	    := komune-io/cccev-web-app
FRONT_CCCEV_IMG	    	:= ${FRONT_CCCEV_NAME}:${VERSION}
FRONT_CCCEV_LATEST		:= ${FRONT_CCCEV_NAME}:latest

CCCEV_APP_NAME	   	 	:= komune-io/cccev-gateway
CCCEV_APP_IMG	    	:= ${CCCEV_APP_NAME}:${VERSION}
CCCEV_APP_LATEST		:= ${CCCEV_APP_NAME}:latest
CCCEV_APP_PACKAGE	   	:= :api-gateway

STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= komune-io/cccev-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

libs: package-kotlin
docker: docker-build docker-push
docs: docs-build docs-push

docker-build: docker-cccev-api-build
docker-push: docker-cccev-api-push

docs-build: package-storybook-build
docs-push: package-storybook-push

package-kotlin:
	VERSION=${VERSION} ./gradlew build publishToMavenLocal publish --stacktrace -x test -x jvmTest

local:
	VERSION=${VERSION} ./gradlew build publishToMavenLocal --stacktrace

docker-cccev-api-build:
	VERSION=${VERSION} ./gradlew build ${CCCEV_APP_PACKAGE}:bootBuildImage -x test -x jvmTest

docker-cccev-api-push:
	@docker push ${CCCEV_APP_IMG}

push-latest-cccev-api:
	@docker tag ${CCCEV_APP_IMG} ${CCCEV_APP_LATEST}
	@docker push ${CCCEV_APP_LATEST}

docker-cccev-front:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} --build-arg VERSION=${VERSION} -f ${FRONT_CCCEV_DOCKERFILE} -t ${FRONT_CCCEV_IMG} .
	@docker push ${FRONT_CCCEV_IMG}

package-storybook-build:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} --build-arg VERSION=${VERSION} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

package-storybook-push:
	@docker push ${STORYBOOK_IMG}

## DEV ENVIRONEMENT
dev: dev-bclan dev-neo4j dev-fs

dev-down: dev-bclan-down dev-neo4j-down dev-fs-down

dev-up: dev-bclan-up dev-neo4j-up dev-fs-up

## DEV bclan
dev-bclan: dev-bclan-down dev-bclan-up

dev-bclan-up:
	@docker compose -f docker-compose-bclan.yml up -d

dev-bclan-down:
	@docker compose -f docker-compose-bclan.yml down -v;

dev-bclan-log:
	@docker compose -f docker-compose-bclan.yml logs -f

## DEV fs
dev-fs: dev-fs-down dev-fs-up

dev-fs-up:
	@docker compose -f docker-compose-fs.yml up -d

dev-fs-down:
	@docker compose -f docker-compose-fs.yml down -v;

dev-fs-log:
	@docker compose -f docker-compose-fs.yml logs -f

## DEV neo4j
dev-neo4j: dev-neo4j-down dev-neo4j-up

dev-neo4j-pull:
	@docker compose -f docker-compose-neo4j.yml pull

dev-neo4j-up:
	@docker compose -f docker-compose-neo4j.yml pull
	@docker compose -f docker-compose-neo4j.yml up -d

dev-neo4j-down:
	@docker compose -f docker-compose-neo4j.yml down -v;

dev-neo4j-log:
	@docker compose -f docker-compose-neo4j.yml logs -f
