# Contexto delimitado: Cliente
Este contexto delimitado é responsável por gerenciar o cliente.
## Domínio: Cliente
Cliente é um domínio que gerencia as informações de um cliente.
Através deste domínio o cliente pode:
- Criar a sua conta aqui
- Atualizar sua conta
- Inativar sua conta
- Reativar sua conta
- Recuperar os dados da sua conta (nome, e-mail, telefone, cpf) com seu cpf ou e-mail

### Agregado: Cliente
Cliente é a raiz agregada desse contexto delimitado. É responsável por gerenciar as informações do cliente e
o estado do ciclo de vida.
#### Entidade: Cliente
Cliente é uma entidade que representa o cliente. Contém nome, e-mail, telefone, cpf e estado do cliente.