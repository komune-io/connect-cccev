FROM node:12.13.0-alpine as build

WORKDIR /app

COPY web ./

ARG NPM_AUTH_TOKEN
RUN printf "\
@komune-io:registry=https://npm.pkg.github.com\n\
//npm.pkg.github.com/:_authToken=%s\n\
" "${NPM_AUTH_TOKEN}" > .npmrc

RUN yarn install
ENV PATH /app/node_modules/.bin:$PATH
RUN yarn workspace cccev-app build

FROM nginx:1.25.4-alpine

LABEL org.opencontainers.image.source="https://github.com/komune-io/connect-cccev"

COPY --from=build /app/packages/cccev-app/build /var/www
COPY infra/docker/cccev-web-app/nginx.conf /etc/nginx/nginx.conf

RUN chown -R nginx:nginx /var/cache/nginx && \
    chown -R nginx:nginx /var/log/nginx && \
    chown -R nginx:nginx /etc/nginx/conf.d

RUN touch /var/run/nginx.pid && \
        chown -R nginx:nginx /var/run/nginx.pid
USER nginx

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
