package Interface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import DAO.ClienteFisicoDAO;
import DAO.ClienteJuridicoDAO;
import DAO.LivroDAO;
import DAO.PedidoDAO;
import Model.ClienteFisico;
import Model.ClienteJuridico;
import Model.ItensPedidos;
import Model.Livro;
import Model.Pedido;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCaixa extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldBuscarCliente;
	private JTextField textFieldNomeCliente;
	private JTextField textFieldEmailCliente;
	private JTextField textFieldBuscarLivro;
	private JTextField textFieldTitulo;
	private JTextField textFieldPrecoLivro;
	private JTextField textFieldQtdEstoque;
	private JLabel lblCPFouCNPJ;
	private JLabel lblNomeOuRazaoSocial;
	
	private JTable tableLivrosDaVenda;
	private JComboBox comboBoxFormaDePagamento;
	

	private DefaultTableModel modeloTableLivrosDaVenda;
	
	private double auxiliar;
	private int quantidadeDeProdutos;
	private boolean flagClienteJuridico;
	private boolean flagClienteFisico;
	private JTextField textFieldTotalVenda;
	
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
					TelaCaixa frame = new TelaCaixa();
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

	/**
	 * Create the frame.
	 */
	public TelaCaixa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1203, 833);
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
		lblImgCaixa.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgTelaCaixa.png")));
		lblImgCaixa.setBounds(0, 0, 304, 867);
		panelImgCaixa.add(lblImgCaixa);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 304, 867);
		contentPane.add(panel);
		
		JPanel panelBuscar = new JPanel();
		panelBuscar.setBackground(Color.DARK_GRAY);
		panelBuscar.setBounds(382, 59, 727, 137);
		contentPane.add(panelBuscar);
		panelBuscar.setLayout(null);
		
		JLabel lblBuscarCliente = new JLabel("CPF/CNPJ:");
		lblBuscarCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblBuscarCliente.setForeground(Color.WHITE);
		lblBuscarCliente.setBounds(10, 11, 82, 14);
		panelBuscar.add(lblBuscarCliente);
		
		textFieldBuscarCliente = new JTextField();
		textFieldBuscarCliente.setBackground(Color.DARK_GRAY);
		textFieldBuscarCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldBuscarCliente.setForeground(Color.WHITE);
		textFieldBuscarCliente.setBounds(89, 8, 120, 20);
		textFieldBuscarCliente.setBorder(null);
		panelBuscar.add(textFieldBuscarCliente);
		textFieldBuscarCliente.setColumns(10);
		
		JLabel lblBarraBuscarCliente = new JLabel("");
		lblBarraBuscarCliente.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraBuscarCliente.setBounds(86, 28, 120, 1);
		panelBuscar.add(lblBarraBuscarCliente);
		
		JLabel lblNomeCliente = new JLabel("Nome:");
		lblNomeCliente.setForeground(Color.WHITE);
		lblNomeCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblNomeCliente.setBounds(10, 49, 56, 14);
		panelBuscar.add(lblNomeCliente);
		
		textFieldNomeCliente = new JTextField();
		textFieldNomeCliente.setEnabled(false);
		textFieldNomeCliente.setForeground(Color.WHITE);
		textFieldNomeCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldNomeCliente.setColumns(10);
		textFieldNomeCliente.setBorder(null);
		textFieldNomeCliente.setBackground(Color.DARK_GRAY);
		textFieldNomeCliente.setBounds(56, 46, 194, 20);
		panelBuscar.add(textFieldNomeCliente);
		
		JButton btnBuscarCliente = new JButton("");
		btnBuscarCliente.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgBuscar.png")));
		btnBuscarCliente.setBackground(Color.DARK_GRAY);
		btnBuscarCliente.setForeground(Color.DARK_GRAY);
		btnBuscarCliente.setContentAreaFilled(false);
		btnBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
				ClienteFisico clienteFisico = new ClienteFisico();

				ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
				ClienteJuridico clienteJuridico = new ClienteJuridico();
				
				if (textFieldBuscarCliente.getText().length() == 14) { //tamanho da string do cpf
					flagClienteJuridico = false;
					flagClienteFisico = true;
					clienteFisico.setCpf(textFieldBuscarCliente.getText());
					clienteFisicoDAO.ConsultarPeloCPF(clienteFisico, null);
					textFieldNomeCliente.setText(clienteFisico.getNome());
					textFieldEmailCliente.setText(clienteFisico.getEmail());
				}
				else {
					flagClienteJuridico = true;
					flagClienteFisico = false;
					clienteJuridico.setCnpj(textFieldBuscarCliente.getText());
					clienteJuridicoDAO.ConsultarPeloCNPJ(clienteJuridico, null);
					textFieldNomeCliente.setText(clienteJuridico.getNome());
					textFieldEmailCliente.setText(clienteJuridico.getEmail());
				}
				
			}
		});
		btnBuscarCliente.setBounds(219, 7, 31, 23);
		panelBuscar.add(btnBuscarCliente);
		
		JLabel lblBarraNomeCliente = new JLabel("");
		lblBarraNomeCliente.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraNomeCliente.setBounds(56, 69, 194, 1);
		panelBuscar.add(lblBarraNomeCliente);
		
		JLabel lblEmailCliente = new JLabel("Email:");
		lblEmailCliente.setForeground(Color.WHITE);
		lblEmailCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblEmailCliente.setBounds(10, 91, 56, 14);
		panelBuscar.add(lblEmailCliente);
		
		textFieldEmailCliente = new JTextField();
		textFieldEmailCliente.setEnabled(false);
		textFieldEmailCliente.setForeground(Color.WHITE);
		textFieldEmailCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldEmailCliente.setColumns(10);
		textFieldEmailCliente.setBorder(null);
		textFieldEmailCliente.setBackground(Color.DARK_GRAY);
		textFieldEmailCliente.setBounds(66, 88, 184, 20);
		panelBuscar.add(textFieldEmailCliente);
		
		JLabel lblBarraEmailCliente = new JLabel("");
		lblBarraEmailCliente.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraEmailCliente.setBounds(56, 108, 194, 1);
		panelBuscar.add(lblBarraEmailCliente);
		
		JButton btnAdicionarCliente = new JButton("");
		btnAdicionarCliente.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgAdicionar.png")));
		btnAdicionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCPFouCNPJ.setText(textFieldBuscarCliente.getText());
				lblNomeOuRazaoSocial.setText(textFieldNomeCliente.getText().toUpperCase());
			}
		});
		btnAdicionarCliente.setBounds(290, 44, 38, 44);
		btnAdicionarCliente.setBackground(Color.DARK_GRAY);
		btnAdicionarCliente.setForeground(Color.DARK_GRAY);
		btnAdicionarCliente.setContentAreaFilled(false);
		panelBuscar.add(btnAdicionarCliente);
		
		JLabel lblBarraDivisao = new JLabel("New label");
		lblBarraDivisao.setBounds(357, 0, 1, 137);
		panelBuscar.add(lblBarraDivisao);
		lblBarraDivisao.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgTelaCaixa.png")));
		
		JLabel lblBuscarLivro = new JLabel("ID livro:");
		lblBuscarLivro.setForeground(Color.WHITE);
		lblBuscarLivro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblBuscarLivro.setBounds(372, 11, 82, 14);
		panelBuscar.add(lblBuscarLivro);
		
		textFieldBuscarLivro = new JTextField();
		textFieldBuscarLivro.setForeground(Color.WHITE);
		textFieldBuscarLivro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldBuscarLivro.setColumns(10);
		textFieldBuscarLivro.setBorder(null);
		textFieldBuscarLivro.setBackground(Color.DARK_GRAY);
		textFieldBuscarLivro.setBounds(452, 8, 120, 20);
		panelBuscar.add(textFieldBuscarLivro);
		
		JLabel lblBarraBuscarLivro = new JLabel("");
		lblBarraBuscarLivro.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraBuscarLivro.setBounds(452, 30, 120, 1);
		panelBuscar.add(lblBarraBuscarLivro);
		
		JButton btnAdicionarLivro = new JButton("");
		btnAdicionarLivro.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgAdicionar.png")));
		btnAdicionarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				
				String conversor = null; // converter o valor do auxiliar do total em String para colocar no JTextField
		
				modeloTableLivrosDaVenda.addRow(new String [] {textFieldBuscarLivro.getText(), textFieldTitulo.getText(), textFieldQtdEstoque.getText(), textFieldPrecoLivro.getText(),  
						String.valueOf(Double.parseDouble(textFieldPrecoLivro.getText())*Integer.parseInt(textFieldQtdEstoque.getText()))});
				
				auxiliar = auxiliar + (Double.parseDouble(textFieldPrecoLivro.getText()) * Integer.parseInt(textFieldQtdEstoque.getText()));
				conversor = String.valueOf(auxiliar);
				textFieldTotalVenda.setText(conversor);				
				quantidadeDeProdutos = quantidadeDeProdutos + Integer.parseInt(textFieldQtdEstoque.getText()); 
			
			}
		});
		btnAdicionarLivro.setBounds(654, 45, 38, 44);
		btnAdicionarLivro.setBackground(Color.DARK_GRAY);
		btnAdicionarLivro.setForeground(Color.DARK_GRAY);
		btnAdicionarLivro.setContentAreaFilled(false);
		panelBuscar.add(btnAdicionarLivro);
		
		JButton btnBuscarLivro = new JButton("");
		btnBuscarLivro.setBackground(Color.DARK_GRAY);
		btnBuscarLivro.setForeground(Color.DARK_GRAY);
		btnBuscarLivro.setContentAreaFilled(false);
		btnBuscarLivro.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgBuscar.png")));
		btnBuscarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				LivroDAO livroDAO = new LivroDAO();
				Livro livro = new Livro();
				
				livro.setId(Integer.parseInt(textFieldBuscarLivro.getText()));
				System.out.println(livro.getId());
				livroDAO.Consultar(livro);
			
				textFieldTitulo.setText(livro.getTitulo());
				textFieldPrecoLivro.setText(String.valueOf(livro.getPreco()));
			}
		});
		btnBuscarLivro.setBounds(582, 7, 31, 23);
		panelBuscar.add(btnBuscarLivro);
		
		JLabel lblTituloLivro = new JLabel("T\u00EDtulo:");
		lblTituloLivro.setForeground(Color.WHITE);
		lblTituloLivro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblTituloLivro.setBounds(372, 49, 82, 14);
		panelBuscar.add(lblTituloLivro);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setEnabled(false);
		textFieldTitulo.setForeground(Color.WHITE);
		textFieldTitulo.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBorder(null);
		textFieldTitulo.setBackground(Color.DARK_GRAY);
		textFieldTitulo.setBounds(440, 46, 173, 20);
		panelBuscar.add(textFieldTitulo);
		
		JLabel lblBarraTitulo = new JLabel("");
		lblBarraTitulo.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraTitulo.setBounds(440, 69, 173, 1);
		panelBuscar.add(lblBarraTitulo);
		
		JLabel lblPrecoLivro = new JLabel("Pre\u00E7o: R$");
		lblPrecoLivro.setForeground(Color.WHITE);
		lblPrecoLivro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblPrecoLivro.setBounds(372, 91, 82, 14);
		panelBuscar.add(lblPrecoLivro);
		
		textFieldPrecoLivro = new JTextField();
		textFieldPrecoLivro.setEnabled(false);
		textFieldPrecoLivro.setForeground(Color.WHITE);
		textFieldPrecoLivro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldPrecoLivro.setColumns(10);
		textFieldPrecoLivro.setBorder(null);
		textFieldPrecoLivro.setBackground(Color.DARK_GRAY);
		textFieldPrecoLivro.setBounds(450, 88, 38, 20);
		panelBuscar.add(textFieldPrecoLivro);
		
		JLabel lblBarraPreco = new JLabel("");
		lblBarraPreco.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraPreco.setBounds(426, 108, 72, 1);
		panelBuscar.add(lblBarraPreco);
		
		JLabel lblQtdEstoque = new JLabel("Qtde.:");
		lblQtdEstoque.setForeground(Color.WHITE);
		lblQtdEstoque.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblQtdEstoque.setBounds(508, 91, 56, 14);
		panelBuscar.add(lblQtdEstoque);
		
		textFieldQtdEstoque = new JTextField("1");
		textFieldQtdEstoque.setForeground(Color.WHITE);
		textFieldQtdEstoque.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		textFieldQtdEstoque.setColumns(10);
		textFieldQtdEstoque.setBorder(null);
		textFieldQtdEstoque.setBackground(Color.DARK_GRAY);
		textFieldQtdEstoque.setBounds(564, 88, 49, 20);
		panelBuscar.add(textFieldQtdEstoque);
		
		JLabel lblBarraQtdEstoque = new JLabel("");
		lblBarraQtdEstoque.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraQtdEstoque.setBounds(564, 108, 49, 1);
		panelBuscar.add(lblBarraQtdEstoque);
		
		JPanel panelVenda = new JPanel();
		panelVenda.setBackground(Color.DARK_GRAY);
		panelVenda.setBounds(382, 229, 727, 491);
		contentPane.add(panelVenda);
		panelVenda.setLayout(null);
		
		JScrollPane scrollPaneTabelaLivrosDaVenda = new JScrollPane();
		scrollPaneTabelaLivrosDaVenda.setBounds(10, 51, 707, 367);
		panelVenda.add(scrollPaneTabelaLivrosDaVenda);
		
		tableLivrosDaVenda = new JTable();
		modeloTableLivrosDaVenda = (DefaultTableModel) tableLivrosDaVenda.getModel();
		modeloTableLivrosDaVenda.addColumn("ID");
		modeloTableLivrosDaVenda.addColumn("Título");
		modeloTableLivrosDaVenda.addColumn("Quantidade");
		modeloTableLivrosDaVenda.addColumn("Preço");
		modeloTableLivrosDaVenda.addColumn("Subtotal");				
		scrollPaneTabelaLivrosDaVenda.setViewportView(tableLivrosDaVenda);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de pagamento:");
		lblFormaDePagamento.setForeground(Color.WHITE);
		lblFormaDePagamento.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblFormaDePagamento.setBounds(10, 446, 161, 14);
		panelVenda.add(lblFormaDePagamento);
		
		comboBoxFormaDePagamento = new JComboBox();
		comboBoxFormaDePagamento.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		comboBoxFormaDePagamento.setModel(new DefaultComboBoxModel(new String[] {"CR\u00C9DITO", "D\u00C9BITO"}));
		comboBoxFormaDePagamento.setBounds(172, 443, 93, 20);
		panelVenda.add(comboBoxFormaDePagamento);
		
		JLabel lblTotal = new JLabel("TOTAL: R$");
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("Lucida Console", Font.PLAIN, 44));
		lblTotal.setBounds(311, 429, 270, 51);
		panelVenda.add(lblTotal);
		
		lblCPFouCNPJ = new JLabel("");
		lblCPFouCNPJ.setForeground(Color.WHITE);
		lblCPFouCNPJ.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblCPFouCNPJ.setBounds(10, 11, 184, 14);
		panelVenda.add(lblCPFouCNPJ);
		
		lblNomeOuRazaoSocial = new JLabel("");
		lblNomeOuRazaoSocial.setForeground(Color.WHITE);
		lblNomeOuRazaoSocial.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblNomeOuRazaoSocial.setBounds(221, 11, 496, 14);
		panelVenda.add(lblNomeOuRazaoSocial);
		
		textFieldTotalVenda = new JTextField();
		textFieldTotalVenda.setEnabled(false);
		textFieldTotalVenda.setBounds(561, 429, 156, 51);
		textFieldTotalVenda.setForeground(Color.WHITE);
		textFieldTotalVenda.setFont(new Font("Lucida Console", Font.PLAIN, 30));
		textFieldTotalVenda.setBorder(null);
		textFieldTotalVenda.setBackground(Color.DARK_GRAY);
		panelVenda.add(textFieldTotalVenda);
		textFieldTotalVenda.setColumns(10);
		
		JButton btnConcluirVenda = new JButton("CONCLUIR VENDA");
		btnConcluirVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int contador = 0;
				Pedido pedido = new Pedido (); 
				PedidoDAO pedidoDAO = new PedidoDAO();
				ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
				ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
				LivroDAO livroDAO = new LivroDAO();
				//FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				
				if (flagClienteFisico == true) {
					pedido.setIdClienteFisico(clienteFisicoDAO.RetornarIdCliente(textFieldBuscarCliente.getText()));
					System.out.println(pedido.getIdClienteFisico());
					pedido.setIdClienteJuridico(1);
				}
				else if (flagClienteJuridico == true) {
					pedido.setIdClienteJuridico(clienteJuridicoDAO.RetornarIdCliente(textFieldBuscarCliente.getText()));
					pedido.setIdClienteFisico(1);
				}
				
				pedido.setTotal(Double.parseDouble(textFieldTotalVenda.getText()));
				pedido.setFormaPagamento(comboBoxFormaDePagamento.getSelectedItem().toString());
				pedido.setIdFuncionario(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite seu código de funcionário")));
				pedidoDAO.Cadastrar(pedido);
				
				System.out.println("\nQuantidade de produtos:  " + modeloTableLivrosDaVenda.getRowCount()); 
				
				for (contador = 0; contador < modeloTableLivrosDaVenda.getRowCount(); contador++) {
					ItensPedidos itensPedidos = new ItensPedidos();	
					itensPedidos.setIdLivro(Integer.parseInt(modeloTableLivrosDaVenda.getValueAt(contador, 0).toString()));
					System.out.println("\nCodigo pedido:" + pedidoDAO.RetornarIdPedido() + "\nENTROU " + contador + "\n\nCof livro: " + itensPedidos.getIdLivro());
					itensPedidos.setIdPedido(pedidoDAO.RetornarIdPedido());
					itensPedidos.setQuantidade(Integer.parseInt(modeloTableLivrosDaVenda.getValueAt(contador, 2).toString()));
					pedidoDAO.CadastrarItem(itensPedidos);
					livroDAO.AtualizarEstoque(itensPedidos.getIdLivro(), itensPedidos.getQuantidade());
				}				
				
			}
		});
		btnConcluirVenda.setIcon(new ImageIcon(TelaCaixa.class.getResource("/Imagens/imgCheck.png")));
		btnConcluirVenda.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		btnConcluirVenda.setBounds(937, 726, 172, 23);
		contentPane.add(btnConcluirVenda);
		
		
		
	}
}
