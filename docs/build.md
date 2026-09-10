# Build

Cada microsserviço é independente e possui seu próprio `pom.xml`.

Execute:

```bash
cd address-service && mvn clean package
cd ../customer-service && mvn clean package
cd ../api-gateway && mvn clean package
```

Depois:

```bash
cd ..
docker compose up --build
```

O repositório não inclui `mvnw` nem o binário Maven Wrapper. Use Maven 3.6.3+ instalado no ambiente.
