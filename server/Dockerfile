FROM node:18-alpine

WORKDIR "/server"

COPY . .

RUN npm install
ENV JWT_SECRET="Come tell me your secret the way to dance how to spin"
ENV MONGODB_CONNECTION_URL="mongodb://mongo:27017"
ENV MONGODB_DATABASE="BishBash"
ENV TCP_PORT=5555
ENV TCP_IP="10.0.2.2"

EXPOSE 8080
CMD ["npm", "start"]

