# Bounded context: Order
This bounded context is responsible for managing the client order.
For a portuguese version of this document, please check [README-pt-BR.md](README-pt-BR.md).
## Domain: Order
Order is a domain that manage the lifecycle of an order that a client made and it will be prepared by the cook.
Through this domain, the client can:
- Create an order
- Add products to the order
- Remove products from the order
- Pay the order
- Cancel the order
- Track the order
- Receive the order

The cook can:
- Prepare the order
- Notify the order is ready
- Finish the order

### Aggregate: Order
Order is the aggregate root of this bounded context. It is responsible for managing the order of the products and
the state of through the lifecycle.
#### Entity: Order
Order is an entity that represents the order of the products. It contains the products, the state of the order and 
the total price, an order can be attributed to a client or not, an order has a number that can be used
by the client to track his order.
##### Value Object: OrderStatus
OrderStatus is a value object that represents the state of the order. It can be:
- CREATED : The order was created;
- PENDING : The order is waiting for the payment, this means that the client already added products to the order;
- ACCEPTED : The order was accepted and is waiting for the preparation, this means that the client paid the order;
- IN_PREPARATION : The order is being prepared, this means that the cook is preparing the order;
- READY : The order is ready to be withdrawn, this means that the cook finished to prepare the order;
- FINISHED : The order was finished, this means that the client withdraw the order;
- CANCELED : The order was canceled, this means that the client canceled the order;
##### Value Object: OrderLine
OrderLine is a value object that represents a line of the order. It contains the product and the quantity of the product.
#### Entity: Product
Product is an entity that represents a product that can be added to the order. This entity is a representation of a 
product in a determined moment, it is created to don't propagate to an existed order or a previous one the changes that
ocurred in the product bounded context. A product can be:
- Food, like a burger or a pizza or a sandwich or fries. Food are divided in:
    - Main course, like a burger or a pizza or a sandwich;
    - Side dish, like fries or onion rings or chicken nuggets;
- Drinks, like a soda or a juice or water;
- Dessert, like ice cream or milkshake or petit gateau;
- Combo, a combination of food, drink and dessert;