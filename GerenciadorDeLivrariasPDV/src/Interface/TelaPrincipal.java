package Interface;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPasswordField;



import DAO.*;
import Model.*;
import Utilitarios.*;


public class TelaPrincipal extends JFrame {

	// painéis referentes ao CRUD pessoa
	private JPanel contentPane;
	private JPanel panelEscolherTipoCliente;
	private JPanel panelFornecedores;
	private JPanel panelFuncionarios;
	private JPanel panelExclusaoAlteracaoPessoa;
	private JPanel panelClientes;
	
	// tabelas referentes ao CRUD pessoa
	private JTable tableFornecedores;
	private JTable tableClientesFisicos;
	private JTable tableClientesJuridicos;
	private JTable tableFuncionarios;	
	
	// componentes do formulário referente ao CRUD pessoa
	private JTextField textFieldNome;
	private JTextField textFieldCPF;
	private JTextField textFieldDataNascimento;
	private JTextField textFieldEmail;
	private JTextField textFieldTelefone;
	private JTextField textFieldCep;
	private JTextField textFieldEstado;
	private JTextField textFieldCidade;
	private JTextField textFieldBairro;
	private JTextField textFieldRua;
	private JTextField textFieldNumero;
	private JTextField textFieldExclusaoAlteracaoBuscarPessoa;	
	
	private JPasswordField passwordField;
	
	private JLabel lblBarraSenha;
	private JLabel lblSenha; 
	private JLabel lblCargo;
	private JLabel lblCPF;
	private JLabel lblBarraEmail;
	private JLabel lblDataNascimento;
	
	private JComboBox comboBoxCargo;	
	private JComboBox comboBoxEscolherTipoCliente;
	

	
	// flags de intertravamento
	/*Cadastro*/
	private boolean flagCadastroClienteJuridico;
	private boolean flagCadastroClienteFisico;
	private boolean flagCadastroFornecedor;
	private boolean flagCadastroFuncionario;
	/*Atualização*/
	private boolean flagAtualizarClienteJuridico;
	private boolean flagAtualizarClienteFisico;
	private boolean flagAtualizarFornecedor;
	private boolean flagAtualizarFuncionario;
	/*Exclusão*/
	private boolean flagExcluirClienteJuridico;
	private boolean flagExcluirClienteFisico;
	private boolean flagExcluirFornecedor;
	private boolean flagExcluirFuncionario;
		
	/*armazena endereço*/
	private int armazenaEndereco;  
	
	/*resposta JOptionPane*/
	Object[] options = { "Confirmar", "Cancelar" };
	private int resposta;

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
					TelaPrincipal frame = new TelaPrincipal();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TelaPrincipal() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				lerTabelaFuncionarios();
				lerTabelaClienteFisico();
				lerTabelaClienteJuridico();
				lerTabelaFornecedores();

			}
		});				
		
		setTitle("Gerenciador de Livrarias");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1007, 672);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*--------------------------------------------------Imagem lateral da tela principal------------------------------------------------------------------*/
		JPanel panelImagem = new JPanel();
		panelImagem.setBackground(new Color(102, 205, 170));
		panelImagem.setBounds(0, 0, 162, 643);
		contentPane.add(panelImagem);
		panelImagem.setLayout(null);
		
		JLabel lblImagemLateral = new JLabel("");
		lblImagemLateral.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgTelas.jpg")));
		lblImagemLateral.setBounds(0, 0, 187, 643);
		panelImagem.add(lblImagemLateral);
		
		/*--------------------------------------------------Tabbed Pane que contém as guias de navegação-------------------------------------------------------*/		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(new Color(51, 51, 51));
		tabbedPane.setBounds(161, 0, 840, 643);
		contentPane.add(tabbedPane);
		
		/*--------------------------------------------------Painel com botões de navegação--------------------------------------------------------------------*/
		JPanel panelInicio = new JPanel();
		tabbedPane.addTab("Início", null, panelInicio, null);
		panelInicio.setBackground(new Color(51, 51, 51));
		panelInicio.setLayout(null);
		
		JButton btnFuncionarios = new JButton("Funcion\u00E1rios");
		btnFuncionarios.setBounds(54, 144,  200, 200);
		btnFuncionarios.setVerticalAlignment(SwingConstants.BOTTOM);
		btnFuncionarios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgFuncionario.png")));
		btnFuncionarios.setForeground(new Color(255, 255, 255));
		btnFuncionarios.setBackground(new Color(51, 51, 51));
		btnFuncionarios.setContentAreaFilled(false);
		btnFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("Funcionários", null, panelFuncionarios, null);				
				tabbedPane.remove(0);
			}
		});
		btnFuncionarios.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnFuncionarios.setHorizontalTextPosition(SwingConstants.CENTER);		
		btnFuncionarios.setOpaque(true);
		btnFuncionarios.setBorder(null);
		panelInicio.add(btnFuncionarios);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("Clientes", null, panelClientes, null);
				tabbedPane.remove(0);
			}
		});
		btnClientes.setBounds(307, 144,  200, 200);
		btnClientes.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgCliente.png")));
		btnClientes.setForeground(new Color(255, 255, 255));
		btnClientes.setBackground(new Color(51, 51, 51));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnClientes.setHorizontalTextPosition(SwingConstants.CENTER);		
		btnClientes.setOpaque(true);
		btnClientes.setBorder(null);
		panelInicio.add(btnClientes);
		
		JButton btnFornecedores = new JButton("Fornecedores");
		btnFornecedores.setBounds(557, 144,  200, 200);
		btnFornecedores.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgFornecedor.png")));
		btnFornecedores.setBackground(new Color(51, 51, 51));
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.addTab("Fornecedores", null, panelFornecedores, null);
				tabbedPane.remove(0);
			}
		});
		btnFornecedores.setForeground(new Color(255, 255, 255));
		btnFornecedores.setContentAreaFilled(false);
		btnFornecedores.setOpaque(true);
		btnFornecedores.setBorder(null);
		btnFornecedores.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnFornecedores.setHorizontalTextPosition(SwingConstants.CENTER);
		panelInicio.add(btnFornecedores);
		
		JButton btnLivros = new JButton("Livros");
		btnLivros.setBounds(54, 355, 200, 200);
		btnLivros.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLivro.png")));
		btnLivros.setForeground(new Color(255, 255, 255));
		btnLivros.setBackground(new Color(51, 51, 51));
		btnLivros.setContentAreaFilled(false);
		btnLivros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TelaLivro telaLivro = new TelaLivro();
				telaLivro.setVisible(true);
				telaLivro.setUndecorated(true);
				telaLivro.setLocationRelativeTo(null);										
				dispose();					
			}
		});
		btnLivros.setOpaque(true);
		btnLivros.setBorder(null);
		btnLivros.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLivros.setHorizontalTextPosition(SwingConstants.CENTER);
		panelInicio.add(btnLivros);
		
		JButton btnPedidos = new JButton("Pedidos");
		btnPedidos.setBounds(307, 355, 200, 200);
		btnPedidos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgPedido.png")));
		btnPedidos.setForeground(new Color(255, 255, 255));
		btnPedidos.setBackground(new Color(51, 51, 51));
		btnPedidos.setContentAreaFilled(false);
		btnPedidos.setOpaque(true);
		btnPedidos.setBorder(null);
		btnPedidos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPedidos.setHorizontalTextPosition(SwingConstants.CENTER);
		panelInicio.add(btnPedidos);
		
		JButton btnRelatorios = new JButton("Relat\u00F3rios");
		btnRelatorios.setBounds(557, 355, 200, 200);
		btnRelatorios.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgRelatorio.png")));
		btnRelatorios.setForeground(new Color(255, 255, 255));
		btnRelatorios.setBackground(new Color(51, 51, 51));
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setOpaque(true);
		btnRelatorios.setBorder(null);
		btnRelatorios.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRelatorios.setHorizontalTextPosition(SwingConstants.CENTER);
		panelInicio.add(btnRelatorios);
		
		JLabel lblBemVindo = new JLabel("Bem-vindo");
		lblBemVindo.setBounds(38, 39, 291, 50);
		lblBemVindo.setFont(new Font("Candara Light", Font.PLAIN, 40));
		lblBemVindo.setForeground(new Color(255, 255, 255));
		panelInicio.add(lblBemVindo);
		
		/*--------------------------------------------------Painel dos clientes físicos e jurídicos------------------------------------------------------------------*/
		panelClientes = new JPanel();
		panelClientes.setBackground(new Color(51, 51, 51));
		//tabbedPane.addTab("Clientes", null, panelClientes, null);
		panelClientes.setLayout(null);
		
		JTabbedPane tabbedPaneClientes = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneClientes.setBounds(10, 39, 815, 296);
		panelClientes.add(tabbedPaneClientes);
		
		JScrollPane scrollPaneTabelaClientesFisicos = new JScrollPane();
		scrollPaneTabelaClientesFisicos.setViewportBorder(null);
		tabbedPaneClientes.addTab("Clientes Físicos", null, scrollPaneTabelaClientesFisicos, null);
		tabbedPaneClientes.setForegroundAt(0, new Color(0, 0, 0));
		tabbedPaneClientes.setBackgroundAt(0, new Color(51, 51, 51));
		tableClientesFisicos = new JTable();
		tableClientesFisicos.setForeground(new Color(255, 255, 255));
		tableClientesFisicos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "CPF","Celular", "Email"
			}
		));
		tableClientesFisicos.setBackground(new Color(51, 51, 51));
		scrollPaneTabelaClientesFisicos.getViewport().setBackground(Color.DARK_GRAY);
		scrollPaneTabelaClientesFisicos.setBackground(Color.DARK_GRAY);
		scrollPaneTabelaClientesFisicos.setViewportView(tableClientesFisicos);
		
		JScrollPane scrollPaneTabelaClientesJuridicos = new JScrollPane();
		scrollPaneTabelaClientesJuridicos.setViewportBorder(null);
		tabbedPaneClientes.addTab("Clientes Jurídicos", null, scrollPaneTabelaClientesJuridicos, null);
		tabbedPaneClientes.setForegroundAt(1, new Color(0, 0, 0));
		tabbedPaneClientes.setBackgroundAt(1, new Color(51, 51, 51));
		scrollPaneTabelaClientesJuridicos.getViewport().setBackground(Color.DARK_GRAY);
		
		tableClientesJuridicos = new JTable();
		tableClientesJuridicos.setForeground(Color.WHITE);
		tableClientesJuridicos.setBackground(Color.DARK_GRAY);
		scrollPaneTabelaClientesJuridicos.setViewportView(tableClientesJuridicos);
		tableClientesJuridicos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"ID", "Nome", "CNPJ","Telefone", "Email"
			}
		));
		
		JPanel panelCadastroEEdicaoPessoa = new JPanel();
		panelCadastroEEdicaoPessoa.setBounds(10, 374, 815, 230);
		panelCadastroEEdicaoPessoa.setLayout(null);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panelClientes.add(panelEscolherTipoCliente);
				panelEscolherTipoCliente.setVisible(true);
				flagCadastroClienteJuridico = true;
				flagCadastroClienteFisico = true;
			}
		});
		btnCadastrar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnCadastrar.setBounds(414, 11, 131, 22);
		btnCadastrar.setBorder(null);
		btnCadastrar.setForeground(new Color(255, 255, 255));
		btnCadastrar.setBackground(new Color(51, 51, 51));
		btnCadastrar.setContentAreaFilled(false);
		panelClientes.add(btnCadastrar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				panelClientes.add(panelExclusaoAlteracaoPessoa);
				panelExclusaoAlteracaoPessoa.setVisible(true);				
				flagExcluirClienteJuridico = true;
				flagExcluirClienteFisico = true;
			
			}
		});
		btnExcluir.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnExcluir.setBounds(694, 11, 131, 22);
		btnExcluir.setForeground(new Color(255, 255, 255));
		btnExcluir.setBackground(new Color(51, 51, 51));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorder(null);
		panelClientes.add(btnExcluir);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnAtualizar.setBounds(553, 11, 131, 22);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panelClientes.add(panelExclusaoAlteracaoPessoa);
				panelExclusaoAlteracaoPessoa.setVisible(true);
				
				panelClientes.add(panelEscolherTipoCliente);
				flagAtualizarClienteJuridico = true;
				flagAtualizarClienteFisico = true;
			}
		});
		btnAtualizar.setForeground(new Color(255, 255, 255));
		btnAtualizar.setBackground(new Color(51, 51, 51));
		btnAtualizar.setContentAreaFilled(false);
		btnAtualizar.setBorder(null);
		panelClientes.add(btnAtualizar);
		
		JLabel lblBarraCadastrar = new JLabel("");
		lblBarraCadastrar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraCadastrar.setBounds(445, 32, 70, 1);
		panelClientes.add(lblBarraCadastrar);
		
		JLabel lblBarraAtualizar = new JLabel("");
		lblBarraAtualizar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraAtualizar.setBackground(new Color(255, 204, 102));
		lblBarraAtualizar.setBounds(581, 32, 70, 1);
		panelClientes.add(lblBarraAtualizar);
		
		JLabel lblBarraExcluir = new JLabel("");
		lblBarraExcluir.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraExcluir.setBounds(723, 32, 70, 1);
		panelClientes.add(lblBarraExcluir);
		
		/*-------------------------------------------------- Painel que será usado como form de cadastro e atuazalição para todos os objetos que extendem a classe pessoa,
		sendo eles: Cliente Físico, Cliente Jurídico, Funcionário e Fornecedor. Os campos do formulário seram adptados de acordo com o intertravamento das flags. Quando a flag
		cadastro ou atualização de cliente físico estiver setada, os campos relacionados ao cliente serão acionados e assim acontecerá em todas as outras classes.------------*/
		JPanel panelCLienteCadastroAtualizacao = new JPanel();
		panelCLienteCadastroAtualizacao.setBorder(null);
		panelCLienteCadastroAtualizacao.setBackground(Color.DARK_GRAY);
		panelCLienteCadastroAtualizacao.setBounds(10, 403, 815, 201);
		panelCLienteCadastroAtualizacao.setVisible(false);
		panelClientes.add(panelCLienteCadastroAtualizacao);
		panelCLienteCadastroAtualizacao.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblNome.setForeground(Color.WHITE);
		lblNome.setBounds(10, 24, 46, 14);
		panelCLienteCadastroAtualizacao.add(lblNome);
		
		JLabel lblNomeBarra = new JLabel("");
		lblNomeBarra.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblNomeBarra.setBounds(56, 37, 400, 1);
		panelCLienteCadastroAtualizacao.add(lblNomeBarra);
		
		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldNome.setForeground(Color.WHITE);
		textFieldNome.setBackground(Color.DARK_GRAY);
		textFieldNome.setBorder(null);
		textFieldNome.setBounds(56, 17, 401, 20);
		panelCLienteCadastroAtualizacao.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		lblCPF = new JLabel("CPF:");
		lblCPF.setForeground(Color.WHITE);
		lblCPF.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblCPF.setBounds(457, 19, 60, 14);
		panelCLienteCadastroAtualizacao.add(lblCPF);
		
		JLabel lblCpfBarra = new JLabel("");
		lblCpfBarra.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblCpfBarra.setBounds(505, 37, 130, 1);
		panelCLienteCadastroAtualizacao.add(lblCpfBarra);
		
		textFieldCPF = new JTextField();
		textFieldCPF.setForeground(Color.WHITE);
		textFieldCPF.setBorder(null);
		textFieldCPF.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldCPF.setColumns(10);
		textFieldCPF.setBackground(Color.DARK_GRAY);
		textFieldCPF.setBounds(505, 16, 130, 20);
		panelCLienteCadastroAtualizacao.add(textFieldCPF);
		
		lblDataNascimento = new JLabel("Data de nascimento:");
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblDataNascimento.setBounds(10, 59, 152, 14);
		panelCLienteCadastroAtualizacao.add(lblDataNascimento);
		
		textFieldDataNascimento = new JTextField();
		textFieldDataNascimento.setForeground(Color.WHITE);
		textFieldDataNascimento.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldDataNascimento.setColumns(10);
		textFieldDataNascimento.setBackground(Color.DARK_GRAY);
		textFieldDataNascimento.setBorder(null);
		textFieldDataNascimento.setBounds(172, 52, 87, 20);
		panelCLienteCadastroAtualizacao.add(textFieldDataNascimento);
		
		JLabel lblBarraDataNascimento = new JLabel("");
		lblBarraDataNascimento.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));	
		lblBarraDataNascimento.setBounds(172, 72, 87, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraDataNascimento);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblEmail.setBounds(269, 59, 58, 14);
		panelCLienteCadastroAtualizacao.add(lblEmail);		
		
		lblBarraEmail = new JLabel("");
		lblBarraEmail.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraEmail.setBounds(325, 72, 162, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setForeground(Color.WHITE);
		textFieldEmail.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBorder(null);
		textFieldEmail.setBackground(Color.DARK_GRAY);
		textFieldEmail.setBounds(325, 51, 162, 20);
		panelCLienteCadastroAtualizacao.add(textFieldEmail);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setForeground(Color.WHITE);
		lblTelefone.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblTelefone.setBounds(643, 19, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblTelefone);
		
		JLabel lblBarraTelefone = new JLabel("");
		lblBarraTelefone.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraTelefone.setBounds(718, 37, 87, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraTelefone);
		
		textFieldTelefone = new JTextField();
		textFieldTelefone.setForeground(Color.WHITE);
		textFieldTelefone.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldTelefone.setColumns(10);
		textFieldTelefone.setBorder(null);
		textFieldTelefone.setBackground(Color.DARK_GRAY);
		textFieldTelefone.setBounds(718, 16, 87, 20);
		panelCLienteCadastroAtualizacao.add(textFieldTelefone);
		
		lblCargo = new JLabel("Cargo:");
		lblCargo.setForeground(Color.WHITE);
		lblCargo.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblCargo.setBounds(505, 59, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblCargo);
		
		comboBoxCargo = new JComboBox();
		comboBoxCargo.setForeground(Color.BLACK);
		comboBoxCargo.setBackground(Color.DARK_GRAY);
		comboBoxCargo.setBorder(null);
		comboBoxCargo.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		comboBoxCargo.setModel(new DefaultComboBoxModel(new String[] {"ADMINISTRADOR", "FUNCION\u00C1RIO"}));
		comboBoxCargo.setBounds(559, 56, 121, 20);
		panelCLienteCadastroAtualizacao.add(comboBoxCargo);
		
		lblSenha = new JLabel("Senha:");
		lblSenha.setForeground(Color.WHITE);
		lblSenha.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblSenha.setBounds(690, 59, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblSenha);
		
		lblBarraSenha = new JLabel("");
		lblBarraSenha.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraSenha.setBounds(736, 72, 69, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraSenha);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(736, 51, 66, 20);
		passwordField.setBorder(null);
		passwordField.setBackground(Color.DARK_GRAY);
		passwordField.setForeground(Color.WHITE);
		panelCLienteCadastroAtualizacao.add(passwordField);
		
		JLabel lblCEP = new JLabel("CEP:");
		lblCEP.setForeground(Color.WHITE);
		lblCEP.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblCEP.setBounds(10, 95, 34, 14);
		panelCLienteCadastroAtualizacao.add(lblCEP);
		
		textFieldCep = new JTextField();
		textFieldCep.setForeground(Color.WHITE);
		textFieldCep.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldCep.setColumns(10);
		textFieldCep.setBorder(null);
		textFieldCep.setBackground(Color.DARK_GRAY);
		textFieldCep.setBounds(43, 87, 70, 20);
		panelCLienteCadastroAtualizacao.add(textFieldCep);
		
		JLabel lblBarraCep = new JLabel("");
		lblBarraCep.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraCep.setBounds(43, 108, 70, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraCep);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setForeground(Color.WHITE);
		lblEstado.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblEstado.setBounds(140, 95, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblEstado);
		
		textFieldEstado = new JTextField();
		textFieldEstado.setForeground(Color.WHITE);
		textFieldEstado.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldEstado.setColumns(10);
		textFieldEstado.setBorder(null);
		textFieldEstado.setBackground(Color.DARK_GRAY);
		textFieldEstado.setBounds(196, 87, 162, 20);
		panelCLienteCadastroAtualizacao.add(textFieldEstado);
		
		JLabel lblBarraEstado = new JLabel("");
		lblBarraEstado.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraEstado.setBounds(196, 108, 162, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraEstado);
		
		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setForeground(Color.WHITE);
		lblCidade.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblCidade.setBounds(366, 95, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblCidade);
		
		JLabel lblBarraCidade = new JLabel("");
		lblBarraCidade.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraCidade.setBounds(427, 108, 130, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraCidade);
		
		textFieldCidade = new JTextField();
		textFieldCidade.setForeground(Color.WHITE);
		textFieldCidade.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldCidade.setColumns(10);
		textFieldCidade.setBorder(null);
		textFieldCidade.setBackground(Color.DARK_GRAY);
		textFieldCidade.setBounds(427, 87, 130, 20);
		panelCLienteCadastroAtualizacao.add(textFieldCidade);
		
		JButton btnBuscarCep = new JButton("");
		btnBuscarCep.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBuscar.png")));
		btnBuscarCep.setBounds(117, 84, 19, 23);
		btnBuscarCep.setContentAreaFilled(false);
		btnBuscarCep.setBorder(null);
		btnBuscarCep.setBackground(new Color(51, 51, 51));
		panelCLienteCadastroAtualizacao.add(btnBuscarCep);
		
		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setForeground(Color.WHITE);
		lblBairro.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblBairro.setBounds(569, 95, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblBairro);
		
		JLabel lblBarraBairro = new JLabel("");
		lblBarraBairro.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraBairro.setBounds(632, 108, 173, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraBairro);
		
		textFieldBairro = new JTextField();
		textFieldBairro.setForeground(Color.WHITE);
		textFieldBairro.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldBairro.setColumns(10);
		textFieldBairro.setBorder(null);
		textFieldBairro.setBackground(Color.DARK_GRAY);
		textFieldBairro.setBounds(632, 87, 173, 20);
		panelCLienteCadastroAtualizacao.add(textFieldBairro);
		
		JLabel lblRua = new JLabel("Rua:");
		lblRua.setForeground(Color.WHITE);
		lblRua.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblRua.setBounds(10, 132, 34, 14);
		panelCLienteCadastroAtualizacao.add(lblRua);
		
		JLabel lblBarraRua = new JLabel("");
		lblBarraRua.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraRua.setBounds(43, 145, 400, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraRua);
		
		textFieldRua = new JTextField();
		textFieldRua.setForeground(Color.WHITE);
		textFieldRua.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldRua.setColumns(10);
		textFieldRua.setBorder(null);
		textFieldRua.setBackground(Color.DARK_GRAY);
		textFieldRua.setBounds(43, 124, 401, 20);
		panelCLienteCadastroAtualizacao.add(textFieldRua);
		
		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setForeground(Color.WHITE);
		lblNumero.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblNumero.setBounds(453, 132, 72, 14);
		panelCLienteCadastroAtualizacao.add(lblNumero);
		
		JLabel lblBarraNumero = new JLabel("");
		lblBarraNumero.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraNumero.setBounds(518, 145, 87, 1);
		panelCLienteCadastroAtualizacao.add(lblBarraNumero);
		
		textFieldNumero = new JTextField();
		textFieldNumero.setForeground(Color.WHITE);
		textFieldNumero.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldNumero.setColumns(10);
		textFieldNumero.setBorder(null);
		textFieldNumero.setBackground(Color.DARK_GRAY);
		textFieldNumero.setBounds(518, 124, 58, 20);
		panelCLienteCadastroAtualizacao.add(textFieldNumero);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/*------------------------------------------------- Cadastro --------------------------*/
				//
	            if (flagCadastroClienteJuridico == true) {
	            	try {
						ClienteJuridico clienteJuridico = new ClienteJuridico();
						ClienteJuridicoDAO clienteDAO = new ClienteJuridicoDAO();
						Endereco endereco = new Endereco();					
						
						cadastrarPessoa (null, clienteJuridico, null, null, endereco);
					
						clienteDAO.Cadastrar(clienteJuridico, endereco);
						
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
					
	            	} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            	
	            }
	            
	            //
	            else if (flagCadastroClienteFisico == true) {
	            	try {
						ClienteFisico clienteFisico = new ClienteFisico();
						ClienteFisicoDAO clienteDAO = new ClienteFisicoDAO();
						Endereco endereco = new Endereco();					
						
						cadastrarPessoa (null, null, clienteFisico, null, endereco);
						
						clienteDAO.Cadastrar(clienteFisico, endereco);					
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            
	            }	            
	            
	            //
	            else if (flagCadastroFornecedor == true) {
	            	try {
						Fornecedor fornecedor = new Fornecedor();
						FornecedorDAO fornecedorDAO = new FornecedorDAO();
						Endereco endereco = new Endereco();					
						
						cadastrarPessoa (fornecedor, null, null, null, endereco);
						
						fornecedorDAO.Cadastrar(fornecedor, endereco);
						
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            
	            }	            
								
	            //
	            else if (flagCadastroFuncionario == true) {
	            	try {
						Funcionario funcionario = new Funcionario();
						FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
						Endereco endereco = new Endereco();					
						
						cadastrarPessoa (null, null, null, funcionario, endereco);
						
						funcionarioDAO.Cadastrar(funcionario, endereco);
						
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            
	            }
	            
	            /*------------------------------------------------- Atualização --------------------------*/

				//
	            else if (flagAtualizarClienteJuridico == true) {
	            	try {
						ClienteJuridico clienteJuridico = new ClienteJuridico();
						ClienteJuridicoDAO clienteDAO = new ClienteJuridicoDAO();
						Endereco endereco = new Endereco();					
						
						clienteJuridico.setIdEndereco(armazenaEndereco);
						//consultarPessoa (null, clienteJuridico, null, null, endereco);
						clienteJuridico.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						cadastrarPessoa (null, clienteJuridico, null, null, endereco);
						clienteDAO.Atualizar(clienteJuridico, endereco);
						
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelExclusaoAlteracaoPessoa.setVisible(false);
	            	} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            	
	            }
	            
	            //
	            else if (flagAtualizarClienteFisico == true) {
	            	try {
						ClienteFisico clienteFisico = new ClienteFisico();
						ClienteFisicoDAO clienteDAO = new ClienteFisicoDAO();
						Endereco endereco = new Endereco();					
						
						clienteFisico.setIdEndereco(armazenaEndereco);
						clienteFisico.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						//consultarPessoa (null, null, clienteFisico, null, endereco);
						cadastrarPessoa (null, null, clienteFisico, null, endereco);
						
						clienteDAO.Atualizar(clienteFisico, endereco);					
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelExclusaoAlteracaoPessoa.setVisible(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            
	            }	            
	            
	            //
	            else if (flagAtualizarFornecedor == true) {
	            	try {
						Fornecedor fornecedor = new Fornecedor();
						FornecedorDAO fornecedorDAO = new FornecedorDAO();
						Endereco endereco = new Endereco();					
						
						fornecedor.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						fornecedor.setIdEndereco(armazenaEndereco);
						//consultarPessoa (fornecedor, null, null, null, endereco);
						cadastrarPessoa (fornecedor, null, null, null, endereco);
						
						fornecedorDAO.Atualizar(fornecedor, endereco);
						
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelExclusaoAlteracaoPessoa.setVisible(false);
	            	} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            
	            }	            
								
	            //
	            else /*if (flagAtualizarFuncionario == true)*/ {
	            	try {
						Funcionario funcionario = new Funcionario();
						FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
						Endereco endereco = new Endereco();					
						
						//consultarPessoa (null, null, null, funcionario, endereco);
						funcionario.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						funcionario.setIdEndereco(armazenaEndereco);
						cadastrarPessoa (null, null, null, funcionario, endereco);
						funcionarioDAO.Atualizar(funcionario, endereco);
						
						limparFlagsCamposPessoa();		
						panelEscolherTipoCliente.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelExclusaoAlteracaoPessoa.setVisible(false);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					            
	            }
	            
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnSalvar.setContentAreaFilled(false);
		btnSalvar.setBorder(new LineBorder(new Color(0, 128, 128)));
		btnSalvar.setBackground(new Color(51, 51, 51));
		btnSalvar.setBounds(518, 168, 131, 22);
		panelCLienteCadastroAtualizacao.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setBorder(new LineBorder(new Color(139, 0, 0)));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFlagsCamposPessoa();		
				panelEscolherTipoCliente.setVisible(false);
				panelCLienteCadastroAtualizacao.setVisible(false);
			}
		});
		btnCancelar.setBackground(new Color(51, 51, 51));
		btnCancelar.setBounds(674, 168, 131, 22);
		panelCLienteCadastroAtualizacao.add(btnCancelar);	
		
		/********************Painel que será visível para cadastro e atualização de Clientes e Fornecedores para que ocorra a distinção entre o físico e o jurídico.*********/
		panelEscolherTipoCliente = new JPanel();
		panelEscolherTipoCliente.setBackground(Color.DARK_GRAY);
		panelEscolherTipoCliente.setBounds(229, 346, 365, 45);
		panelClientes.add(panelEscolherTipoCliente);
		panelEscolherTipoCliente.setVisible(false);
		panelEscolherTipoCliente.setLayout(null);
		
		comboBoxEscolherTipoCliente = new JComboBox();
		comboBoxEscolherTipoCliente.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		comboBoxEscolherTipoCliente.setModel(new DefaultComboBoxModel(new String[] {"F\u00CDSICO", "JUR\u00CDDICO"}));
		comboBoxEscolherTipoCliente.setBounds(146, 11, 167, 20);
		panelEscolherTipoCliente.add(comboBoxEscolherTipoCliente);
		
		JLabel lblEscolherCliente = new JLabel("Tipo do cliente:");
		lblEscolherCliente.setForeground(Color.WHITE);
		lblEscolherCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblEscolherCliente.setBounds(10, 14, 167, 14);
		panelEscolherTipoCliente.add(lblEscolherCliente);
		
		JButton btnEscolhido = new JButton("");
		btnEscolhido.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgCheck.png")));
		btnEscolhido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Endereco endereco = new Endereco();
				
				// FLAGS CADASTRO
				if (flagCadastroClienteFisico == true && flagCadastroClienteJuridico == true) {
					//clientes
					if(comboBoxEscolherTipoCliente.getSelectedItem().toString().equals("FÍSICO")) {

						flagCadastroClienteJuridico = false;
						flagCadastroClienteFisico = true;					
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblCPF.setText("CPF: ");
						lblBarraEmail.setBounds(325, 72, 300, 1);	
						lblDataNascimento.setText("Data de nascimento: ");
						lblDataNascimento.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
						
						panelClientes.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
						
					}
					else {
						
						flagCadastroClienteJuridico = true;
						flagCadastroClienteFisico = false;
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblCPF.setText("CNPJ: ");					
						lblBarraEmail.setBounds(325, 72, 300, 1);
						lblDataNascimento.setText("Data de criação: ");
						
						panelClientes.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
						
					}					
				}
				
				else if (flagCadastroFornecedor == true) {
					//fornecedores					
					if(comboBoxEscolherTipoCliente.getSelectedItem().toString().equals("FÍSICO")) {				
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblDataNascimento.setText("Data de nascimento: ");
						lblDataNascimento.setFont(new Font("Lucida Sans", Font.PLAIN, 13));		
						lblCPF.setText("CPF: ");
						lblBarraEmail.setBounds(325, 72, 300, 1);	
			
						panelFornecedores.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
					}
					else {						
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblDataNascimento.setText("Data de criação: ");
						lblCPF.setText("CNPJ: ");					
						lblBarraEmail.setBounds(325, 72, 162, 1);
						panelFornecedores.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
						
					}
					
				}
				
				
				//FLAGS ALTERAÇÃO
				else if (flagAtualizarClienteFisico == true && flagAtualizarClienteJuridico == true) {
					//clientes
					if(comboBoxEscolherTipoCliente.getSelectedItem().toString().equals("FÍSICO")) {

						ClienteFisico clienteFisico = new ClienteFisico();
						ClienteFisicoDAO clienteDAO = new ClienteFisicoDAO();
						clienteFisico.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						
						flagAtualizarClienteJuridico = false;
						flagAtualizarClienteFisico = true;					
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblCPF.setText("CPF: ");
						lblBarraEmail.setBounds(325, 72, 300, 1);	
						lblDataNascimento.setText("Data de nascimento: ");
						lblDataNascimento.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
						
						panelClientes.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
					
						clienteDAO.Consultar(clienteFisico, endereco);
						consultarPessoa (null, null, clienteFisico, null, endereco);
					}
					else {

						ClienteJuridicoDAO clienteDAO = new ClienteJuridicoDAO();						
						ClienteJuridico clienteJuridico = new ClienteJuridico();
						clienteJuridico.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						
						flagAtualizarClienteJuridico = true;
						flagAtualizarClienteFisico = false;
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblCPF.setText("CNPJ: ");					
						lblBarraEmail.setBounds(325, 72, 300, 1);
						lblDataNascimento.setText("Data de criação: ");
						
						panelClientes.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
						
						clienteDAO.Consultar(clienteJuridico, endereco);
						consultarPessoa (null, clienteJuridico, null, null, endereco);
					}					
				}
				
				else if (flagAtualizarFornecedor == true) {
					
					FornecedorDAO fornecedorDAO = new FornecedorDAO();
					Fornecedor fornecedor = new Fornecedor();
					fornecedor.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					//fornecedores
					if(comboBoxEscolherTipoCliente.getSelectedItem().toString().equals("FÍSICO")) {				
						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblDataNascimento.setText("Data de nascimento: ");
						lblDataNascimento.setFont(new Font("Lucida Sans", Font.PLAIN, 13));		
						lblCPF.setText("CPF: ");
						lblBarraEmail.setBounds(325, 72, 300, 1);	
			
						panelFornecedores.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
						
						fornecedorDAO.Consultar(fornecedor, endereco);
						consultarPessoa (fornecedor, null, null, null, endereco);
					}
					else if (flagAtualizarFuncionario == true){						
						passwordField.setVisible(false);
						lblBarraSenha.setVisible(false);
						lblSenha.setVisible(false);
						comboBoxCargo.setVisible(false);
						lblCargo.setVisible(false);
						lblDataNascimento.setText("Data de criação: ");
						lblCPF.setText("CNPJ: ");					
						lblBarraEmail.setBounds(325, 72, 162, 1);
						
						panelFornecedores.add(panelCLienteCadastroAtualizacao);
						panelCLienteCadastroAtualizacao.setVisible(true);
						
						fornecedorDAO.Consultar(fornecedor, endereco);
						consultarPessoa (fornecedor, null, null, null, endereco);
					}
				}
				
				// exclusao
				
				if (flagExcluirClienteJuridico == true && flagExcluirClienteFisico == true) {
					if(comboBoxEscolherTipoCliente.getSelectedItem().toString().equals("FÍSICO")) {

						ClienteFisicoDAO clienteDAO = new ClienteFisicoDAO();
						
						flagAtualizarClienteJuridico = false;
						flagAtualizarClienteFisico = true;					

						
						clienteDAO.Deletar(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
						
						lerTabelaClienteFisico();						
						limparFlagsCamposPessoa();	
						panelExclusaoAlteracaoPessoa.setVisible(false);
						panelEscolherTipoCliente.setVisible(false);
						
					}
					else {

						ClienteJuridicoDAO clienteDAO = new ClienteJuridicoDAO();	
						
						flagAtualizarClienteJuridico = true;
						flagAtualizarClienteFisico = false;
						
						clienteDAO.Deletar(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));

						lerTabelaClienteJuridico();
						limparFlagsCamposPessoa();	
						panelExclusaoAlteracaoPessoa.setVisible(false);
						panelEscolherTipoCliente.setVisible(false);						
					}					
					
				}
				
			}
		});
		btnEscolhido.setBounds(323, 9, 32, 23);
		btnEscolhido.setContentAreaFilled(false);
		btnEscolhido.setBorder(null);
		btnEscolhido.setBackground(new Color(51, 51, 51));
		panelEscolherTipoCliente.add(btnEscolhido);
		
		panelExclusaoAlteracaoPessoa = new JPanel();
		panelExclusaoAlteracaoPessoa.setBackground(Color.DARK_GRAY);
		panelExclusaoAlteracaoPessoa.setBounds(658, 346, 167, 45);
		panelClientes.add(panelExclusaoAlteracaoPessoa);
		panelExclusaoAlteracaoPessoa.setLayout(null);
		panelExclusaoAlteracaoPessoa.setVisible(false);
		
		JButton btnExclusaoAlteracaoBuscarPessoa = new JButton("");
		btnExclusaoAlteracaoBuscarPessoa.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgCheck.png")));
		btnExclusaoAlteracaoBuscarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				//Alteração
				
				Endereco endereco = new Endereco();
				
				if (flagAtualizarClienteFisico == true && flagAtualizarClienteJuridico ==  true) {
					panelEscolherTipoCliente.setVisible(true);
				}
				
				else if (flagAtualizarFornecedor == true ) {
					panelEscolherTipoCliente.setVisible(true);
				}
				
				else if (flagAtualizarFuncionario == true ) {
					
					Funcionario funcionario = new Funcionario();
					FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
					
					funcionario.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					
					funcionarioDAO.Consultar(funcionario, endereco);
					consultarPessoa (null,null, null, funcionario, endereco);
					panelCLienteCadastroAtualizacao.setVisible(true);
				
				}
				
				// Exclusão
				
				if (flagExcluirClienteFisico == true && flagExcluirClienteJuridico ==  true) {
					panelEscolherTipoCliente.setVisible(true);
				}
				
				else if (flagExcluirFornecedor == true ) {
					FornecedorDAO fornecedorDAO = new FornecedorDAO();			
					fornecedorDAO.Deletar(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					limparFlagsCamposPessoa();	
					panelExclusaoAlteracaoPessoa.setVisible(false);
					lerTabelaFornecedores();
				}
				
				else if (flagExcluirFuncionario == true ) {		
					FuncionarioDAO funcionarioDAO = new FuncionarioDAO();			
					funcionarioDAO.Deletar(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					limparFlagsCamposPessoa();		
					panelExclusaoAlteracaoPessoa.setVisible(false);
					lerTabelaFuncionarios();
				}				
				
			}
		});
		btnExclusaoAlteracaoBuscarPessoa.setBounds(125, 11, 28, 23);
		btnExclusaoAlteracaoBuscarPessoa.setContentAreaFilled(false);
		btnExclusaoAlteracaoBuscarPessoa.setBorder(null);
		btnExclusaoAlteracaoBuscarPessoa.setBackground(new Color(51, 51, 51));
		panelExclusaoAlteracaoPessoa.add(btnExclusaoAlteracaoBuscarPessoa);
		
		textFieldExclusaoAlteracaoBuscarPessoa =   new JTextField();
		textFieldExclusaoAlteracaoBuscarPessoa.setBounds(60, 12, 55, 20);
		panelExclusaoAlteracaoPessoa.add(textFieldExclusaoAlteracaoBuscarPessoa);
		textFieldExclusaoAlteracaoBuscarPessoa.setColumns(10);
		
		JLabel lblExclusaoAlteracaoBuscarPessoa = new JLabel("ID:");
		lblExclusaoAlteracaoBuscarPessoa.setForeground(Color.WHITE);
		lblExclusaoAlteracaoBuscarPessoa.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblExclusaoAlteracaoBuscarPessoa.setBounds(29, 15, 48, 14);
		panelExclusaoAlteracaoPessoa.add(lblExclusaoAlteracaoBuscarPessoa);
		
		JButton btnPanelClientesVoltar = new JButton("");
		btnPanelClientesVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (panelCLienteCadastroAtualizacao.isVisible()) {
					resposta = JOptionPane.showOptionDialog(null, "Tem certeza que deseja voltar para página inicial? Todo o professo seá perdido", 
					    	   "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if(resposta == JOptionPane.YES_OPTION) {
						limparFlagsCamposPessoa();		
						panelExclusaoAlteracaoPessoa.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelEscolherTipoCliente.setVisible(false);
						tabbedPane.remove(0);
						tabbedPane.addTab("Início", null, panelInicio, null);
						
					}
				}
				else {
					tabbedPane.remove(0);
					tabbedPane.addTab("Início", null, panelInicio, null);
					
				}					
			}
		});
		btnPanelClientesVoltar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgInicioVoltar.png")));
		btnPanelClientesVoltar.setForeground(new Color(255, 255, 255));
		btnPanelClientesVoltar.setBackground(new Color(51, 51, 51));
		btnPanelClientesVoltar.setContentAreaFilled(false);
		btnPanelClientesVoltar.setBorder(null);
		btnPanelClientesVoltar.setBounds(10, 5, 64, 28);
		panelClientes.add(btnPanelClientesVoltar);
		
		/*----------------------------------------------------------------------Painel Fornecedores------------------------------------------------------------------*/
		panelFornecedores = new JPanel();
		panelFornecedores.setBackground(new Color(51, 51, 51));
		//tabbedPane.addTab("Fornecedores", null, panelFornecedores, null);
		panelFornecedores.setLayout(null);
		
		JPanel panelscrollPaneTabelaFornecedores = new JPanel();
		panelscrollPaneTabelaFornecedores.setBackground(Color.DARK_GRAY);
		panelscrollPaneTabelaFornecedores.setBounds(10, 39, 815, 296);
		panelFornecedores.add(panelscrollPaneTabelaFornecedores);		
		panelscrollPaneTabelaFornecedores.setLayout(null);
		
		JScrollPane scrollPaneTabelaFornecedores = new JScrollPane();
		scrollPaneTabelaFornecedores.setBounds(0, 0, 815, 296);
		panelscrollPaneTabelaFornecedores.add(scrollPaneTabelaFornecedores);
		
		tableFornecedores = new JTable();
		tableFornecedores.setBorder(null);
		tableFornecedores.setForeground(Color.WHITE);
		tableFornecedores.setBackground(Color.DARK_GRAY);
		tableFornecedores.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"ID", "Nome", "CNPJ","CPF", "Telefone", "Email"
			}
		));		
		scrollPaneTabelaFornecedores.setViewportView(tableFornecedores);
		
		JButton btnCadastrarFornecedor = new JButton("Cadastrar");
		btnCadastrarFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panelFornecedores.add(panelEscolherTipoCliente);
				panelEscolherTipoCliente.setVisible(true);
				flagCadastroFornecedor = true;
			}
		});
		btnCadastrarFornecedor.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnCadastrarFornecedor.setBounds(414, 11, 131, 22);
		btnCadastrarFornecedor.setBorder(null);
		btnCadastrarFornecedor.setForeground(new Color(255, 255, 255));
		btnCadastrarFornecedor.setBackground(new Color(51, 51, 51));
		btnCadastrarFornecedor.setContentAreaFilled(false);
		panelFornecedores.add(btnCadastrarFornecedor);
		
		JButton btnExcluirFornecedor = new JButton("Excluir");
		btnExcluirFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				flagExcluirFornecedor = true;
				panelFornecedores.add(panelExclusaoAlteracaoPessoa);
				panelExclusaoAlteracaoPessoa.setVisible(true);				
			}
		});
		btnExcluirFornecedor.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnExcluirFornecedor.setBounds(694, 11, 131, 22);
		btnExcluirFornecedor.setForeground(new Color(255, 255, 255));
		btnExcluirFornecedor.setBackground(new Color(51, 51, 51));
		btnExcluirFornecedor.setContentAreaFilled(false);
		btnExcluirFornecedor.setBorder(null);
		panelFornecedores.add(btnExcluirFornecedor);
		
		JButton btnAtualizarFornecedor = new JButton("Atualizar");
		btnAtualizarFornecedor.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnAtualizarFornecedor.setBounds(553, 11, 131, 22);
		btnAtualizarFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				panelFornecedores.add(panelExclusaoAlteracaoPessoa);
				panelExclusaoAlteracaoPessoa.setVisible(true);
				panelFornecedores.add(panelEscolherTipoCliente);
				flagAtualizarFornecedor = true;
			}
		});
		btnAtualizarFornecedor.setForeground(new Color(255, 255, 255));
		btnAtualizarFornecedor.setBackground(new Color(51, 51, 51));
		btnAtualizarFornecedor.setContentAreaFilled(false);
		btnAtualizarFornecedor.setBorder(null);
		panelFornecedores.add(btnAtualizarFornecedor);
		
		JLabel lblBarraCadastrarFornecedor = new JLabel("");
		lblBarraCadastrarFornecedor.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraCadastrarFornecedor.setBounds(445, 32, 70, 1);
		panelFornecedores.add(lblBarraCadastrarFornecedor);
		
		JLabel lblBarraAtualizarFornecedor = new JLabel("");
		lblBarraAtualizarFornecedor.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraAtualizarFornecedor.setBackground(new Color(255, 204, 102));
		lblBarraAtualizarFornecedor.setBounds(581, 32, 70, 1);
		panelFornecedores.add(lblBarraAtualizarFornecedor);
		
		JLabel lblBarraExcluirFornecedor = new JLabel("");
		lblBarraExcluirFornecedor.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraExcluirFornecedor.setBounds(723, 32, 70, 1);
		panelFornecedores.add(lblBarraExcluirFornecedor);
		
		JButton btnPanelFornecedoresVoltar = new JButton("");
		btnPanelFornecedoresVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (panelCLienteCadastroAtualizacao.isVisible()) {
					resposta = JOptionPane.showOptionDialog(null, "Tem certeza que deseja voltar para página inicial? Todo o professo seá perdido", 
					    	   "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if(resposta == JOptionPane.YES_OPTION) {
						limparFlagsCamposPessoa();		
						panelExclusaoAlteracaoPessoa.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelEscolherTipoCliente.setVisible(false);
						tabbedPane.remove(0);
						tabbedPane.addTab("Início", null, panelInicio, null);
					}
				}
				else {
					tabbedPane.remove(0);
					tabbedPane.addTab("Início", null, panelInicio, null);			
				}
			}				
		});
		btnPanelFornecedoresVoltar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgInicioVoltar.png")));
		btnPanelFornecedoresVoltar.setForeground(new Color(255, 255, 255));
		btnPanelFornecedoresVoltar.setBackground(new Color(51, 51, 51));
		btnPanelFornecedoresVoltar.setContentAreaFilled(false);
		btnPanelFornecedoresVoltar.setBorder(null);
		btnPanelFornecedoresVoltar.setBounds(10, 5, 64, 28);		
		panelFornecedores.add(btnPanelFornecedoresVoltar);
		
		
		
		/*-------------------------------------------------------------Painel Funcionarios------------------------------------------------------------------------------*/
		
		panelFuncionarios = new JPanel();
		panelFuncionarios.setBackground(new Color(51, 51, 51));
		//tabbedPane.addTab("Funcionários", null, panelFuncionarios, null);
		panelFuncionarios.setLayout(null);
		
		JPanel panelscrollPaneTabelaFuncionarios = new JPanel();
		panelscrollPaneTabelaFuncionarios.setBackground(Color.DARK_GRAY);
		panelscrollPaneTabelaFuncionarios.setBounds(10, 39, 815, 296);
		panelFuncionarios.add(panelscrollPaneTabelaFuncionarios);		
		panelscrollPaneTabelaFuncionarios.setLayout(null);
		
		JScrollPane scrollPaneTabelaFuncionarios = new JScrollPane();
		scrollPaneTabelaFuncionarios.setBounds(0, 0, 815, 296);
		panelscrollPaneTabelaFuncionarios.add(scrollPaneTabelaFuncionarios);
		
		tableFuncionarios = new JTable();
		tableFuncionarios.setBorder(null);
		tableFuncionarios.setForeground(Color.WHITE);
		tableFuncionarios.setBackground(Color.DARK_GRAY);
		tableFuncionarios.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"ID", "Nome", "CPF", "Telefone", "Email"
			}
		));		
		scrollPaneTabelaFuncionarios.setViewportView(tableFuncionarios);
		
		JButton btnCadastrarFuncionario = new JButton("Cadastrar");
		btnCadastrarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				passwordField.setVisible(true);
				lblBarraSenha.setVisible(true);
				lblSenha.setVisible(true);
				comboBoxCargo.setVisible(true);
				lblCargo.setVisible(true);
				lblCPF.setText("CPF: ");					
				lblDataNascimento.setText("Data de nascimento: ");
				lblDataNascimento.setFont(new Font("Lucida Sans", Font.PLAIN, 13));
				lblBarraEmail.setBounds(325, 72, 160, 1);
				
				panelFuncionarios.add(panelCLienteCadastroAtualizacao);
				panelCLienteCadastroAtualizacao.setVisible(true);
				flagCadastroFuncionario = true;
			}
		});
		btnCadastrarFuncionario.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnCadastrarFuncionario.setBounds(414, 11, 131, 22);
		btnCadastrarFuncionario.setBorder(null);
		btnCadastrarFuncionario.setForeground(new Color(255, 255, 255));
		btnCadastrarFuncionario.setBackground(new Color(51, 51, 51));
		btnCadastrarFuncionario.setContentAreaFilled(false);
		panelFuncionarios.add(btnCadastrarFuncionario);
		
		JButton btnExcluirFuncionario = new JButton("Excluir");
		btnExcluirFuncionario.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnExcluirFuncionario.setBounds(694, 11, 131, 22);
		btnExcluirFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flagExcluirFuncionario = true;				
				panelFuncionarios.add(panelExclusaoAlteracaoPessoa);
				panelExclusaoAlteracaoPessoa.setVisible(true);				
			}
		});
		btnExcluirFuncionario.setForeground(new Color(255, 255, 255));
		btnExcluirFuncionario.setBackground(new Color(51, 51, 51));
		btnExcluirFuncionario.setContentAreaFilled(false);
		btnExcluirFuncionario.setBorder(null);
		panelFuncionarios.add(btnExcluirFuncionario);
		
		JButton btnAtualizarFuncionario = new JButton("Atualizar");
		btnAtualizarFuncionario.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnAtualizarFuncionario.setBounds(553, 11, 131, 22);
		btnAtualizarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				passwordField.setVisible(true);
				lblBarraSenha.setVisible(true);
				lblSenha.setVisible(true);
				comboBoxCargo.setVisible(true);
				lblCargo.setVisible(true);
				lblCPF.setText("CPF: ");					
				lblBarraEmail.setBounds(325, 72, 160, 1);
				
				panelFuncionarios.add(panelExclusaoAlteracaoPessoa);	
				panelFuncionarios.add(panelCLienteCadastroAtualizacao);
				panelExclusaoAlteracaoPessoa.setVisible(true);
				flagAtualizarFuncionario = true;
			}
		});
		btnAtualizarFuncionario.setForeground(new Color(255, 255, 255));
		btnAtualizarFuncionario.setBackground(new Color(51, 51, 51));
		btnAtualizarFuncionario.setContentAreaFilled(false);
		btnAtualizarFuncionario.setBorder(null);
		panelFuncionarios.add(btnAtualizarFuncionario);
		
		JLabel lblBarraCadastrarFuncionario = new JLabel("");
		lblBarraCadastrarFuncionario.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraCadastrarFuncionario.setBounds(445, 32, 70, 1);
		panelFuncionarios.add(lblBarraCadastrarFuncionario);
		
		JLabel lblBarraAtualizarFuncionario= new JLabel("");
		lblBarraAtualizarFuncionario.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraAtualizarFuncionario.setBackground(new Color(255, 204, 102));
		lblBarraAtualizarFuncionario.setBounds(581, 32, 70, 1);
		panelFuncionarios.add(lblBarraAtualizarFuncionario);
		
		JLabel lblBarraExcluirFuncionario = new JLabel("");
		lblBarraExcluirFuncionario.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraExcluirFuncionario.setBounds(723, 32, 70, 1);
		panelFuncionarios.add(lblBarraExcluirFuncionario);
		
		JButton btnPanelFuncionariosVoltar = new JButton("");
		btnPanelFuncionariosVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (panelCLienteCadastroAtualizacao.isVisible()) {
					resposta = JOptionPane.showOptionDialog(null, "Tem certeza que deseja voltar para página inicial? Todo o processo seá perdido", 
					    	   "Informação", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if(resposta == JOptionPane.YES_OPTION) {
						limparFlagsCamposPessoa();		
						panelExclusaoAlteracaoPessoa.setVisible(false);
						panelCLienteCadastroAtualizacao.setVisible(false);
						panelEscolherTipoCliente.setVisible(false);
						tabbedPane.remove(0);
						tabbedPane.addTab("Início", null, panelInicio, null);
					}
				}
				else {
					tabbedPane.remove(0);
					tabbedPane.addTab("Início", null, panelInicio, null);	
				}								
			}
		});
		btnPanelFuncionariosVoltar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgInicioVoltar.png")));
		btnPanelFuncionariosVoltar.setForeground(new Color(255, 255, 255));
		btnPanelFuncionariosVoltar.setBackground(new Color(51, 51, 51));
		btnPanelFuncionariosVoltar.setContentAreaFilled(false);
		btnPanelFuncionariosVoltar.setBorder(null);
		btnPanelFuncionariosVoltar.setBounds(10, 5, 64, 28);
		panelFuncionarios.add(btnPanelFuncionariosVoltar);		
		
		//------------------------------------------------------------------------------------------------------------------------
		
		
	}
	
	public Date formatador (String data) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		Date date = (Date)formatter.parse("01/29/02");
		return date;
	}
	
	// Popular JTables com os dados do MySQL	
	
    public void lerTabelaClienteFisico() {
        
        DefaultTableModel modelo = (DefaultTableModel) tableClientesFisicos.getModel();
        modelo.setNumRows(0);
    	ClienteFisicoDAO clienteDAO = new ClienteFisicoDAO();
    	
        for (ClienteFisico clienteFisico : clienteDAO.ListarClienteAtivos()) {

            	modelo.addRow(new Object[]{
            	clienteFisico.getId(),		
                clienteFisico.getNome(),
                clienteFisico.getCpf(),
                clienteFisico.getCelular(),
                clienteFisico.getEmail()
         });

        }

    }
	
    public void lerTabelaClienteJuridico() {
        
        DefaultTableModel modelo = (DefaultTableModel) tableClientesJuridicos.getModel();
        modelo.setNumRows(0);
    	ClienteJuridicoDAO clienteDAO = new ClienteJuridicoDAO();
    	

        for (ClienteJuridico clienteJuridico : clienteDAO.ListarClientesAtivos()) {

        	//"ID", "Nome", "CNPJ","Telefone", "Email"
        	modelo.addRow(new Object[]{
        		clienteJuridico.getId(),	
        		clienteJuridico.getNome(),
        		clienteJuridico.getCnpj(),
        		clienteJuridico.getCelular(),
        		clienteJuridico.getEmail()                
        	});

        }
    }
    
    public void lerTabelaFuncionarios() {
        
        DefaultTableModel modelo = (DefaultTableModel) tableFuncionarios.getModel();
        modelo.setNumRows(0);      
    	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        for (Funcionario funcionario : funcionarioDAO.ListarFuncionariosAtivos()) {
        	modelo.addRow(new Object[]{
        			funcionario.getId(),	
        			funcionario.getNome(),
        			funcionario.getCpf(),
        			funcionario.getCelular(),
        			funcionario.getEmail()                
        	});
        }
        
    }    
    
    public void lerTabelaFornecedores() {
        
        DefaultTableModel modelo = (DefaultTableModel) tableFornecedores.getModel();
        modelo.setNumRows(0);
    	FornecedorDAO fornecedorDAO = new FornecedorDAO();
    	

        for (Fornecedor fornecedor : fornecedorDAO.ListarFornecedoresAtivos()) {

        	//"ID", "Nome", "CNPJ","Telefone", "Email"
        	modelo.addRow(new Object[]{
        			fornecedor.getId(),	
        			fornecedor.getNome(),
        			fornecedor.getCnpj(),
        			fornecedor.getCpf(),
        			fornecedor.getCelular(),
        			fornecedor.getEmail()                
        	});

        }
    }   
		
	public void limparFlagsCamposPessoa() {
		
		//torna as flags falsas e limpa os campos de texto de Fornecedor, Funcionário, Cliente Físico e Jurídico
		
        flagCadastroClienteJuridico = false;   
        flagCadastroClienteFisico = false;   
        flagCadastroFornecedor = false;   
        flagCadastroFuncionario = false;   
 
        flagAtualizarClienteJuridico = false;   
        flagAtualizarClienteFisico = false;   
        flagAtualizarFornecedor = false;   
        flagAtualizarFuncionario = false;		

        flagExcluirClienteJuridico = false;
    	flagExcluirClienteFisico = false;
    	flagExcluirFornecedor = false;
    	flagExcluirFuncionario = false;
    	        
        
		textFieldCep.setText("");
		textFieldBairro.setText("");
		textFieldCidade.setText("");
		textFieldEstado.setText("");
		textFieldRua.setText("");
		textFieldNumero.setText("");
        
		textFieldNome.setText("");
		textFieldCPF.setText("");
		textFieldDataNascimento.setText("");
		textFieldEmail.setText("");
		textFieldTelefone.setText("");
		passwordField.setText("");;
		textFieldCep.setText("");
		textFieldEstado.setText("");
		textFieldCidade.setText("");
		textFieldBairro.setText("");
		textFieldRua.setText("");
		textFieldNumero.setText("");
		
		textFieldExclusaoAlteracaoBuscarPessoa.setText("");
	
		armazenaEndereco = 0;
	}
		
	 @SuppressWarnings("deprecation")
	 public void cadastrarPessoa (Fornecedor fornecedor,
			 					  ClienteJuridico clienteJuridico, 
			 					  ClienteFisico clienteFisico, 
			 					  Funcionario funcionario, 
			 					  Endereco endereco) throws ParseException {
		 
			 endereco.setCep(textFieldCep.getText());
			 endereco.setBairro(textFieldBairro.getText());
			 endereco.setCidade(textFieldCidade.getText());
			 endereco.setEstado(textFieldEstado.getText());
			 endereco.setRua(textFieldRua.getText());
			 endereco.setNumero(Integer.parseInt(textFieldNumero.getText()));
			 
			 if (fornecedor != null) {
			        try {
			        	fornecedor.setCelular(textFieldTelefone.getText());
			        	fornecedor.setEmail(textFieldEmail.getText());
			        	fornecedor.setNome(textFieldNome.getText());
			        	fornecedor.setDataCriacao(textFieldDataNascimento.getText());
			        	
			        	if(comboBoxEscolherTipoCliente.getSelectedItem().toString().equals("FÍSICO")) {
			        		fornecedor.setCpf(textFieldCPF.getText()); // se o fornecedor for do tipo físico, o objeto vai ter 
			        		fornecedor.setCnpj(null);				   //o atributo cpf preenchido e o atributo cnpj setado como null 
			        	}
			        	else {
			        		fornecedor.setCnpj(textFieldCPF.getText());
			        		fornecedor.setCpf(null);
			        	}       				        	
			        
			        } catch (Exception e) {
			            e.printStackTrace();
			        }				 
			 }

			 else if (clienteJuridico != null) {			
			        try {
			            clienteJuridico.setCelular(textFieldTelefone.getText());
						clienteJuridico.setCnpj(textFieldCPF.getText());
						clienteJuridico.setDataCriacao(textFieldDataNascimento.getText());
						System.out.println(clienteJuridico.getDataCriacao());
						clienteJuridico.setEmail(textFieldEmail.getText());
						clienteJuridico.setNome(textFieldNome.getText());
			        
			        } catch (Exception e) {
			            e.printStackTrace();
			        }			 
			 }
			 else if (clienteFisico != null) {
			        try {
			        	clienteFisico.setCelular(textFieldTelefone.getText());
			        	clienteFisico.setCpf(textFieldCPF.getText());
			        	clienteFisico.setDataCriacao(textFieldDataNascimento.getText());
			        	clienteFisico.setEmail(textFieldEmail.getText());
			        	clienteFisico.setNome(textFieldNome.getText());
			        
			        } catch (Exception e) {
			            e.printStackTrace();
			        }				 
			 }
			 else {
				 	funcionario.setDataCriacao(textFieldDataNascimento.getText());
		        	funcionario.setCelular(textFieldTelefone.getText());
		        	funcionario.setCpf(textFieldCPF.getText());
		        	funcionario.setSenha(passwordField.getText());
		        	funcionario.setEmail(textFieldEmail.getText());
		        	funcionario.setNome(textFieldNome.getText());	
		        	funcionario.setCargo(comboBoxCargo.getSelectedItem().toString());
			 }
		 
		 
	 
	 
	 
	 }
	
	 public void consultarPessoa (Fornecedor fornecedor,
			  					  ClienteJuridico clienteJuridico, 
			  					  ClienteFisico clienteFisico, 
			  					  Funcionario funcionario, 
			  					  Endereco endereco) {

		 textFieldCep.setText(endereco.getCep());
		 textFieldBairro.setText(endereco.getBairro());
		 textFieldCidade.setText(endereco.getCidade());
		 textFieldEstado.setText(endereco.getEstado());
		 textFieldRua.setText(endereco.getRua());
		 textFieldNumero.setText(String.valueOf(endereco.getNumero()));
		 armazenaEndereco = endereco.getId();
		 
		 if (fornecedor != null) {
		        try {
		        	textFieldTelefone.setText(fornecedor.getCelular());
		        	textFieldEmail.setText(fornecedor.getEmail());
		        	textFieldNome.setText(fornecedor.getNome());	
		        	textFieldDataNascimento.setText(fornecedor.getDataCriacao());
		        	if(fornecedor.getCpf() == null) {
		        		textFieldCPF.setText(fornecedor.getCnpj()); 
		        	}
		        	else {
		        		textFieldCPF.setText(fornecedor.getCpf());
		        	}       				        	

		        
		        } catch (Exception e) {
		            e.printStackTrace();
		        }				 
		 }

		 else if (clienteJuridico != null) {			
		        try {
		            textFieldTelefone.setText(clienteJuridico.getCelular());
					textFieldCPF.setText(clienteJuridico.getCnpj());
					textFieldDataNascimento.setText(clienteJuridico.getDataCriacao());
					textFieldEmail.setText(clienteJuridico.getEmail());
					textFieldNome.setText(clienteJuridico.getNome());
		        
		        } catch (Exception e) {
		            e.printStackTrace();
		        }			 
		 }
		 else if (clienteFisico != null) {
		        try {
		        	textFieldTelefone.setText(clienteFisico.getCelular());
		        	textFieldCPF.setText(clienteFisico.getCpf());
		        	textFieldDataNascimento.setText(clienteFisico.getDataCriacao());
		        	textFieldEmail.setText(clienteFisico.getEmail());
		        	textFieldNome.setText(clienteFisico.getNome());
		        
		        } catch (Exception e) {
		            e.printStackTrace();
		        }				 
		 }
		 else {
	        	textFieldTelefone.setText(funcionario.getCelular());
	        	textFieldCPF.setText(funcionario.getCpf());
	        	passwordField.setText(funcionario.getSenha());
	        	textFieldEmail.setText(funcionario.getEmail());
	        	textFieldNome.setText(funcionario.getNome());
	        	textFieldDataNascimento.setText(funcionario.getDataCriacao());
	        	
	        	if (funcionario.getCargo().equals("ADMINISTRADOR")) {
	        		comboBoxCargo.setSelectedItem(0);
	        	}
	        	else {
	        		comboBoxCargo.setSelectedItem(1);
	        	}
	        	
		 }


	 }	
}