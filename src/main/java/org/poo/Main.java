package org.poo;

import org.poo.cliente.Cliente;
import org.poo.cliente.ClienteDAO;
import org.poo.contrato.Contrato;
import org.poo.contrato.ContratoDAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final ContratoDAO contratoDAO = new ContratoDAO();

    public static void main(String[] args) {

        Cliente cliente1 = createAndSaveCliente("11929826303", "Ana Zaira");
        Cliente cliente2 = createAndSaveCliente("26752965030", "Beatriz Yana");

        createAndSaveContratos(cliente1, "Contrato por tempo determinado", "21/05/2023",
                "Contrato por tempo indeterminado", "01/05/2023", "Contrato de trabalho eventual", "26/05/2023");

        createAndSaveContratos(cliente2, "Contrato de estágio", "15/10/2023", "Contrato de experiência", "16/09/2023",
                "Contrato de teletrabalho", "17/08/2023", "Contrato intermitente", "15/07/2023");

        atualizarClientes();
        mostrarClientes();

        findAndPrintClienteByID(21L); // Substituir 21L pelo ID do cliente para testar consulta por entidade Cliente específica;

        printContratoByID(34L); // Substituir 34L pelo ID do cliente para testar consulta por entidade Contrato específica;
    }

    private static Cliente createAndSaveCliente(String cpf, String nome) {
        Cliente cliente = new Cliente(cpf, nome);
        cliente.setId(clienteDAO.saveOrUpdate(cliente));
        System.out.println("> " + cliente);
        return cliente;
    }

    private static void createAndSaveContratos(Cliente cliente, String... contratosData) {
        List<Contrato> contratos = new ArrayList<>();
        for (int i = 0; i < contratosData.length; i += 2) {
            Contrato contrato = new Contrato(contratosData[i], LocalDate.parse(contratosData[i + 1], formatter), cliente.getId());
            contrato.setId(contratoDAO.saveOrUpdate(contrato));
            contratos.add(contrato);
        }
        cliente.setContratos(contratos);
    }

    private static void atualizarClientes() {
        List<Cliente> clientes = clienteDAO.findAll();
        for (Cliente cliente : clientes) {
            try {
                cliente.setNome(cliente.getNome() + "!!!");
                clienteDAO.saveOrUpdate(cliente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void mostrarClientes() {
        List<Cliente> clientes = clienteDAO.findAll();
        for (Cliente cliente : clientes) {
            List<Contrato> contratosList = clienteDAO.findContratosByClientID(cliente.getId());
            cliente.setContratos(contratosList);
        }
        System.out.println(">> " + clientes);
    }

    private static void findAndPrintClienteByID(Long id) {
        Cliente cliente = clienteDAO.findClienteByID(id);
        if (cliente != null) {
            List<Contrato> contratosList = clienteDAO.findContratosByClientID(cliente.getId());
            cliente.setContratos(contratosList);
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Nenhum cliente encontrado com o ID: " + id);
        }
    }

    public static void printContratoByID(Long contratoID) {
        ContratoDAO contratoDAO = new ContratoDAO();
        Contrato contrato = contratoDAO.findContratoByID(contratoID);

        if (contrato != null) {
            System.out.println("Contrato ID: " + contrato.getId());
            System.out.println("Redacao: " + contrato.getRedacao());
            System.out.println("Ultima Atualizacao: " + contrato.getUltimaAtualizacao());
            System.out.println("Cliente ID: " + contrato.getCliente().getId());
            System.out.println();
        } else {
            System.out.println("Nenhum contrato encontrado com ID: " + contratoID);
        }
    }
}
