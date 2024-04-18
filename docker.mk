DOCKER_REPOSITORY = ghcr.io/

FRONT_CCCEV_DOCKERFILE	:= infra/docker/cccev-web-app/Dockerfile
FRONT_CCCEV_NAME	    := ${DOCKER_REPOSITORY}komune-io/cccev-web-app
FRONT_CCCEV_IMG	    	:= ${FRONT_CCCEV_NAME}:${VERSION}
FRONT_CCCEV_LATEST		:= ${FRONT_CCCEV_NAME}:latest

CCCEV_APP_NAME	   	 	:= ${DOCKER_REPOSITORY}komune-io/cccev-gateway
CCCEV_APP_IMG	    	:= ${CCCEV_APP_NAME}:${VERSION}
CCCEV_APP_PACKAGE	   	:= :api-gateway

lint: docker-cccev-front-lint

build: docker-cccev-api-build

test:
	echo 'No Tests'

publish: docker-cccev-api-push

promote:
	echo 'No Tests'

# docker-cccev-api
docker-cccev-api-build:
	VERSION=${VERSION} ./gradlew build ${CCCEV_APP_PACKAGE}:bootBuildImage --imageName ${CCCEV_APP_IMG}  -x test -x jvmTest

docker-cccev-api-push:
	@docker push ${CCCEV_APP_IMG}

# docker-cccev-front
docker-cccev-front-lint:
	echo 'No Tests'
	#@docker run --rm -i hadolint/hadolint hadolint - < ${FRONT_CCCEV_DOCKERFILE}

#docker-cccev-front:
#	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} --build-arg VERSION=${VERSION} -f ${FRONT_CCCEV_DOCKERFILE} -t ${FRONT_CCCEV_IMG} .
#	@docker push ${FRONT_CCCEV_IMG}
