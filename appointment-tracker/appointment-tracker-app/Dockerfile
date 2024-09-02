# Use an official Node runtime as a parent image
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Use an official NGINX image to serve the frontend
FROM nginx:alpine

# Copy the build output to the NGINX HTML directory
COPY --from=0 /app/build /usr/share/nginx/html

# Expose the port NGINX runs on
EXPOSE 80

# Start NGINX server
CMD ["nginx", "-g", "daemon off;"]
