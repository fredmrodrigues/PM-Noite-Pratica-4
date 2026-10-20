# Prática 4 - Iteradores, Enumeradores e Tratamento de Exceções

**Pontifícia Universidade Católica de Minas Gerais (PUC Minas)**  
**Disciplina:** Laboratório de Programação Modular  
**Professor:** Glender Brás

## Instruções

### Exercício: Sistema Gerenciador de Pedidos

Uma loja virtual deseja melhorar seu sistema de pedidos. O sistema deve permitir:

- Diferentes tipos de pedidos;
- Processamento seguro;
- Validações e tratamento adequado de erros.

### 1. Crie os enums

**StatusPedido:**
- `PENDENTE`
- `PROCESSANDO`
- `ENVIADO`
- `ENTREGUE`
- `CANCELADO`

**Categoria:**
- `ELETRONICO`
- `ROUPA`
- `ALIMENTO`

### 2. Crie as interfaces com os seguintes métodos

**Interface Processável:**

```java
void processarPedido() throws PedidoInvalidoException;
double calcularValorFinal();
```

**Interface Entregável:**

```java
double calcularFrete();
```

### 3. Crie as exceções personalizadas

**PedidoInvalidoException:** deve ocorrer quando:
- Um pedido cancelado tentar ser processado;
- Um pedido entregue tentar mudar de status.

**ValorInvalidoException:** deve ocorrer quando:
- O valor do pedido for menor ou igual a zero.

### 4. Crie uma classe abstrata Pedido

- **Atributos:** `id`, `nomeCliente`, `valor`, `status` (enum), `categoria`.
- No construtor, lançar `ValorInvalidoException` se:
  - `valor <= 0`.
- O método `processarPedido()` deve:
  - Validar o status;
  - Lançar `PedidoInvalidoException` quando necessário.

### 5. Crie as subclasses de Pedido

**PedidoEletronico:**
- Desconto de 10%, se `valor > 100`;
- Frete = R$ 20 + 10% do valor da compra.

**PedidoRoupa:**
- Desconto de 20%, se `valor > 200`;
- Frete = R$ 10 + 5% do valor da compra.

**PedidoAlimento:**
- Sem desconto;
- Frete = R$ 5.

Todos os pedidos são processáveis, mas somente o `PedidoAlimento` não é entregável.

### 6. Na classe main, crie um menu para

- Cadastrar pedido;
- Alterar o status de um pedido;
- Processar pedido: deve usar `Iterator` para processar todos os pedidos cadastrados;
- Remover cancelados: deve usar `Iterator` para remover todos os pedidos cancelados da lista;
- Exibir lista de pedidos: não deve exibir os cancelados;
- Exibir pedidos entregáveis;
- Encerrar.
