#Angular dockerfile
FROM node:alpine3.20 AS build

WORKDIR /app

COPY . .

RUN npm cache clean --force

RUN npm install
RUN npm run build

FROM nginx:bookworm
COPY --from=build /app/dist/frontend-angular/browser /usr/share/nginx/html

COPY /nginx.conf /etc/nginx/nginx.template.conf

CMD /bin/sh -c "envsubst '${GATEWAY_HOST} ${GATEWAY_PORT}' < /etc/nginx/nginx.template.conf > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"

EXPOSE 8084