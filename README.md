# S3-Carspace

Main branch for stable "releases"

front-end
docker build -t frondendcontainer .
docker run -d -p 3000:3000 --rm --name runningfrontend frondendcontainer

back-end
docker build --build-arg JAR_FILE=build/libs/*.jar -t backendimage .
docker run -d -p 8080:8080 -e DB_PASSWORD=yaQZW9!$uRFIY#xIyymG -e INSERT_DATA=false -e DB_USERNAME=customer_251983_database -e SPRING_DATASOURCE_DIALECT=org.hibernate.dialect.MySQL5InnoDBDialect -e SPRING_DATASOURCE_URL=jdbc:mysql://eu01-sql.pebblehost.com:3306/customer_251983_database?useSSL=false --rm --name runningbackend ydoykov/s3carspacebackend .
