public abstract class Pedido implements Processavel {
    private final int id;
    private final String nomeCliente;
    private final double valor;
    private StatusPedido status;
    private final Categoria categoria;

    protected Pedido(int id, String nomeCliente, double valor,
                     StatusPedido status, Categoria categoria)
            throws ValorInvalidoException {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do pedido deve ser maior que zero.");
        }
        if (nomeCliente == null || nomeCliente.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        if (status == null || categoria == null) {
            throw new IllegalArgumentException("Status e categoria são obrigatórios.");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.status = status;
        this.categoria = categoria;
    }

    @Override
    public void processarPedido() throws PedidoInvalidoException {
        if (status == StatusPedido.CANCELADO) {
            throw new PedidoInvalidoException("Pedido cancelado não pode ser processado.");
        }
        status = StatusPedido.PROCESSANDO;
    }

    public void alterarStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        if (status == StatusPedido.ENTREGUE) {
            throw new PedidoInvalidoException("Pedido entregue não pode mudar de status.");
        }
        if (novoStatus == null) {
            throw new IllegalArgumentException("O novo status é obrigatório.");
        }
        status = novoStatus;
    }

    public int getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public double getValor() {
        return valor;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format(
                "Pedido{id=%d, cliente='%s', valor=R$ %.2f, valor final=R$ %.2f, status=%s, categoria=%s}",
                id, nomeCliente, valor, calcularValorFinal(), status, categoria);
    }
}
