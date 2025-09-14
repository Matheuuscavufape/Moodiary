# Moodiary — Diário Pessoal (Angular + Spring Boot)
Monorepo com **frontend/** (Angular) e **backend/** (Spring Boot). Autenticação JWT, CRUD de entradas, busca por data/palavra-chave e resumo de humor. CI com GitHub Actions, JaCoCo e SonarCloud. Deploy via Docker no Render.

## Rodando localmente
```bash
# banco (Postgres)
docker run -d --name diario-db -e POSTGRES_USER=diario -e POSTGRES_PASSWORD=diario -e POSTGRES_DB=diario -p 5432:5432 postgres:16

# backend
cd backend && ./mvnw spring-boot:run  # ou mvn spring-boot:run

# frontend
cd ../frontend && npm ci && npm start
```
