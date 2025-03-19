FROM node:18-alpine as build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Use npm install instead of npm ci since package-lock.json might not exist
RUN npm install

# Copy all files and build the app
COPY . .
RUN npm run build

# Production stage
FROM nginx:alpine as production
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=build /app/dist .

# Optional: Copy custom nginx config if needed
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]