package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import DAO.FornecedorDAO;
import DAO.FuncionarioDAO;
import DAO.LivroDAO;
import Model.Endereco;
import Model.Fornecedor;
import Model.Funcionario;
import Model.Livro;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import Model.Categoria;
import javax.swing.JTextArea;

public class TelaLivro extends JFrame {

	private JPanel contentPane;
	private JPanel panelLivros;
	private JPanel panelCadastroEAlteracaoDeLivros;
	private JPanel panelExclusaoAlteracaoLivro;
	
	private JTable tableLivros;
	
	private JTextField textFieldTitulo;
	private JTextField textFieldPreco;
	private JTextField textFieldQtdeEstoque;
	private JTextField textFieldQtdePaginas;
	private JTextField textFieldISBN;
	private JTextField textFieldEditora;
	private JTextField textFieldExclusaoAlteracaoBuscarPessoa;
	private JTextField textFieldFornecedor;
	
	private JTextArea textAreaDescricao;
	
	private JComboBox comboBoxEscolherGenero;
	
	private boolean flagAtualizarLivro = false;
	private boolean flagCadastrarLivro = false;
	private boolean flagExcluirLivro = false;

	
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
					TelaLivro frame = new TelaLivro();
					frame.setUndecorated(true);
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
	public TelaLivro() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				/**/
				lerTabelaLivros();
			}
		});	
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1007, 672);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 51, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		/*--------------------------------------------------Imagem lateral da tela------------------------------------------------------------------*/
		JPanel panelImagem = new JPanel();
		panelImagem.setBackground(new Color(102, 205, 170));
		panelImagem.setBounds(0, 0, 162, 687);
		contentPane.add(panelImagem);
		panelImagem.setLayout(null);
		
		JLabel lblImagemLateral = new JLabel("");
		lblImagemLateral.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgTelas.jpg")));
		lblImagemLateral.setBounds(0, 0, 187, 713);
		panelImagem.add(lblImagemLateral);
		
		/*--------------------------------------------------panel livros------------------------------------------------------------------*/
		panelLivros = new JPanel();
		panelLivros.setBackground(new Color(51, 51, 51));
		panelLivros.setBounds(161, 0, 840, 643);
		contentPane.add(panelLivros);
			
		JButton btnCadastrarLivro = new JButton("Cadastrar");
		btnCadastrarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCadastroEAlteracaoDeLivros.setVisible(true);
				flagCadastrarLivro = true;
			}
		});
		panelLivros.setLayout(null);
		btnCadastrarLivro.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnCadastrarLivro.setBounds(414, 11, 131, 22);
		btnCadastrarLivro.setBorder(null);
		btnCadastrarLivro.setForeground(new Color(255, 255, 255));
		btnCadastrarLivro.setBackground(new Color(51, 51, 51));
		btnCadastrarLivro.setContentAreaFilled(false);
		panelLivros.add(btnCadastrarLivro);
		
		JButton btnExcluirLivro = new JButton("Excluir");
		btnExcluirLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flagAtualizarLivro = false;
				flagExcluirLivro = true;
				panelExclusaoAlteracaoLivro.setVisible(true);
			}
		});
		btnExcluirLivro.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnExcluirLivro.setBounds(694, 11, 131, 22);
		btnExcluirLivro.setForeground(new Color(255, 255, 255));
		btnExcluirLivro.setBackground(new Color(51, 51, 51));
		btnExcluirLivro.setContentAreaFilled(false);
		btnExcluirLivro.setBorder(null);
		panelLivros.add(btnExcluirLivro);
		
		JButton btnAtualizarLivro = new JButton("Atualizar");
		btnAtualizarLivro.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnAtualizarLivro.setBounds(553, 11, 131, 22);
		btnAtualizarLivro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				flagAtualizarLivro = true;
				flagExcluirLivro = false;
				panelExclusaoAlteracaoLivro.setVisible(true);
			}
		});
		btnAtualizarLivro.setForeground(new Color(255, 255, 255));
		btnAtualizarLivro.setBackground(new Color(51, 51, 51));
		btnAtualizarLivro.setContentAreaFilled(false);
		btnAtualizarLivro.setBorder(null);
		panelLivros.add(btnAtualizarLivro);
		
		JLabel lblBarraCadastrarLivro = new JLabel("");
		lblBarraCadastrarLivro.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraCadastrarLivro.setBounds(445, 32, 70, 1);
		panelLivros.add(lblBarraCadastrarLivro);
		
		JLabel lblBarraAtualizarLivro = new JLabel("");
		lblBarraAtualizarLivro.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraAtualizarLivro.setBackground(new Color(255, 204, 102));
		lblBarraAtualizarLivro.setBounds(581, 32, 70, 1);
		panelLivros.add(lblBarraAtualizarLivro);
		
		JLabel lblBarraExcluirLivro = new JLabel("");
		lblBarraExcluirLivro.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraExcluirLivro.setBounds(723, 32, 70, 1);
		panelLivros.add(lblBarraExcluirLivro);
		
		JScrollPane scrollPaneTabelaLivros = new JScrollPane();
		scrollPaneTabelaLivros.setBounds(20, 44, 810, 302);
		panelLivros.add(scrollPaneTabelaLivros);
		
		tableLivros = new JTable();
		tableLivros.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		tableLivros.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Título", "Preço", "Qtde. estoque", "Qtde. página"
			}
		));
		scrollPaneTabelaLivros.setViewportView(tableLivros);
		
		JButton btnPanelLivrosVoltar = new JButton("");
		btnPanelLivrosVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCamposFlagsLivros();			
			}
		});
		btnPanelLivrosVoltar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgInicioVoltar.png")));
		btnPanelLivrosVoltar.setForeground(new Color(255, 255, 255));
		btnPanelLivrosVoltar.setBackground(new Color(51, 51, 51));
		btnPanelLivrosVoltar.setContentAreaFilled(false);
		btnPanelLivrosVoltar.setBorder(null);
		btnPanelLivrosVoltar.setBounds(10, 5, 64, 28);
		panelLivros.add(btnPanelLivrosVoltar);

		panelExclusaoAlteracaoLivro = new JPanel();
		panelExclusaoAlteracaoLivro.setBackground(Color.DARK_GRAY);
		panelExclusaoAlteracaoLivro.setBounds(663, 365, 167, 45);
		panelLivros.add(panelExclusaoAlteracaoLivro);
		panelExclusaoAlteracaoLivro.setLayout(null);
		panelExclusaoAlteracaoLivro.setVisible(false);
		//(29, 15, 48, 14);
		
		JButton btnExclusaoAlteracaoBuscarPessoa = new JButton("");
		btnExclusaoAlteracaoBuscarPessoa.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgCheck.png")));
		btnExclusaoAlteracaoBuscarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				LivroDAO livroDAO = new LivroDAO();
				Livro livro = new Livro();
				
				if (flagAtualizarLivro == true) {
					System.out.println(flagExcluirLivro);
					System.out.println(flagAtualizarLivro);
					livro.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					livroDAO.Consultar(livro);
					consultarLivro (livro);
					panelCadastroEAlteracaoDeLivros.setVisible(true);
				}
				else if (flagExcluirLivro == true) {
					livroDAO.Deletar(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					limparCamposFlagsLivros();	
				}

			}
		});
		btnExclusaoAlteracaoBuscarPessoa.setBounds(125, 11, 28, 23);
		btnExclusaoAlteracaoBuscarPessoa.setContentAreaFilled(false);
		btnExclusaoAlteracaoBuscarPessoa.setBorder(null);
		btnExclusaoAlteracaoBuscarPessoa.setBackground(new Color(51, 51, 51));
		panelExclusaoAlteracaoLivro.add(btnExclusaoAlteracaoBuscarPessoa);
		
		textFieldExclusaoAlteracaoBuscarPessoa = new JTextField();
		textFieldExclusaoAlteracaoBuscarPessoa.setBounds(60, 12, 55, 20);
		panelExclusaoAlteracaoLivro.add(textFieldExclusaoAlteracaoBuscarPessoa);
		textFieldExclusaoAlteracaoBuscarPessoa.setColumns(10);
		
		JLabel lblExclusaoAlteracaoBuscarPessoa = new JLabel("ID:");
		lblExclusaoAlteracaoBuscarPessoa.setForeground(Color.WHITE);
		lblExclusaoAlteracaoBuscarPessoa.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblExclusaoAlteracaoBuscarPessoa.setBounds(29, 15, 48, 14);
		panelExclusaoAlteracaoLivro.add(lblExclusaoAlteracaoBuscarPessoa);
		
		panelCadastroEAlteracaoDeLivros = new JPanel();
		panelCadastroEAlteracaoDeLivros.setBorder(null);
		panelCadastroEAlteracaoDeLivros.setBackground(Color.DARK_GRAY);
		panelCadastroEAlteracaoDeLivros.setBounds(20, 429, 810, 214);
		panelLivros.add(panelCadastroEAlteracaoDeLivros);
		panelCadastroEAlteracaoDeLivros.setLayout(null);
		panelCadastroEAlteracaoDeLivros.setVisible(false);
		
		JLabel lblImgLivro = new JLabel("");
		lblImgLivro.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLabelLivro.png")));
		lblImgLivro.setBounds(10, 11, 200, 192);
		panelCadastroEAlteracaoDeLivros.add(lblImgLivro);
		
		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o:");
		lblDescricao.setForeground(Color.WHITE);
		lblDescricao.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblDescricao.setBounds(220, 11, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblDescricao);
		
		textAreaDescricao = new JTextArea();
		textAreaDescricao.setForeground(Color.WHITE);
		textAreaDescricao.setBackground(Color.DARK_GRAY);
		textAreaDescricao.setLineWrap(true);
		textAreaDescricao.setBounds(296, 11, 200, 192);
		textAreaDescricao.setBorder(new LineBorder(new Color(0, 128, 128)));
		panelCadastroEAlteracaoDeLivros.add(textAreaDescricao);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo:");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblTitulo.setBounds(506, 11, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblTitulo);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setForeground(Color.WHITE);
		textFieldTitulo.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldTitulo.setBackground(Color.DARK_GRAY);
		textFieldTitulo.setBounds(559, 8, 241, 20);
		textFieldTitulo.setBorder(null);
		panelCadastroEAlteracaoDeLivros.add(textFieldTitulo);
		textFieldTitulo.setColumns(10);
		
		JLabel lblBarraTitulo = new JLabel("0");
		lblBarraTitulo.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraTitulo.setBounds(559, 28, 241, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraTitulo);
		
		JLabel lblGenero = new JLabel("G\u00EAnero:");
		lblGenero.setForeground(Color.WHITE);
		lblGenero.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblGenero.setBounds(506, 51, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblGenero);
		
		comboBoxEscolherGenero = new JComboBox();
		comboBoxEscolherGenero.setModel(new DefaultComboBoxModel(Categoria.values()));
		comboBoxEscolherGenero.setBounds(558, 48, 242, 17);
		comboBoxEscolherGenero.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		panelCadastroEAlteracaoDeLivros.add(comboBoxEscolherGenero);		
		
		JLabel lblPreco = new JLabel("Pre\u00E7o:  R$");
		lblPreco.setForeground(Color.WHITE);
		lblPreco.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblPreco.setBounds(506, 80, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblPreco);
		
		textFieldPreco = new JTextField();
		textFieldPreco.setForeground(Color.WHITE);
		textFieldPreco.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldPreco.setColumns(10);
		textFieldPreco.setBorder(null);
		textFieldPreco.setBackground(Color.DARK_GRAY);
		textFieldPreco.setBounds(582, 76, 45, 20);
		panelCadastroEAlteracaoDeLivros.add(textFieldPreco);
		
		JLabel lblBarraPreco = new JLabel("0");
		lblBarraPreco.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraPreco.setBounds(559, 96, 70, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraPreco);
		
		JLabel lblQtdeEstoque = new JLabel("Qtde.:");
		lblQtdeEstoque.setForeground(Color.WHITE);
		lblQtdeEstoque.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblQtdeEstoque.setBounds(635, 80, 57, 14);
		panelCadastroEAlteracaoDeLivros.add(lblQtdeEstoque);
		
		textFieldQtdeEstoque = new JTextField();
		textFieldQtdeEstoque.setColumns(10);
		textFieldQtdeEstoque.setBorder(null);
		textFieldQtdeEstoque.setForeground(Color.WHITE);
		textFieldQtdeEstoque.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldQtdeEstoque.setBackground(Color.DARK_GRAY);
		textFieldQtdeEstoque.setBounds(676, 76, 28, 20);
		panelCadastroEAlteracaoDeLivros.add(textFieldQtdeEstoque);
		
		JLabel lblBarraQtdeEstoque = new JLabel("0");
		lblBarraQtdeEstoque.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraQtdeEstoque.setBounds(676, 96, 35, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraQtdeEstoque);
		
		JLabel lblQtdePaginas = new JLabel("Pags.:");
		lblQtdePaginas.setForeground(Color.WHITE);
		lblQtdePaginas.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblQtdePaginas.setBounds(714, 80, 57, 14);
		panelCadastroEAlteracaoDeLivros.add(lblQtdePaginas);
		
		textFieldQtdePaginas = new JTextField();
		textFieldQtdePaginas.setColumns(10);
		textFieldQtdePaginas.setBorder(null);
		textFieldQtdePaginas.setForeground(Color.WHITE);
		textFieldQtdePaginas.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldQtdePaginas.setBackground(Color.DARK_GRAY);
		textFieldQtdePaginas.setBounds(760, 76, 40, 20);
		panelCadastroEAlteracaoDeLivros.add(textFieldQtdePaginas);
		
		JLabel lblBarraQtdePagina = new JLabel("0");
		lblBarraQtdePagina.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraQtdePagina.setBounds(760, 96, 40, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraQtdePagina);
		
		JLabel lblISBN = new JLabel("ISBN:");
		lblISBN.setForeground(Color.WHITE);
		lblISBN.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblISBN.setBounds(506, 108, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblISBN);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setColumns(10);
		textFieldISBN.setBorder(null);
		textFieldISBN.setForeground(Color.WHITE);
		textFieldISBN.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldISBN.setBackground(Color.DARK_GRAY);
		textFieldISBN.setBounds(559, 105, 241, 20);
		panelCadastroEAlteracaoDeLivros.add(textFieldISBN);
		
		JLabel lblBarraISBN = new JLabel("0");
		lblBarraISBN.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraISBN.setBounds(559, 126, 241, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraISBN);
		
		JLabel lblEditora = new JLabel("Editora:");
		lblEditora.setForeground(Color.WHITE);
		lblEditora.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblEditora.setBounds(506, 138, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblEditora);
		
		textFieldEditora = new JTextField();
		textFieldEditora.setColumns(10);
		textFieldEditora.setBorder(null);
		textFieldEditora.setForeground(Color.WHITE);
		textFieldEditora.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		textFieldEditora.setBackground(Color.DARK_GRAY);
		textFieldEditora.setBounds(559, 133, 241, 20);
		panelCadastroEAlteracaoDeLivros.add(textFieldEditora);
		
		JLabel lblBarraEditora = new JLabel("0");
		lblBarraEditora.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraEditora.setBounds(559, 153, 241, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraEditora);
		
		JButton btnCancelar = new JButton("");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCamposFlagsLivros();
				panelCadastroEAlteracaoDeLivros.setVisible(false);
				panelExclusaoAlteracaoLivro.setVisible(false);
			}
		});
		btnCancelar.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgCancelarLivro.png")));
		btnCancelar.setBounds(755, 164, 45, 39);
		btnCancelar.setForeground(new Color(255, 255, 255));
		btnCancelar.setBackground(new Color(51, 51, 51));
		btnCancelar.setContentAreaFilled(false);
		btnCancelar.setBorder(null);
		panelCadastroEAlteracaoDeLivros.add(btnCancelar);
		
		JButton btnSalvar = new JButton("");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				LivroDAO livroDAO = new LivroDAO();
				Livro livro = new Livro();
				
				if (flagAtualizarLivro = true) {
					cadastrarAtualizarLivro (livro);
					livro.setId(Integer.parseInt(textFieldExclusaoAlteracaoBuscarPessoa.getText()));
					livroDAO.Atualizar(livro);
				}
				else if(flagCadastrarLivro = true) {
					cadastrarAtualizarLivro (livro);
					livroDAO.Cadastrar(livro);					
				}
				
				panelCadastroEAlteracaoDeLivros.setVisible(false);
				panelExclusaoAlteracaoLivro.setVisible(false);				
				lerTabelaLivros();
				limparCamposFlagsLivros();
			}
		});
		btnSalvar.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnSalvar.setBounds(700, 164, 45, 39);
		btnSalvar.setForeground(new Color(255, 255, 255));
		btnSalvar.setBackground(new Color(51, 51, 51));
		btnSalvar.setContentAreaFilled(false);
		btnSalvar.setBorder(null);
		panelCadastroEAlteracaoDeLivros.add(btnSalvar);
		
		JLabel lblFornecedor = new JLabel("Fornecedor:");
		lblFornecedor.setForeground(Color.WHITE);
		lblFornecedor.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		lblFornecedor.setBounds(506, 167, 80, 14);
		panelCadastroEAlteracaoDeLivros.add(lblFornecedor);
		
		textFieldFornecedor = new JTextField();
		textFieldFornecedor.setColumns(10);
		textFieldFornecedor.setBorder(null);
		textFieldFornecedor.setBackground(Color.DARK_GRAY);
		textFieldFornecedor.setForeground(Color.WHITE);
		textFieldFornecedor.setFont(new Font("Lucida Console", Font.PLAIN, 12));		
		textFieldFornecedor.setBounds(582, 164, 104, 20);
		panelCadastroEAlteracaoDeLivros.add(textFieldFornecedor);
		
		JLabel lblBarraFornecedor = new JLabel("0");
		lblBarraFornecedor.setIcon(new ImageIcon(TelaLivro.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraFornecedor.setBounds(582, 185, 104, 1);
		panelCadastroEAlteracaoDeLivros.add(lblBarraFornecedor);
	
		
	}
	
	public void limparCamposFlagsLivros() {
		textFieldTitulo.setText("");
		textFieldPreco.setText("");
		textFieldQtdeEstoque.setText("");
		textFieldQtdePaginas.setText("");
		textFieldISBN.setText("");
		textFieldEditora.setText("");
		textAreaDescricao.setText("");
		textFieldFornecedor.setText("");
		
		flagAtualizarLivro=false;
		flagCadastrarLivro=false;
		flagExcluirLivro=false;
	}
	
	public void cadastrarAtualizarLivro (Livro livro) {
		livro.setTitulo(textFieldTitulo.getText());
		livro.setPreco(Double.parseDouble(textFieldPreco.getText()));
		livro.setEditora(textFieldEditora.getText());
		livro.setDescricao(textAreaDescricao.getText());
		livro.setQuantidadeEstoque(Integer.parseInt(textFieldQtdeEstoque.getText()));
		livro.setQuantidadePaginas(Integer.parseInt(textFieldQtdePaginas.getText()));
		livro.setIsbn(textFieldISBN.getText());
		livro.setCategoria(comboBoxEscolherGenero.getSelectedItem().toString());
		
		FornecedorDAO fornecedorDAO = new FornecedorDAO(); 
		
		livro.setIdFornecedor(fornecedorDAO.RetornarIdFornecedor(textFieldFornecedor.getText()));
		
	}
	
	public void consultarLivro (Livro livro) {
		textFieldTitulo.setText(livro.getTitulo());
		textFieldPreco.setText(String.valueOf(livro.getPreco()));
		textFieldQtdeEstoque.setText(String.valueOf(livro.getQuantidadeEstoque()));
		textFieldQtdePaginas.setText(String.valueOf(livro.getQuantidadePaginas()));
		textFieldISBN.setText(livro.getIsbn());
		textFieldEditora.setText(livro.getEditora());		
		textAreaDescricao.setText(livro.getDescricao());
		
		FornecedorDAO fornecedorDAO = new FornecedorDAO();
		
		textFieldFornecedor.setText(fornecedorDAO.RetornarNomeFornecedor(livro.getIdFornecedor()));
	}	
	
	public void lerTabelaLivros() {
        DefaultTableModel modelo = (DefaultTableModel) tableLivros.getModel();
        modelo.setNumRows(0);
    	LivroDAO livroDAO = new LivroDAO();
    	

        for (Livro livro : livroDAO.Listar()) {

        	//"ID", "Título", "Preço", "Qtde. estoque", "Qtde. página"
        	modelo.addRow(new Object[]{
        			livro.getId(),                
        			livro.getTitulo(),
        			livro.getPreco(),
        			livro.getQuantidadePaginas(),
        			livro.getQuantidadeEstoque()
        	});

        }

	}
}
