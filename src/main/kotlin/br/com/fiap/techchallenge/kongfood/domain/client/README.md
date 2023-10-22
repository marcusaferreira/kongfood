# Bounded context: Client
This bounded context is responsible for managing the client.
For a portuguese version of this document, please check [README-pt-BR.md](README-pt-BR.md).
## Domain: Client
Client is a domain that manage the information of a client.
Through this domain, the client can:
- Create an account
- Update his account
- Inactivate his account
- Reactivate his account
- Retrieve his account data (name, email, phone number, cpf) with his cpf or email

### Aggregate: Client
Client is the aggregate root of this bounded context. It is responsible for managing the client information and
the state of through the lifecycle.
#### Entity: Client
Client is an entity that represents the client. It contains the name, email, phone number, cpf and the state of the client.
