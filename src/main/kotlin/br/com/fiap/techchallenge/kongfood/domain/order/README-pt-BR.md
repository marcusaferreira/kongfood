# Contexto delimitado: Ordem
Este contexto delimitado é responsável por gerenciar o pedido do cliente.
## Domínio: Pedido
Pedido é um domínio que gerencia o ciclo de vida de um pedido feito por um cliente.
Através deste domínio o cliente pode:
- Criar um pedido
- Adicionar produtos ao pedido
- Remover produtos do pedido
- Paguar o pedido
- Cancelar o pedido
- Acompanhar o pedido
- Receber o pedido

O cozinheiro pode:
- Preparar o pedido
- Notificar que o pedido está pronto
- Finalizar o pedido

### Agregado: Pedido
O pedido é a raiz agregada deste contexto delimitado. É responsável por gerenciar o pedido dos produtos e
o estado do ciclo de vida.
#### Entidade: Pedido
Pedido é uma entidade que representa a solicitação de produtos de um cliente. Contém os produtos, o estado do pedido e
o preço total, um pedido pode ser atribuido a um cliente ou não, um pedido tem um número que pode ser utilizado
pelo cliente para rastrear seu pedido.
##### Objeto de valor: OrderStatus
OrderStatus é um objeto de valor que representa o estado do pedido. Pode ser:
- CREATED (CRIADO): O pedido foi criado;
- PENDING (PENDENTE): O pedido está aguardando pagamento, isso significa que o cliente já adicionou produtos ao pedido;
- ACCEPTED (ACEITO): O pedido foi aceito e está aguardando a preparação, isso significa que o cliente pagou o pedido;
- IN_PREPARATION (EM_PREPARACAO): O pedido está sendo preparado, isso significa que o cozinheiro está preparando o pedido;
- READY (PRONTO): O pedido está pronto para ser retirado, isso significa que o cozinheiro terminou de preparar o pedido;
- FINISHED (FINALIZADO): O pedido foi finalizado, isso significa que o cliente desiste do pedido;
- CANCELED (CANCELADO): O pedido foi cancelado, isso significa que o cliente cancelou o pedido;
##### Objeto de valor: OrderLine
OrderLine é um objeto de valor que representa uma linha do pedido. Contém o produto e a quantidade do produto.
#### Entidade: Produto
Produto é uma entidade que representa um produto que pode ser adicionado ao pedido. Um produto pode ser:
- Comida, como um hambúrguer, uma pizza, um sanduíche ou batatas fritas. Os alimentos são divididos em:
    - Prato principal, como um hambúrguer ou uma pizza ou um sanduíche;
    - Acompanhamentos, como batatas fritas ou anéis de cebola ou nuggets de frango;
- Bebidas, como refrigerante, suco ou água;
- Sobremesa, tipo sorvete ou milkshake ou petit gateau;
- Combo, combinação de comida, bebida e sobremesa;