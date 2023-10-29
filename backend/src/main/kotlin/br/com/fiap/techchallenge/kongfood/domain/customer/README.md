# Bounded context: Customer
This bounded context is responsible for managing the customer.
For a portuguese version of this document, please check [README-pt-BR.md](README-pt-BR.md).
## Domain: Customer
Customer is a domain that manage the information of a customer.
Through this domain, the customer can:
- Create an account
- Update his account
- Inactivate his account
- Reactivate his account
- Retrieve his account data (name, email, phone number, cpf) with his cpf or email

### Aggregate: Customer
Customer is the aggregate root of this bounded context. It is responsible for managing the customer information and
the state of through the lifecycle.
#### Entity: Customer
Customer is an entity that represents the customer. It contains the name, email, phone number, cpf and the state of the customer.
