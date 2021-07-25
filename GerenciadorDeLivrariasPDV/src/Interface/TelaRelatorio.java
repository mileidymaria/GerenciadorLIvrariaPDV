package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.ClienteFisicoDAO;
import DAO.ClienteJuridicoDAO;
import DAO.FornecedorDAO;
import DAO.FuncionarioDAO;
import DAO.LivroDAO;
import DAO.PedidoDAO;
import Model.ClienteFisico;
import Model.ClienteJuridico;
import Model.Endereco;
import Model.Fornecedor;
import Model.Funcionario;
import Model.ItensPedidos;
import Model.Livro;
import Model.Pedido;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class TelaRelatorio extends JFrame {

	private JPanel contentPane;
	private JPanel panelRelatorioPedidos;
	private JPanel panelRelatorioFuncionarios;
	private JPanel panelRelatorioClientes;
	private JPanel panelRelatorioFornecedores;
	private JPanel panelRelatorioLivros;
	
	private JTabbedPane tabbedPaneNavegacao;
	private JTable tablePedidos;
	private JTable tableFuncionarios;
	private JTable tableFornecedores;
	private JTable tableLivros;
	private JTable tableClientes;
	
	private JTextField textFieldPesquisarPedidos;
	private JTextField textFieldPesquisarFornecedor;
	private JTextField textFieldPesquisarFuncionario;
	private JTextField textFieldPesquisarLivro;
	private JTextField textFieldPesquisarCliente;

	private JTextArea textAreaDescricaoPedido;
	private JTextArea textAreaRelatorioCliente;
	private JTextArea textAreaRelatorioLivros;
	private JTextArea textAreaDescricaoFornecedor;
	private JTextArea textAreaRelatorioFuncionario;
	
	private int selecionadoTablePedidos, selecionadoTableFuncionarios, selecionadoTableFornecedores, selecionadoTableLivros, selecionadoTableClientes;
	private JTable tableItensPedidos;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
			
		catch(Exception e) {
			  System.out.println("Error setting native LAF: " + e);
		}		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRelatorio frame = new TelaRelatorio();
					frame.setUndecorated(true);
					frame.setExtendedState(MAXIMIZED_BOTH);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public void lerTabelaClientes() {
    	
    	//"ID", "Nome/Razão Social", "E-mail", "Telefone", "Cliente desde"
        
        DefaultTableModel modelo = (DefaultTableModel) tableClientes.getModel();
        modelo.setNumRows(0);
    	ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
    	ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
    	
        for (ClienteFisico clienteFisico : clienteFisicoDAO.ListarClientesRelatorio(textFieldPesquisarCliente.getText())) {
        	
        	if(clienteFisico.isStatus() == true) {
            	modelo.addRow(new Object[]{
    	            	clienteFisico.getId(),		
    	                clienteFisico.getNome(),
    	                clienteFisico.getCpf(),
    	                clienteFisico.getEmail(),
    	                clienteFisico.getCelular(),
    	                clienteFisico.getDataCadastro(),
    	                "CLIENTE ATIVO"
                	});        		
        	}
        	else {
            	modelo.addRow(new Object[]{
    	            	clienteFisico.getId(),		
    	                clienteFisico.getNome(),
    	                clienteFisico.getCpf(),
    	                clienteFisico.getEmail(),
    	                clienteFisico.getCelular(),
    	                clienteFisico.getDataCadastro(),
    	                "CLIENTE INATIVO"
                });        		
        	}


        }

        for (ClienteJuridico clienteJuridico : clienteJuridicoDAO.ListarClientesRelatorio(textFieldPesquisarCliente.getText())) {

        	//"ID", "Nome", "CNPJ","Telefone", "Email"
        	
        	if(clienteJuridico.isStatus() == true) {
            	modelo.addRow(new Object[]{
            			clienteJuridico.getId(),		
            			clienteJuridico.getNome(),
            			clienteJuridico.getCnpj(),
            			clienteJuridico.getEmail(),
            			clienteJuridico.getCelular(),
            			clienteJuridico.getDataCadastro(),    
            			"CLIENTE ATIVO"
            	});        		
        	}
        	else {
            	modelo.addRow(new Object[]{
            			clienteJuridico.getId(),		
            			clienteJuridico.getNome(),
            			clienteJuridico.getCnpj(),
            			clienteJuridico.getEmail(),
            			clienteJuridico.getCelular(),
            			clienteJuridico.getDataCadastro(),    
            			"CLIENTE INATIVO"
            	});        		
        	}


        }        

    }
    
    public void lerTabelaFuncionarios() {
        
        DefaultTableModel modelo = (DefaultTableModel) tableFuncionarios.getModel();
        modelo.setNumRows(0);      
    	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    	//"ID", "Funcionário", "E-mail", "Data de nascimento", "Funcionário desde"
        for (Funcionario funcionario : funcionarioDAO.ListarFuncionariosRelatorio(textFieldPesquisarFuncionario.getText())) {
        	
        	if (funcionario.isStatus() == true) {
            	modelo.addRow(new Object[]{
            			funcionario.getId(),	
            			funcionario.getNome(),
            			funcionario.getEmail(),
            			funcionario.getCelular(),
            			funcionario.getDataCadastro(),
            			"FUNCIONÁRIO ATIVO"
            	});        		
        	}
        	else {
            	modelo.addRow(new Object[]{
            			funcionario.getId(),	
            			funcionario.getNome(),
            			funcionario.getEmail(),
            			funcionario.getCelular(),
            			funcionario.getDataCadastro(),
            			"FUNCIONÁRIO INATIVO"
            	});        		
        	}

        }
        
    }    
    
    public void lerTabelaFornecedores() {
        
        DefaultTableModel modelo = (DefaultTableModel) tableFornecedores.getModel();
        modelo.setNumRows(0);
    	FornecedorDAO fornecedorDAO = new FornecedorDAO();
    	

        for (Fornecedor fornecedor : fornecedorDAO.ListarFornecedoresRelatorio(textFieldPesquisarFornecedor.getText())) {

        	//"ID", "Nome", "CNPJ","Telefone", "Email"
        	//"ID", "Fornecedor", "E-mail", "Fornecedor desde"
        	
        	if(fornecedor.isStatus() == true) {
            	if(fornecedor.getCpf() == null) {
                	modelo.addRow(new Object[]{
                			fornecedor.getId(),	
                			fornecedor.getNome(),
                			fornecedor.getCnpj(),
                			//fornecedor.getCpf(),
                			fornecedor.getEmail(),
                			fornecedor.getDataCadastro(),
                			"FORNECEDOR ATIVO"
                	});        		
            	}
            	
            	else {
                	modelo.addRow(new Object[]{
                			fornecedor.getId(),	
                			fornecedor.getNome(),
                			//fornecedor.getCnpj(),
                			fornecedor.getCpf(),
                			fornecedor.getEmail(),
                			fornecedor.getDataCadastro(),
                			"FORNECDOR ATIVO"
                	});        		
            	}
        	}
        	else {
            	if(fornecedor.getCpf() == null) {
                	modelo.addRow(new Object[]{
                			fornecedor.getId(),	
                			fornecedor.getNome(),
                			fornecedor.getCnpj(),
                			//fornecedor.getCpf(),
                			fornecedor.getEmail(),
                			fornecedor.getDataCadastro(),
                			"FORNECEDOR INATIVO"
                	});        		
            	}
            	
            	else {
                	modelo.addRow(new Object[]{
                			fornecedor.getId(),	
                			fornecedor.getNome(),
                			//fornecedor.getCnpj(),
                			fornecedor.getCpf(),
                			fornecedor.getEmail(),
                			fornecedor.getDataCadastro(),
                			"FORNECEDOR INATIVO"
                	});        		
            	}
        	}
        	



        }
    }   
	
	public void lerTabelaLivros() {
        DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
        modelo.setNumRows(0);
    	LivroDAO livroDAO = new LivroDAO();
    	

        for (Livro livro : livroDAO.ListarLivrosRelatorio(textFieldPesquisarLivro.getText())) {
        	//"ID", "Título", "ISBN", "Preço", "Quantidade de páginas", "Quantidade no estoque", "Editora", "Fornecedor"
        	//"ID", "Título", "Preço", "Qtde. estoque", "Qtde. página"
        	modelo.addRow(new Object[]{
        			livro.getId(),                
        			livro.getTitulo(),
        			livro.getIsbn(),
        			livro.getPreco(),
        			livro.getQuantidadePaginas(),
        			livro.getQuantidadeEstoque(),
        			livro.getEditora(),
        			livro.getIdFornecedor()
        	});

        }

	} 
	
	public void lerTabelaPedidos() {
        DefaultTableModel modelo = (DefaultTableModel) tablePedidos.getModel();
        modelo.setNumRows(0);
    	PedidoDAO pedidoDAO = new PedidoDAO();
    	ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
    	ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
    	//"ID", "ID do Cliente", "Cliente", "Forma de pagamento", "Data", "Quantidade de produtos", "Total"
        for (Pedido pedido : pedidoDAO.Listar()) {

        	if(pedido.getIdClienteFisico() == 1) {
            	modelo.addRow(new Object[]{
            			pedido.getId(),
            			pedido.getIdClienteJuridico(),
            			clienteJuridicoDAO.RetornarNomeCliente(pedido.getIdClienteJuridico()),
            			pedido.getFormaPagamento(),
            			pedido.getDataPedido(),
            			pedido.getTotal()
            			
            	});  
            	System.out.println("\nDentro da funcao: " + pedido.getIdClienteJuridico());
        	}
        	else {
            	modelo.addRow(new Object[]{
            			pedido.getId(),
            			pedido.getIdClienteFisico(),
            			clienteFisicoDAO.RetornarNomeCliente(pedido.getIdClienteFisico()),
            			pedido.getFormaPagamento(),
            			pedido.getDataPedido(),
            			pedido.getTotal()
            	});  
            	System.out.println("\nDentro da funcao: " + pedido.getIdClienteFisico());
        	}
        }
	}	
	
	public void lerTabelaItensVendidos(int idPedido) {
		
        DefaultTableModel modelo = (DefaultTableModel) tableItensPedidos.getModel();
        modelo.setNumRows(0);
    	PedidoDAO pedidoDAO = new PedidoDAO();
        
    	for (ItensPedidos itensPedidos : pedidoDAO.ConsultarItem(idPedido)) {

        	    modelo.addRow(new Object[]{
        	    	itensPedidos.getIdLivro(),
        	    	pedidoDAO.RetornarTituloLivro(itensPedidos.getIdLivro()),
        	    	itensPedidos.getQuantidade()
            	}); 
        	    System.out.println(itensPedidos.getIdLivro() + "\n " + pedidoDAO.RetornarTituloLivro(itensPedidos.getIdLivro()) + "\n" + itensPedidos.getQuantidade());
        }
    	
	}	
	
	/**
	 * Create the frame.
	 */
	public TelaRelatorio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1364, 788);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelImgCaixa = new JPanel();
		panelImgCaixa.setBounds(0, 0, 304, 867);
		contentPane.add(panelImgCaixa);
		panelImgCaixa.setLayout(null);
		
		JLabel lblImgCaixa = new JLabel("");
		lblImgCaixa.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgTelaCaixa.png")));
		lblImgCaixa.setBounds(0, 0, 304, 867);
		panelImgCaixa.add(lblImgCaixa);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 304, 867);
		contentPane.add(panel);
		
		JPanel panelNavegacao = new JPanel();
		panelNavegacao.setBounds(363, 36, 888, 43);
		panelNavegacao.setBorder(new LineBorder(new Color(0, 102, 102)));
		panelNavegacao.setBackground(Color.DARK_GRAY);
		contentPane.add(panelNavegacao);
		panelNavegacao.setLayout(null);
		
				JLabel lblNavegacao = new JLabel("R  E  L  A  T  Ó  R  I  O  S");
				lblNavegacao.setBounds(10, 11, 844, 31);
				panelNavegacao.add(lblNavegacao);
				lblNavegacao.setHorizontalAlignment(SwingConstants.CENTER);
				lblNavegacao.setFont(new Font("Lucida Console", Font.PLAIN, 30));
				lblNavegacao.setForeground(Color.WHITE);		
		
		tabbedPaneNavegacao = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneNavegacao.setBounds(363, 100, 888, 649);
		contentPane.add(tabbedPaneNavegacao);
		
		panelRelatorioClientes = new JPanel();
		panelRelatorioClientes.setBackground(new Color(51, 51, 51));
		tabbedPaneNavegacao.addTab("Clientes", null, panelRelatorioClientes, null);
		panelRelatorioClientes.setLayout(null);
		
		JScrollPane scrollPaneTabelaClientes = new JScrollPane();
		scrollPaneTabelaClientes.setBounds(10, 80, 863, 295);
		panelRelatorioClientes.add(scrollPaneTabelaClientes);

		tableClientes = new JTable();
		tableClientes.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				selecionadoTableClientes = tableClientes.getSelectedRow();
				ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
				ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
				ClienteFisico clienteFisico = new ClienteFisico();
				ClienteJuridico clienteJuridico = new ClienteJuridico();
				Endereco enderecoClienteFisico = new Endereco();
				Endereco enderecoClienteJuridico = new Endereco();
				
				clienteFisico.setNome(String.valueOf(tableClientes.getValueAt(selecionadoTableClientes, 1)));
				clienteJuridico.setNome(String.valueOf(tableClientes.getValueAt(selecionadoTableClientes, 1)));
				
				clienteFisicoDAO.ConsultarPeloNome(clienteFisico, enderecoClienteFisico);
				clienteJuridicoDAO.ConsultarPelaRazaoSocial(clienteJuridico, enderecoClienteJuridico);
				
				if(String.valueOf(tableClientes.getValueAt(selecionadoTableClientes, 2)).length() != 14) {
					textAreaRelatorioCliente.setText("\nID:  " + clienteJuridico.getId() + "  Razão social:  " + clienteJuridico.getNome() +
													 "  Telefone:  " + clienteJuridico.getCelular() + "  Email: " + clienteJuridico.getEmail() +
													 "  \n\nCNPJ:  " + clienteJuridico.getCnpj() + "  Data de criação:  " + clienteJuridico.getDataCriacao() +
													 "  Data de cadastro: " + clienteJuridico.getDataCadastro() + "\n\nRua:  " + enderecoClienteJuridico.getRua() +
													 "  Número: " + enderecoClienteJuridico.getNumero() + "  Bairro: " + enderecoClienteJuridico.getBairro() +
													 "  CEP: " + enderecoClienteJuridico.getCep() + "  \n\nCidade: " + enderecoClienteJuridico.getCidade() +
													 "  Estado: " + enderecoClienteJuridico.getEstado());

				}
				if(String.valueOf(tableClientes.getValueAt(selecionadoTableClientes, 2)).length() == 14) {
					textAreaRelatorioCliente.setText("\nID:  " + clienteFisico.getId() + "  Nome:  " + clienteFisico.getNome() +
							 						 "  Celular:  " + clienteFisico.getCelular() + "  Email: " + clienteFisico.getEmail() +
							 						 "  \n\nCPF:  " + clienteFisico.getCpf() + "  Data de criação:  " + clienteFisico.getDataCriacao() +
							 						 "  Data de cadastro: " + clienteFisico.getDataCadastro() + "\n\nRua:  " + enderecoClienteFisico.getRua() +
							 						 "  Número: " + enderecoClienteFisico.getNumero() + "  Bairro: " + enderecoClienteFisico.getBairro() +
							 						 "  CEP: " + enderecoClienteFisico.getCep() + "  \n\nCidade: " + enderecoClienteFisico.getCidade() +
							 						 "  Estado: " + enderecoClienteFisico.getEstado());	

				}
				
				//String.valueOf(tableVendas.getValueAt(selecionadoTableClientes, 1))
				//textAreaRelatorioCliente
			}
		});
		tableClientes.setModel(new DefaultTableModel(
				//id_cliente_juridico, id_cliente_fisico, id_funcionario, data, forma_pagamento, total
			new Object[][] {
			},
			new String[] {
					"ID", "Nome/Razão Social", "CPF/CNPJ","E-mail", "Telefone", "Cliente desde", "Status"
			}
		));
		scrollPaneTabelaClientes.setViewportView(tableClientes);		
		
		JLabel lblPesquisarCientes = new JLabel("Pesquisar:");
		lblPesquisarCientes.setForeground(Color.WHITE);
		lblPesquisarCientes.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblPesquisarCientes.setBounds(10, 38, 96, 14);
		panelRelatorioClientes.add(lblPesquisarCientes);
		
		textFieldPesquisarCliente = new JTextField();
		textFieldPesquisarCliente.setText("Pesquise por id, CPF/CNPJ, email ou nome do cliente");
		textFieldPesquisarCliente.setForeground(Color.GRAY);
		textFieldPesquisarCliente.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textFieldPesquisarCliente.setColumns(10);
		textFieldPesquisarCliente.setBounds(116, 35, 578, 20);
		panelRelatorioClientes.add(textFieldPesquisarCliente);
		
		JButton btnPesquisarClientes = new JButton("");
		btnPesquisarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lerTabelaClientes();
			}
		});
		btnPesquisarClientes.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgBuscar.png")));
		btnPesquisarClientes.setContentAreaFilled(false);
		btnPesquisarClientes.setBackground(Color.DARK_GRAY);
		btnPesquisarClientes.setBounds(704, 34, 26, 23);
		panelRelatorioClientes.add(btnPesquisarClientes);
		
		textAreaRelatorioCliente = new JTextArea();
		textAreaRelatorioCliente.setForeground(Color.WHITE);
		textAreaRelatorioCliente.setFont(new Font("Lucida Console", Font.ITALIC, 12));
		textAreaRelatorioCliente.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		textAreaRelatorioCliente.setBackground(new Color(51, 51, 51));
		textAreaRelatorioCliente.setBounds(10, 402, 770, 176);
		panelRelatorioClientes.add(textAreaRelatorioCliente);
		
		JButton btnConcluirConsultaCliente = new JButton("");
		btnConcluirConsultaCliente.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 textAreaRelatorioCliente.setText("");
			}
		});
		btnConcluirConsultaCliente.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnConcluirConsultaCliente.setBounds(784, 448, 89, 88);
		btnConcluirConsultaCliente.setContentAreaFilled(false);
		btnConcluirConsultaCliente.setBackground(Color.DARK_GRAY);
		panelRelatorioClientes.add(btnConcluirConsultaCliente);
		
		panelRelatorioLivros = new JPanel();
		panelRelatorioLivros.setBackground(new Color(51, 51, 51));
		tabbedPaneNavegacao.addTab("Livros", null, panelRelatorioLivros, null);
		panelRelatorioLivros.setLayout(null);
		
		JScrollPane scrollPaneTabelaLivros = new JScrollPane();
		scrollPaneTabelaLivros.setBounds(10, 82, 863, 295);
		panelRelatorioLivros.add(scrollPaneTabelaLivros);

		tableLivros = new JTable();
		tableLivros.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selecionadoTableLivros = tableLivros.getSelectedRow();
				
				LivroDAO livroDAO = new LivroDAO();
				Livro livro = new Livro();
				livro.setId(Integer.parseInt(String.valueOf(tableLivros.getValueAt(selecionadoTableLivros, 0))));
				livroDAO.Consultar(livro);
				
				textAreaRelatorioLivros.setText("ID:   " + livro.getId() + "    Título:   " + livro.getTitulo() + "    Categoria:   " + livro.getCategoria() +
												"    ISBN:   " + livro.getIsbn() + "   \n\nEditora:  " + livro.getEditora() + "   Fornecedor" + livro.getIdFornecedor() + 
												"   Preço:  " + livro.getPreco() + "   Quantidade de páginas:  " + livro.getQuantidadePaginas() 
												+ "   \n\nQuantidade no estoque:  " + livro.getQuantidadeEstoque() + "Quantidade de livros vendidos: ");
				//String.valueOf(tableVendas.getValueAt(selecionadoTableClientes, 1))
				//textAreaRelatorioLivros
			}
		});
		tableLivros.setModel(new DefaultTableModel(
				//id_cliente_juridico, id_cliente_fisico, id_funcionario, data, forma_pagamento, total
			new Object[][] {
			},
			new String[] {
					"ID", "Título", "ISBN", "Preço", "Quantidade de páginas", "Quantidade no estoque", "Editora", "Fornecedor"
			}
		));
		scrollPaneTabelaLivros.setViewportView(tableLivros);		
		
		JLabel lblPesquisarLivro = new JLabel("Pesquisar:");
		lblPesquisarLivro.setForeground(Color.WHITE);
		lblPesquisarLivro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblPesquisarLivro.setBounds(10, 40, 96, 14);
		panelRelatorioLivros.add(lblPesquisarLivro);
		
		textFieldPesquisarLivro = new JTextField();
		textFieldPesquisarLivro.setText("Pesquise por id, título, categoria, ISBN ou editora do livro");
		textFieldPesquisarLivro.setForeground(Color.GRAY);
		textFieldPesquisarLivro.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textFieldPesquisarLivro.setColumns(10);
		textFieldPesquisarLivro.setBounds(116, 37, 578, 20);
		panelRelatorioLivros.add(textFieldPesquisarLivro);
		
		JButton btnPesquisarLivro = new JButton("");
		btnPesquisarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lerTabelaLivros();
			}
		});
		btnPesquisarLivro.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgBuscar.png")));
		btnPesquisarLivro.setContentAreaFilled(false);
		btnPesquisarLivro.setBackground(Color.DARK_GRAY);
		btnPesquisarLivro.setBounds(704, 36, 26, 23);
		panelRelatorioLivros.add(btnPesquisarLivro);
		
		textAreaRelatorioLivros = new JTextArea();
		textAreaRelatorioLivros.setForeground(Color.WHITE);
		textAreaRelatorioLivros.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textAreaRelatorioLivros.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		textAreaRelatorioLivros.setBackground(new Color(51, 51, 51));
		textAreaRelatorioLivros.setBounds(10, 404, 763, 176);
		panelRelatorioLivros.add(textAreaRelatorioLivros);
		
		JButton btnConcluirConsultaLivros = new JButton("");
		btnConcluirConsultaLivros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaRelatorioLivros.setText("");
			}
		});
		btnConcluirConsultaLivros.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnConcluirConsultaLivros.setContentAreaFilled(false);
		btnConcluirConsultaLivros.setBackground(Color.DARK_GRAY);
		btnConcluirConsultaLivros.setBounds(784, 447, 89, 88);
		panelRelatorioLivros.add(btnConcluirConsultaLivros);
		
		panelRelatorioFuncionarios = new JPanel();
		panelRelatorioFuncionarios.setBackground(new Color(51, 51, 51));
		tabbedPaneNavegacao.addTab("Funcionarios", null, panelRelatorioFuncionarios, null);
		panelRelatorioFuncionarios.setLayout(null);

		panelRelatorioFornecedores = new JPanel();
		panelRelatorioFornecedores.setBackground(new Color(51, 51, 51));
		panelRelatorioFuncionarios.setBackground(new Color(51, 51, 51));
		tabbedPaneNavegacao.addTab("Fornecedores", null, panelRelatorioFornecedores, null);
		panelRelatorioFornecedores.setLayout(null);
		
		JScrollPane scrollPaneTabelaFornecedores = new JScrollPane();
		scrollPaneTabelaFornecedores.setBounds(10, 68, 863, 295);
		panelRelatorioFornecedores.add(scrollPaneTabelaFornecedores);

		tableFornecedores = new JTable();
		tableFornecedores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				selecionadoTableFornecedores = tableFornecedores.getSelectedRow();
				
				FornecedorDAO fornecedorDAO = new FornecedorDAO();
				Fornecedor fornecedor = new Fornecedor();
				Endereco endereco = new Endereco();
				fornecedor.setId(Integer.parseInt(String.valueOf(tableFornecedores.getValueAt(selecionadoTableFornecedores, 0))));
				fornecedorDAO.Consultar(fornecedor, endereco);
				
				textAreaDescricaoFornecedor.setText("ID:  " + fornecedor.getId() + "   Nome:  " + fornecedor.getNome() + "   Data de criação:  " + fornecedor.getDataCriacao() + 
													 "  \n\nCPF: " + fornecedor.getCpf() + "   CNPJ: " + fornecedor.getCnpj() + "   Celular:  " + fornecedor.getCelular() + "   \n\nData de cadastro:  " + fornecedor.getDataCadastro() +
													 "  Email:  " + fornecedor.getEmail() + 
													 "\n\n\nRua:   " + endereco.getRua() + "   Número:  " + endereco.getNumero() + "    Bairro:   " + endereco.getBairro() +
													 "\n\nCidade:   " + endereco.getCidade() + "    Estado:   " + endereco.getEstado() + "   CEP:   " + endereco.getCep());
				
				//String.valueOf(tableVendas.getValueAt(selecionadoTableClientes, 1))
				//textAreaRelatorioLivros				
						
			
			}
		});
		tableFornecedores.setModel(new DefaultTableModel(

			new Object[][] {
			},
			new String[] {
					"ID", "Fornecedor", "CPF/CNPJ","E-mail", "Fornecedor desde", "Status"
			}
		));
		scrollPaneTabelaFornecedores.setViewportView(tableFornecedores);		
		
		
		JLabel lblPesquisarFornecedor = new JLabel("Pesquisar:");
		lblPesquisarFornecedor.setForeground(Color.WHITE);
		lblPesquisarFornecedor.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblPesquisarFornecedor.setBounds(10, 26, 96, 14);
		panelRelatorioFornecedores.add(lblPesquisarFornecedor);
		
		textFieldPesquisarFornecedor = new JTextField();
		textFieldPesquisarFornecedor.setText("Pesquise por id ou nome do fornecedor");
		textFieldPesquisarFornecedor.setForeground(Color.GRAY);
		textFieldPesquisarFornecedor.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textFieldPesquisarFornecedor.setColumns(10);
		textFieldPesquisarFornecedor.setBounds(116, 23, 578, 20);
		panelRelatorioFornecedores.add(textFieldPesquisarFornecedor);
		
		JButton btnPesquisarFornecedor = new JButton("");
		btnPesquisarFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lerTabelaFornecedores();
			}
		});
		btnPesquisarFornecedor.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgBuscar.png")));
		btnPesquisarFornecedor.setContentAreaFilled(false);
		btnPesquisarFornecedor.setBackground(Color.DARK_GRAY);
		btnPesquisarFornecedor.setBounds(704, 22, 26, 23);
		panelRelatorioFornecedores.add(btnPesquisarFornecedor);
		
		textAreaDescricaoFornecedor = new JTextArea();
		textAreaDescricaoFornecedor.setForeground(Color.WHITE);
		textAreaDescricaoFornecedor.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textAreaDescricaoFornecedor.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		textAreaDescricaoFornecedor.setBackground(new Color(51, 51, 51));
		textAreaDescricaoFornecedor.setBounds(10, 390, 756, 176);
		panelRelatorioFornecedores.add(textAreaDescricaoFornecedor);
		
		JButton btnConcluirConsultaFornecedor = new JButton("");
		btnConcluirConsultaFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaDescricaoFornecedor.setText("");
			}
		});
		btnConcluirConsultaFornecedor.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnConcluirConsultaFornecedor.setContentAreaFilled(false);
		btnConcluirConsultaFornecedor.setBackground(Color.DARK_GRAY);
		btnConcluirConsultaFornecedor.setBounds(784, 428, 89, 88);
		panelRelatorioFornecedores.add(btnConcluirConsultaFornecedor);
		panelRelatorioFuncionarios.setLayout(null);		
		
		panelRelatorioPedidos = new JPanel();
		panelRelatorioPedidos.setBackground(new Color(51, 51, 51));
		panelRelatorioFuncionarios.setBackground(new Color(51, 51, 51));
		
		JScrollPane scrollPaneRelatorioFuncionario = new JScrollPane();
		scrollPaneRelatorioFuncionario.setBounds(10, 75, 863, 295);
		panelRelatorioFuncionarios.add(scrollPaneRelatorioFuncionario);

		tableFuncionarios = new JTable();
		tableFuncionarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				selecionadoTableFuncionarios = tableFuncionarios.getSelectedRow();
				
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				Funcionario funcionario = new Funcionario();
				Endereco endereco = new Endereco();
				funcionario.setId(Integer.parseInt(String.valueOf(tableFuncionarios.getValueAt(selecionadoTableFuncionarios, 0))));
				funcionarioDAO.Consultar(funcionario, endereco);
				
				textAreaRelatorioFuncionario.setText("ID:  " + funcionario.getId() + "   Nome:  " + funcionario.getNome() + "   Data de nascimento:  " + funcionario.getDataCriacao() + 
													 "  CPF: " + funcionario.getCpf() + "   \n\nCelular:  " + funcionario.getCelular() + "   Data de cadastro:  " + funcionario.getDataCadastro() + "   \n\nCargo:  " + funcionario.getCargo() +
													 "  Email:  " + funcionario.getEmail() + 
													 "\n\n\nRua:   " + endereco.getRua() + "   Número:  " + endereco.getNumero() + "    Bairro:   " + endereco.getBairro() +
													 "\n\nCidade:   " + endereco.getCidade() + "    Estado:   " + endereco.getEstado() + "   CEP:   " + endereco.getCep());
				
				//String.valueOf(tableVendas.getValueAt(selecionadoTableClientes, 1))
				//textAreaRelatorioLivros				
			
			}
		});
		tableFuncionarios.setModel(new DefaultTableModel(
				//id_cliente_juridico, id_cliente_fisico, id_funcionario, data, forma_pagamento, total
			new Object[][] {
			},
			new String[] {
				"ID", "Funcionário", "E-mail", "Celular", "Funcionário desde", "Status"
			}
		));
		scrollPaneRelatorioFuncionario.setViewportView(tableFuncionarios);		
		
		JLabel lblPesquisarFuncionario = new JLabel("Pesquisar:");
		lblPesquisarFuncionario.setForeground(Color.WHITE);
		lblPesquisarFuncionario.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblPesquisarFuncionario.setBounds(10, 33, 96, 14);
		panelRelatorioFuncionarios.add(lblPesquisarFuncionario);
		
		textFieldPesquisarFuncionario = new JTextField();
		textFieldPesquisarFuncionario.setText("Pesquise por id, email, cpf ou nome do funcionário");
		textFieldPesquisarFuncionario.setForeground(Color.GRAY);
		textFieldPesquisarFuncionario.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textFieldPesquisarFuncionario.setColumns(10);
		textFieldPesquisarFuncionario.setBounds(116, 30, 578, 20);
		panelRelatorioFuncionarios.add(textFieldPesquisarFuncionario);
		
		JButton btnBuscarFuncionario = new JButton("");
		btnBuscarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lerTabelaFuncionarios();
			}
		});
		btnBuscarFuncionario.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgBuscar.png")));
		btnBuscarFuncionario.setContentAreaFilled(false);
		btnBuscarFuncionario.setBackground(Color.DARK_GRAY);
		btnBuscarFuncionario.setBounds(704, 29, 26, 23);
		panelRelatorioFuncionarios.add(btnBuscarFuncionario);
		
		textAreaRelatorioFuncionario = new JTextArea();
		textAreaRelatorioFuncionario.setForeground(Color.WHITE);
		textAreaRelatorioFuncionario.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textAreaRelatorioFuncionario.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		textAreaRelatorioFuncionario.setBackground(new Color(51, 51, 51));
		textAreaRelatorioFuncionario.setBounds(10, 397, 769, 176);
		panelRelatorioFuncionarios.add(textAreaRelatorioFuncionario);
		
		JButton btnConcluirConsultaFuncionario = new JButton("");
		btnConcluirConsultaFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaRelatorioFuncionario.setText("");
			}
		});
		btnConcluirConsultaFuncionario.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnConcluirConsultaFuncionario.setContentAreaFilled(false);
		btnConcluirConsultaFuncionario.setBackground(Color.DARK_GRAY);
		btnConcluirConsultaFuncionario.setBounds(784, 443, 89, 88);
		panelRelatorioFuncionarios.add(btnConcluirConsultaFuncionario);
		tabbedPaneNavegacao.addTab("Pedidos", null, panelRelatorioPedidos, null);
		panelRelatorioPedidos.setLayout(null);
		
		JScrollPane scrollPaneTabelaPedidos = new JScrollPane();
		scrollPaneTabelaPedidos.setBounds(10, 69, 863, 295);
		panelRelatorioPedidos.add(scrollPaneTabelaPedidos);
		
		tablePedidos = new JTable();
		tablePedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				selecionadoTablePedidos = tablePedidos.getSelectedRow();
				
				PedidoDAO pedidoDAO = new PedidoDAO();
				Pedido pedido = new Pedido();
				pedido.setId(Integer.parseInt(String.valueOf(tablePedidos.getValueAt(selecionadoTablePedidos, 0))));
				pedidoDAO.Consultar(pedido);
				
				textAreaDescricaoPedido.setText("ID:  " + pedido.getId() + "   Cliente:  " + String.valueOf(tablePedidos.getValueAt(selecionadoTablePedidos, 2)) + "   Data do pedido:  " + pedido.getDataPedido() + 
													 "  \n\nFuncionario: " + pedido.getIdFuncionario() + "   Forma de pagamento: " + pedido.getFormaPagamento() + "   Total:  " + pedido.getTotal());
				lerTabelaItensVendidos(pedido.getId());
				//String.valueOf(tableVendas.getValueAt(selecionadoTableClientes, 1))
				//textAreaRelatorioLivros		
				
			}
		});
		tablePedidos.setModel(new DefaultTableModel(
				//id_cliente_juridico, id_cliente_fisico, id_funcionario, data, forma_pagamento, total
			new Object[][] {
			},
			new String[] {
				"ID", "ID do Cliente", "Cliente", "Forma de pagamento", "Data", "Total"
			}
		));
		scrollPaneTabelaPedidos.setViewportView(tablePedidos);
		
		JLabel lblNavegacaoPedido = new JLabel("Pesquisar:");
		lblNavegacaoPedido.setForeground(Color.WHITE);
		lblNavegacaoPedido.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblNavegacaoPedido.setBounds(10, 27, 96, 14);
		panelRelatorioPedidos.add(lblNavegacaoPedido);
		
		textFieldPesquisarPedidos = new JTextField();
		textFieldPesquisarPedidos.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textFieldPesquisarPedidos.setForeground(Color.GRAY);
		textFieldPesquisarPedidos.setText("Pesquise por id, cliente ou funcion\u00E1rio");
		textFieldPesquisarPedidos.setBounds(116, 24, 578, 20);
		panelRelatorioPedidos.add(textFieldPesquisarPedidos);
		textFieldPesquisarPedidos.setColumns(10);
		
		JButton btnPesquisarPedido = new JButton("");
		btnPesquisarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lerTabelaPedidos();
			}
		});
		btnPesquisarPedido.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgBuscar.png")));
		btnPesquisarPedido.setBounds(704, 23, 26, 23);
		btnPesquisarPedido.setContentAreaFilled(false);
		btnPesquisarPedido.setBackground(Color.DARK_GRAY);
		panelRelatorioPedidos.add(btnPesquisarPedido);
		
		textAreaDescricaoPedido = new JTextArea();
		textAreaDescricaoPedido.setBackground(new Color(51, 51, 51));
		textAreaDescricaoPedido.setBounds(10, 391, 863, 101);
		textAreaDescricaoPedido.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		textAreaDescricaoPedido.setFont(new Font("Lucida Console", Font.ITALIC, 14));
		textAreaDescricaoPedido.setForeground(Color.WHITE);
		panelRelatorioPedidos.add(textAreaDescricaoPedido);
		
		JButton btnConcluirConsultaPedidos = new JButton("");
		btnConcluirConsultaPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaDescricaoPedido.setText("");
			}
		});
		btnConcluirConsultaPedidos.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnConcluirConsultaPedidos.setContentAreaFilled(false);
		btnConcluirConsultaPedidos.setBackground(Color.DARK_GRAY);
		btnConcluirConsultaPedidos.setBounds(784, 503, 89, 88);
		panelRelatorioPedidos.add(btnConcluirConsultaPedidos);
		
		JScrollPane scrollPaneTableItensPedidos = new JScrollPane();
		scrollPaneTableItensPedidos.setBounds(10, 503, 526, 107);
		panelRelatorioPedidos.add(scrollPaneTableItensPedidos);
		
		tableItensPedidos = new JTable();
		tableItensPedidos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"ID do livro", "Livro", "Quantidade"
			}
		));
		scrollPaneTableItensPedidos.setViewportView(tableItensPedidos);
		

				
	}
}
