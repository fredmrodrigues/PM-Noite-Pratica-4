import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().executar();
    }

    private void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarPedido();
                    case 2 -> alterarStatus();
                    case 3 -> processarPedidos();
                    case 4 -> removerCancelados();
                    case 5 -> exibirPedidos();
                    case 6 -> exibirEntregaveis();
                    case 0 -> System.out.println("Programa encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (PedidoInvalidoException | ValorInvalidoException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=== Sistema Gerenciador de Pedidos ===");
        System.out.println("1 - Cadastrar pedido");
        System.out.println("2 - Alterar status de um pedido");
        System.out.println("3 - Processar pedidos");
        System.out.println("4 - Remover cancelados");
        System.out.println("5 - Exibir pedidos");
        System.out.println("6 - Exibir pedidos entregáveis");
        System.out.println("0 - Encerrar");
    }

    private void cadastrarPedido() throws ValorInvalidoException {
        int tipo = lerInteiro("Tipo (1-Eletrônico, 2-Roupa, 3-Alimento): ");
        int id = lerInteiro("ID: ");
        String cliente = lerTexto("Nome do cliente: ");
        double valor = lerDouble("Valor: ");
        Pedido pedido;
        switch (tipo) {
            case 1 -> pedido = new PedidoEletronico(id, cliente, valor, StatusPedido.PENDENTE);
            case 2 -> pedido = new PedidoRoupa(id, cliente, valor, StatusPedido.PENDENTE);
            case 3 -> pedido = new PedidoAlimento(id, cliente, valor, StatusPedido.PENDENTE);
            default -> throw new IllegalArgumentException("Tipo de pedido inválido.");
        }
        pedidos.add(pedido);
        System.out.println("Pedido cadastrado.");
    }

    private void alterarStatus() throws PedidoInvalidoException {
        Pedido pedido = buscarPorId(lerInteiro("ID do pedido: "));
        StatusPedido novoStatus = lerStatus();
        pedido.alterarStatus(novoStatus);
        System.out.println("Status alterado.");
    }

    private void processarPedidos() {
        Iterator<Pedido> iterator = pedidos.iterator();
        while (iterator.hasNext()) {
            Pedido pedido = iterator.next();
            try {
                pedido.processarPedido();
                System.out.printf("Pedido %d processado.%n", pedido.getId());
            } catch (PedidoInvalidoException e) {
                System.out.printf("Pedido %d não processado: %s%n", pedido.getId(), e.getMessage());
            }
        }
    }

    private void removerCancelados() {
        int removidos = 0;
        Iterator<Pedido> iterator = pedidos.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getStatus() == StatusPedido.CANCELADO) {
                iterator.remove();
                removidos++;
            }
        }
        System.out.println(removidos + " pedido(s) cancelado(s) removido(s).");
    }

    private void exibirPedidos() {
        boolean encontrou = false;
        for (Pedido pedido : pedidos) {
            if (pedido.getStatus() != StatusPedido.CANCELADO) {
                System.out.println(pedido);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pedido ativo.");
        }
    }

    private void exibirEntregaveis() {
        boolean encontrou = false;
        for (Pedido pedido : pedidos) {
            if (pedido instanceof Entregavel && pedido.getStatus() != StatusPedido.CANCELADO) {
                Entregavel entregavel = (Entregavel) pedido;
                System.out.printf("%s | frete=R$ %.2f%n", pedido, entregavel.calcularFrete());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pedido entregável.");
        }
    }

    private Pedido buscarPorId(int id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        throw new IllegalArgumentException("Pedido não encontrado.");
    }

    private StatusPedido lerStatus() {
        int opcao = lerInteiro("Novo status (1-Pendente, 2-Processando, 3-Enviado, 4-Entregue, 5-Cancelado): ");
        switch (opcao) {
            case 1: return StatusPedido.PENDENTE;
            case 2: return StatusPedido.PROCESSANDO;
            case 3: return StatusPedido.ENVIADO;
            case 4: return StatusPedido.ENTREGUE;
            case 5: return StatusPedido.CANCELADO;
            default: throw new IllegalArgumentException("Status inválido.");
        }
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        String valor = scanner.nextLine().trim();
        if (valor.isEmpty()) {
            throw new IllegalArgumentException("O texto não pode ser vazio.");
        }
        return valor;
    }

    private int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um número inteiro válido.");
        }
    }

    private double lerDouble(String mensagem) {
        System.out.print(mensagem);
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um número válido.");
        }
    }
}
