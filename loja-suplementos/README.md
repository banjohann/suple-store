# Loja de Suplementos

## Pré-requisitos
- Java 17 ou superior
- Maven (ou utilize o Maven Wrapper incluído no projeto)

## Configurando o banco

### Usando o docker
   ```bash
   docker compose up -d 
   ```

### Usando o PostgreSQL Local
1. **Criar o banco de dados**:
```sql
    CREATE DATABASE loja-suplementos;
    CREATE SCHEMA public;
```

2. **Configure as propriedades do banco de dados no arquivo `src/main/resources/application.properties`:**
```properties
    url: jdbc:postgresql://localhost:5432/loja-suplementos
    username: userdobanco
    password: senhadobanco
```

3. **Opcional**: Executar o dump.sql para popular o banco de dados com dados iniciais:
```bash
    psql -U userdobanco -d loja-suplementos -f dump.sql
```

## Build e Execução

### Usando o Maven Wrapper
1. **Build do projeto e download das dependências**:
   ```bash
   ./mvnw clean install
    ```

2. **Executar a aplicação**:
    ```bash
    java -jar target/loja-suplentes-0.0.1-SNAPSHOT.jar
    ```

3. **Acessar a aplicação**:
   - Acesse `http://localhost:8080` no seu navegador.