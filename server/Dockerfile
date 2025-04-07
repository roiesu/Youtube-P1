FROM node:18-alpine

COPY . "./server"
WORKDIR "/server"

RUN "npm i"
ENV JWT_SECRET="Come tell me your secret the way to dance how to spin"
ENV MONGODB_CONNECTION_URL="mongodb+srv://orenb99:org05101971@bishbash.3blxfdz.mongodb.net/"
ENV MONGODB_DATABASE="BishBash"
ENV TCP_PORT=5555
ENV TCP_IP="localhost"

EXPOSE 8080
RUN "npm start"

