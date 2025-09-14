# --- build stage ---
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build -- --configuration production

# --- runtime stage ---
FROM nginx:1.25-alpine
# remove default config e adiciona a nossa (SPA + gzip)
RUN rm -f /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf
# copie os arquivos gerados pelo Angular (ajuste o caminho se seu outputPath for outro)
COPY --from=build /app/dist/diario /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]