DOCKER_REPOSITORY = ghcr.io/

FRONT_CCCEV_DOCKERFILE	:= infra/docker/cccev-web-app/Dockerfile
FRONT_CCCEV_NAME	    := =cccev-web-app
FRONT_CCCEV_IMG	    	:= ${FRONT_CCCEV_NAME}:${VERSION}
FRONT_CCCEV_LATEST		:= ${FRONT_CCCEV_NAME}:latest

CCCEV_APP_NAME	   	 	:= cccev-gateway
CCCEV_APP_IMG	    	:= ${CCCEV_APP_NAME}:${VERSION}
CCCEV_APP_PACKAGE	   	:= :api-gateway

lint: docker-cccev-front-lint

build: docker-cccev-api-build

test:
	echo 'No Tests'

publish: docker-cccev-api-publish

promote: docker-cccev-api-promote

# docker-cccev-api
docker-cccev-api-build:
	VERSION=${VERSION} ./gradlew build ${CCCEV_APP_PACKAGE}:bootBuildImage --imageName ${CCCEV_APP_IMG}  -Dorg.gradle.parallel=true -x test -x jvmTest

docker-cccev-api-publish:
	@docker tag ${CCCEV_APP_IMG} ghcr.io/komune-io/${CCCEV_APP_IMG}
	@docker push ghcr.io/komune-io/${CCCEV_APP_IMG}

docker-cccev-api-promote:
	@docker tag ${CCCEV_APP_IMG} docker.io/komune/${CCCEV_APP_IMG}
	@docker push docker.io/komune/${CCCEV_APP_IMG}

# docker-cccev-front
docker-cccev-front-lint:
	echo 'No Tests'
	#@docker run --rm -i hadolint/hadolint hadolint - < ${FRONT_CCCEV_DOCKERFILE}

#docker-cccev-front:
#	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} --build-arg VERSION=${VERSION} -f ${FRONT_CCCEV_DOCKERFILE} -t ${FRONT_CCCEV_IMG} .

docker-script-publish:
	@docker tag ${FRONT_CCCEV_IMG} ghcr.io/komune-io/${FRONT_CCCEV_IMG}
	@docker push ghcr.io/komune-io/${CCCEV_APP_IMG}

docker-script-promote:
	@docker tag ${FRONT_CCCEV_IMG} docker.io/komune/${FRONT_CCCEV_IMG}
	@docker push docker.io/komune/${FRONT_CCCEV_IMG}