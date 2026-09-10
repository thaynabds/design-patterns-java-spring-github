#!/usr/bin/env bash
set -euo pipefail

echo "Executando testes do Address Service"
cd address-service
mvn test

cd ../customer-service
echo "Executando testes do Customer Service"
mvn test

cd ../api-gateway
echo "Compilando API Gateway"
mvn test

echo "Verificação concluída."
