# Projeto Gerenciador de Hotel - API Backend

## Visão Geral

Esta é uma API RESTful desenvolvida em Java com Spring Boot para o gerenciamento de pequenos hotéis. O sistema permite ao proprietário do hotel controlar quartos, reservas e hóspedes de forma simples e eficiente.

## Funcionalidades
- Cadastro, listagem, atualização e remoção de quartos (Rooms)
- Cadastro, listagem, atualização e remoção de reservas (Reservations)
- Regras de negócio para evitar conflitos de reservas e duplicidade de quartos
- Exclusão automática de reservas cujo check-out já passou

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Spring Data JPA
- Flyway (migração de banco)
- MySQL
- Lombok

## Como rodar o projeto

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd Projeto-GerenciadorHotel-Backend
   ```
2. **Configure o banco de dados:**
   - Crie um banco MySQL e ajuste as credenciais no arquivo `src/main/resources/application.properties`.
3. **Rode as migrações:**
   - O Flyway executa automaticamente ao iniciar a aplicação.
4. **Build e execute:**
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints principais

### Rooms
- **POST /rooms** — Cadastrar novo quarto
  - Exemplo de JSON:
    ```json
    {
      "roomNumber": "101",
      "roomDescription": "Quarto Standard com vista",
      "valuePerDay": 150.0,
      "isVacant": true,
      "roomStatus": "STANDARD"
    }
    ```
- **GET /rooms** — Listar todos os quartos
- **GET /rooms/{id}** — Buscar quarto por ID
- **PATCH /rooms/{id}** — Atualização parcial (ex: apenas isVacant)
  - Exemplo:
    ```json
    { "isVacant": false }
    ```
- **DELETE /rooms/{id}** — Remover quarto

### Reservations
- **POST /reservations** — Criar nova reserva
  - Exemplo de JSON:
    ```json
    {
      "guest": {
        "name": "João da Silva",
        "document": "12345678900"
      },
      "room": {
        "roomNumber": "101"
      },
      "checkIn": "25/07/2025 - 12:00",
      "checkOut": "28/07/2025 - 12:00"
    }
    ```
- **GET /reservations** — Listar todas as reservas
- **GET /reservations/{id}** — Buscar reserva por ID
- **PUT /reservations/{id}** — Atualizar reserva
- **DELETE /reservations/{id}** — Remover reserva

## Regras de Negócio Importantes
- **Não é permitido cadastrar dois quartos com o mesmo número (`roomNumber`).**
- **Não é permitido criar reservas para quartos ocupados ou para períodos já reservados.**
- **Reservas cujo `checkOut` já passou são excluídas automaticamente a cada hora.**
- **Datas de check-in e check-out devem ser informadas no formato:**
  - `"dd/MM/yyyy - HH:mm"` (ex: `"25/07/2025 - 12:00"`)

## Respostas de Erro
- Mensagens amigáveis são retornadas para erros de negócio, como:
  - Quarto não encontrado
  - Quarto não está vago
  - Já existe uma reserva para este quarto neste período
  - Reserva não encontrada

---

Se tiver dúvidas ou quiser contribuir, fique à vontade para abrir uma issue ou pull request! 