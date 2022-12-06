# S3-Carspace

Main branch for stable "releases"

front-end
docker build -t frondendcontainer .
docker run -it -p 3000:3000 --rm --name runningfrontend frondendcontainer

back-end
docker build --build-arg JAR_FILE=build/libs/*.jar -t backendimage .
docker run -it -p 8080:8080 --rm --name runningbackend backendimage .