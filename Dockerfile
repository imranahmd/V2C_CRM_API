# # Stage 1: Build the application
# FROM maven:3.8.8-eclipse-temurin-17 AS build

# # Set the working directory inside the container
# WORKDIR /app

# # Copy only the necessary files to cache Maven dependencies
# COPY pom.xml .
# RUN mvn dependency:go-offline -B

# # Copy the entire project to the container
# COPY . .

# # Build the WAR file
# RUN mvn clean package -DskipTests

# # Stage 2: Deploy the WAR file
# FROM tomcat:9.0-jdk17-temurin

# # Set the WAR file deployment path
# ENV DEPLOY_PATH /usr/local/tomcat/webapps/

# # Copy the WAR file from the build stage to the deployment directory
# COPY --from=build /app/target/*.war ${DEPLOY_PATH}app.war

# # Expose the default Tomcat port
# EXPOSE 8080

# # Start Tomcat
# CMD ["catalina.sh", "run"]




# Stage 1: Build the application
FROM maven:3.8.8-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files to cache Maven dependencies
COPY pom.xml . 
RUN mvn dependency:go-offline -B

# Copy the entire project to the container
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests

# Stage 2: Deploy the WAR file
FROM tomcat:10.0-jdk17-temurin

# Set the WAR file deployment path
ENV DEPLOY_PATH /usr/local/tomcat/webapps/

# Copy the WAR file from the build stage to the deployment directory
# COPY --from=build /app/target/*.war ${DEPLOY_PATH}app.war

COPY --from=build /app/target/*.war ${DEPLOY_PATH}ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]






# FROM tomcat:9-jdk17

# # Copy the WAR file to the Tomcat webapps directory
# COPY target/pg-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/pg.war

# # Expose the default Tomcat port
# EXPOSE 8080

# # Start Tomcat
# CMD ["catalina.sh", "run"]
