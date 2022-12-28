# S3-Carspace

Project for the 3rd semester in Fontys.

## Project structure
Project features a ReactJS frontend website and a Spring boot REST API backend. Website also uses https://github.com/Dani055/ydimage-server to upload images to Amazon S3. (For the sake of privacy this functionality is disabled)

- Project features a CI/CD Pipeline (which does not function here, it is meant for GitLab). You can see how it functions in the software architecture document.
- You can see some screenshots of the website in the Screenshots folder.
- **For more info you can check out the Documentation folder, which features a very detailed overview of the project**

## How to run project
Thankfully this project is fully Dockerized, so all you need to do is have docker installed and run one command.
### Steps
- Have Docker Desktop installed
- Go to the folder Carspace-backend. Run the command "gradlew build -x test" so java can build the app and make a .jar file.
- In the root folder (here) run the command "docker compose up". This spins up the 2 containers (front-end and back-end with an in-memory database)
- Go to localhost:3000 to see the app live!
