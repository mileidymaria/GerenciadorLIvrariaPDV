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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DAO.ClienteFisicoDAO;
import DAO.ClienteJuridicoDAO;
import DAO.LivroDAO;
import DAO.PedidoDAO;
import Model.ClienteFisico;
import Model.ClienteJuridico;
import Model.Endereco;
import Model.ItensPedidos;
import Model.Livro;
import Model.Pedido;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.border.LineBorder;

public class TelaPedidos extends JFrame {

	private JPanel contentPane;
	private JPanel panelVendas;
	private JPanel panelAlteracaoVendas;
	private JPanel panelAlteracaoDeVendas;
	
	private boolean flagAtualizarVenda = false;
	private boolean flagCadastrarVenda = false;
	private boolean flagExcluirVenda = false;	
	
	private JTable tableVendas;
	private JTable tableLivrosDoPedido;
	
	private JTextField textFieldCliente;
	private JTextField textFieldCPFCNPJ;
	private JTextField textFieldFormaPagameto;
	private JTextField textFieldIdCliente;
	private JTextField textFieldIdPedido;
	private JTextField textFieldTotalVenda;
	private JTextField textFieldDataVenda;
	private JTextField textFieldFuncionarioResponsavel;
	private JTextField textFieldTitulo;
	
	private JSpinner spinner;
	
	private boolean flagClienteFisico;
	private boolean flagClienteJuridico;

	private int armazenaIdLivro;
	private int armazenaLinhaSelecionadaTabelaPedidos;
	private int armazenaLinhaSelecionadaTabelaItensPedidos;
	
	private int armazenaClienteFisico;
	private int armazenaClienteJuridico;
	
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
					TelaPedidos frame = new TelaPedidos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void lerTabelaVendas() {
        DefaultTableModel modelo = (DefaultTableModel) tableVendas.getModel();
        modelo.setNumRows(0);
    	PedidoDAO pedidoDAO = new PedidoDAO();
    	ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
    	ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
        for (Pedido pedido : pedidoDAO.Listar()) {

        	if(pedido.getIdClienteFisico() == 1) {
            	modelo.addRow(new Object[]{
            			pedido.getId(),
            			clienteJuridicoDAO.RetornarNomeCliente(pedido.getIdClienteJuridico()),
            			pedido.getTotal(),
            			pedido.getDataPedido(),
            			pedido.getIdFuncionario(),
            			pedido.getFormaPagamento()
            	});  
            	armazenaClienteJuridico = pedido.getIdClienteJuridico();
            	System.out.println("\nDentro da funcao: " + pedido.getIdClienteJuridico());
        	}
        	else {
            	modelo.addRow(new Object[]{
            			pedido.getId(),
            			clienteFisicoDAO.RetornarNomeCliente(pedido.getIdClienteFisico()),
            			pedido.getTotal(),
            			pedido.getDataPedido(),
            			pedido.getIdFuncionario(),
            			pedido.getFormaPagamento()
            	});  
            	armazenaClienteFisico = pedido.getIdClienteFisico();
            	System.out.println("\nDentro da funcao: " + pedido.getIdClienteFisico());
        	}
        }
	}	

	public void lerTabelaItensVendidos(int linhaSelecionada) {
		
        DefaultTableModel modelo = (DefaultTableModel) tableLivrosDoPedido.getModel();
        modelo.setNumRows(0);
    	PedidoDAO pedidoDAO = new PedidoDAO();
        
    	for (ItensPedidos itensPedidos : pedidoDAO.ConsultarItem(Integer.parseInt(String.valueOf(tableVendas.getValueAt(linhaSelecionada, 0))))) {

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
	public TelaPedidos() {

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				/**/
				lerTabelaVendas();
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

		/*--------------------------------------------------Painel edição e alteração de vendas------------------------------------------------------------------*/
		
		panelVendas = new JPanel();
		panelVendas.setBackground(new Color(51, 51, 51));
		panelVendas.setBounds(161, 0, 840, 643);
		contentPane.add(panelVendas);
			
		JButton btnCaixa = new JButton("Caixa");
		btnCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCaixa telaCaixa = new TelaCaixa();
				telaCaixa.setVisible(true);
				telaCaixa.setLocationRelativeTo(null);	
				telaCaixa.setExtendedState(MAXIMIZED_BOTH);
				dispose();				
			}
		});
		panelVendas.setLayout(null);
		btnCaixa.setFont(new Font("Lucida Sans", Font.PLAIN, 14));
		btnCaixa.setBounds(694, 11, 131, 22);
		btnCaixa.setBorder(null);
		btnCaixa.setForeground(new Color(255, 255, 255));
		btnCaixa.setBackground(new Color(51, 51, 51));
		btnCaixa.setContentAreaFilled(false);
		panelVendas.add(btnCaixa);
				
		JLabel lblBarraCadastrarVenda = new JLabel("");
		lblBarraCadastrarVenda.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraCadastrarVenda.setBounds(723, 32, 70, 1);
		panelVendas.add(lblBarraCadastrarVenda);
		
		JScrollPane scrollPaneTabelaVendas = new JScrollPane();
		scrollPaneTabelaVendas.setBounds(20, 44, 810, 302);
		panelVendas.add(scrollPaneTabelaVendas);
		
		tableVendas = new JTable();
		tableVendas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				PedidoDAO pedidoDAO = new PedidoDAO();
				Pedido pedido = new Pedido();
				ClienteFisicoDAO clienteFisicoDAO = new ClienteFisicoDAO();
				ClienteJuridicoDAO clienteJuridicoDAO = new ClienteJuridicoDAO();
				ClienteFisico clienteFisico = new ClienteFisico();
				ClienteJuridico clienteJuridico = new ClienteJuridico();
				ItensPedidos itensPedidos = new ItensPedidos ();
				Endereco endereco = new Endereco();
				armazenaLinhaSelecionadaTabelaPedidos = tableVendas.getSelectedRow();
				Object[] opcoes = { "Editar", "Excluir"};	
				String opcao = (String) JOptionPane.showInputDialog(null,
														"Escolha uma opção", 
														"Opçao",
														JOptionPane.INFORMATION_MESSAGE, 
														null, 
														opcoes, 
														opcoes[0]);		
				if(opcao.equals("Editar")) {
					panelAlteracaoDeVendas.setVisible(true);
					lerTabelaItensVendidos(armazenaLinhaSelecionadaTabelaPedidos);
					pedido.setId(Integer.parseInt(String.valueOf(tableVendas.getValueAt(armazenaLinhaSelecionadaTabelaPedidos, 0))));
					pedidoDAO.Consultar(pedido);
					
					if(pedido.getIdClienteJuridico() == 1){
						clienteFisico.setId(pedido.getIdClienteFisico());
						clienteFisicoDAO.Consultar(clienteFisico, endereco);
						consultarVenda(clienteFisico, null, pedido);
					}
					else {
						clienteJuridico.setId(pedido.getIdClienteJuridico());
						clienteJuridicoDAO.Consultar(clienteJuridico, endereco);
						System.out.println(clienteJuridico.getNome());
						consultarVenda(null, clienteJuridico, pedido);					
					}
				}
				else if(opcao.equals("Excluir")) {
	
					pedidoDAO.Deletar(Integer.parseInt(String.valueOf(tableVendas.getValueAt(armazenaLinhaSelecionadaTabelaPedidos, 0))));
					System.out.println(Integer.parseInt(String.valueOf(tableVendas.getValueAt(armazenaLinhaSelecionadaTabelaPedidos, 0))));
				}
			}
		});
		tableVendas.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		tableVendas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Cliente", "Total", "Data do pedido","Funcionario", "Forma de pagamento"
			}
		));

		scrollPaneTabelaVendas.setViewportView(tableVendas);
		
		JButton btnPanelVendasVoltar = new JButton("");
		btnPanelVendasVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//limparCamposFlagsVendas();			
			}
		});
		btnPanelVendasVoltar.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/Imagens/imgInicioVoltar.png")));
		btnPanelVendasVoltar.setForeground(new Color(255, 255, 255));
		btnPanelVendasVoltar.setBackground(new Color(51, 51, 51));
		btnPanelVendasVoltar.setContentAreaFilled(false);
		btnPanelVendasVoltar.setBorder(null);
		btnPanelVendasVoltar.setBounds(10, 5, 64, 28);
		panelVendas.add(btnPanelVendasVoltar);		

		panelAlteracaoDeVendas = new JPanel();
		panelAlteracaoDeVendas.setBorder(null);
		panelAlteracaoDeVendas.setBackground(Color.DARK_GRAY);
		panelAlteracaoDeVendas.setBounds(20, 357, 810, 286);
		panelVendas.add(panelAlteracaoDeVendas);
		panelAlteracaoDeVendas.setLayout(null);
		
		JScrollPane scrollPaneLivrosDoPedido = new JScrollPane();
		scrollPaneLivrosDoPedido.setBounds(10, 11, 248, 264);
		panelAlteracaoDeVendas.add(scrollPaneLivrosDoPedido);
		
		tableLivrosDoPedido = new JTable();
		tableLivrosDoPedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				armazenaLinhaSelecionadaTabelaItensPedidos  = tableLivrosDoPedido.getSelectedRow();
				//Integer.parseInt(String.valueOf(tableVendas.getValueAt(selecionado, 0)))
				armazenaIdLivro = Integer.parseInt((String.valueOf(tableLivrosDoPedido.getValueAt(armazenaLinhaSelecionadaTabelaItensPedidos , 0))));
				textFieldTitulo.setText(String.valueOf(tableLivrosDoPedido.getValueAt(armazenaLinhaSelecionadaTabelaItensPedidos , 1)));
				spinner.setValue(Integer.parseInt((String.valueOf(tableLivrosDoPedido.getValueAt(armazenaLinhaSelecionadaTabelaItensPedidos, 2)))));				
			}
		});
		tableLivrosDoPedido.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Título", "Quantidade"
			}
		));
		scrollPaneLivrosDoPedido.setViewportView(tableLivrosDoPedido);
		
		JLabel lblNomeCliente = new JLabel("Cliente:");
		lblNomeCliente.setForeground(Color.WHITE);
		lblNomeCliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblNomeCliente.setBounds(268, 18, 89, 14);
		panelAlteracaoDeVendas.add(lblNomeCliente);
		
		JLabel lblCPFCNPJ = new JLabel("CPF/CNPJ:");
		lblCPFCNPJ.setForeground(Color.WHITE);
		lblCPFCNPJ.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblCPFCNPJ.setBounds(268, 52, 89, 14);
		panelAlteracaoDeVendas.add(lblCPFCNPJ);
		
		JLabel lblLblIdcliente = new JLabel("ID do Cliente:");
		lblLblIdcliente.setForeground(Color.WHITE);
		lblLblIdcliente.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblLblIdcliente.setBounds(477, 52, 127, 14);
		panelAlteracaoDeVendas.add(lblLblIdcliente);
		
		JLabel lblIdDoPedido = new JLabel("ID do Pedido:");
		lblIdDoPedido.setForeground(Color.WHITE);
		lblIdDoPedido.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblIdDoPedido.setBounds(649, 52, 127, 14);
		panelAlteracaoDeVendas.add(lblIdDoPedido);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de pagamento:");
		lblFormaDePagamento.setForeground(Color.WHITE);
		lblFormaDePagamento.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblFormaDePagamento.setBounds(268, 87, 152, 14);
		panelAlteracaoDeVendas.add(lblFormaDePagamento);
		
		JLabel lblTotalDaVenda = new JLabel("Total da venda:   R$");
		lblTotalDaVenda.setForeground(Color.WHITE);
		lblTotalDaVenda.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblTotalDaVenda.setBounds(541, 87, 191, 14);
		panelAlteracaoDeVendas.add(lblTotalDaVenda);
		
		JLabel lblDataDaVenda = new JLabel("Data da venda:");
		lblDataDaVenda.setForeground(Color.WHITE);
		lblDataDaVenda.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblDataDaVenda.setBounds(268, 118, 127, 14);
		panelAlteracaoDeVendas.add(lblDataDaVenda);
		
		JLabel lblFuncionrioResponsvel = new JLabel("Funcion\u00E1rio respons\u00E1vel:");
		lblFuncionrioResponsvel.setForeground(Color.WHITE);
		lblFuncionrioResponsvel.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblFuncionrioResponsvel.setBounds(462, 118, 200, 14);
		panelAlteracaoDeVendas.add(lblFuncionrioResponsvel);
		panelAlteracaoDeVendas.setVisible(false);
		
		textFieldCliente = new JTextField();
		textFieldCliente.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldCliente.setForeground(Color.WHITE);
		textFieldCliente.setBackground(Color.DARK_GRAY);
		textFieldCliente.setBounds(336, 15, 464, 20);
		textFieldCliente.setBorder(null);
		panelAlteracaoDeVendas.add(textFieldCliente);
		textFieldCliente.setColumns(10);
		
		textFieldCPFCNPJ = new JTextField();
		textFieldCPFCNPJ.setForeground(Color.WHITE);
		textFieldCPFCNPJ.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldCPFCNPJ.setColumns(10);
		textFieldCPFCNPJ.setBorder(null);
		textFieldCPFCNPJ.setBackground(Color.DARK_GRAY);
		textFieldCPFCNPJ.setBounds(346, 49, 121, 20);
		panelAlteracaoDeVendas.add(textFieldCPFCNPJ);
		
		textFieldFormaPagameto = new JTextField();
		textFieldFormaPagameto.addMouseListener(new MouseAdapter() {
			 @Override
			public void mouseClicked(MouseEvent arg0) {
				 if (textFieldFormaPagameto.getText().equalsIgnoreCase("CRÉDITO")) {
					 textFieldFormaPagameto.setText("DÉBITO");
				 }
				 else {
					 textFieldFormaPagameto.setText("CRÉDITO");
				 }
			}
		});
		textFieldFormaPagameto.setForeground(Color.WHITE);
		textFieldFormaPagameto.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldFormaPagameto.setColumns(10);
		textFieldFormaPagameto.setBorder(new LineBorder(new Color(0, 102, 102), 2));
		textFieldFormaPagameto.setBackground(Color.DARK_GRAY);
		textFieldFormaPagameto.setBounds(424, 85, 108, 20);
		panelAlteracaoDeVendas.add(textFieldFormaPagameto);
		
		textFieldIdCliente = new JTextField();
		textFieldIdCliente.setForeground(Color.WHITE);
		textFieldIdCliente.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldIdCliente.setColumns(10);
		textFieldIdCliente.setBorder(null);
		textFieldIdCliente.setBackground(Color.DARK_GRAY);
		textFieldIdCliente.setBounds(593, 49, 47, 20);
		panelAlteracaoDeVendas.add(textFieldIdCliente);
		
		textFieldIdPedido = new JTextField();
		textFieldIdPedido.setForeground(Color.WHITE);
		textFieldIdPedido.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldIdPedido.setColumns(10);
		textFieldIdPedido.setBorder(null);
		textFieldIdPedido.setBackground(Color.DARK_GRAY);
		textFieldIdPedido.setBounds(753, 49, 47, 20);
		panelAlteracaoDeVendas.add(textFieldIdPedido);
		
		textFieldTotalVenda = new JTextField();
		textFieldTotalVenda.setForeground(Color.WHITE);
		textFieldTotalVenda.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldTotalVenda.setColumns(10);
		textFieldTotalVenda.setBorder(null);
		textFieldTotalVenda.setBackground(Color.DARK_GRAY);
		textFieldTotalVenda.setBounds(720, 84, 80, 20);
		panelAlteracaoDeVendas.add(textFieldTotalVenda);
		
		textFieldDataVenda = new JTextField();
		textFieldDataVenda.setForeground(Color.WHITE);
		textFieldDataVenda.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldDataVenda.setColumns(10);
		textFieldDataVenda.setBorder(null);
		textFieldDataVenda.setBackground(Color.DARK_GRAY);
		textFieldDataVenda.setBounds(385, 115, 67, 20);
		panelAlteracaoDeVendas.add(textFieldDataVenda);
		
		textFieldFuncionarioResponsavel = new JTextField();
		textFieldFuncionarioResponsavel.setForeground(Color.WHITE);
		textFieldFuncionarioResponsavel.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldFuncionarioResponsavel.setColumns(10);
		textFieldFuncionarioResponsavel.setBorder(null);
		textFieldFuncionarioResponsavel.setBackground(Color.DARK_GRAY);
		textFieldFuncionarioResponsavel.setBounds(660, 115, 140, 20);
		panelAlteracaoDeVendas.add(textFieldFuncionarioResponsavel);
		
		JLabel lblBarraDivisao = new JLabel("");
		lblBarraDivisao.setIcon(new ImageIcon(TelaPedidos.class.getResource("/Imagens/imgLinhaCadastro.jpg")));
		lblBarraDivisao.setBounds(268, 157, 532, 1);
		panelAlteracaoDeVendas.add(lblBarraDivisao);
		
		JLabel lblTitulo = new JLabel("T\u00EDtulo:");
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblTitulo.setBounds(268, 169, 67, 14);
		panelAlteracaoDeVendas.add(lblTitulo);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setForeground(Color.WHITE);
		textFieldTitulo.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBorder(null);
		textFieldTitulo.setBackground(Color.DARK_GRAY);
		textFieldTitulo.setBounds(336, 166, 84, 20);
		panelAlteracaoDeVendas.add(textFieldTitulo);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setForeground(Color.WHITE);
		lblQuantidade.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblQuantidade.setBounds(424, 169, 97, 14);
		panelAlteracaoDeVendas.add(lblQuantidade);
		
		spinner = new JSpinner();
		spinner.setBackground(Color.DARK_GRAY);
		spinner.setBounds(520, 166, 67, 20);
		panelAlteracaoDeVendas.add(spinner);
		
		JLabel lblAviso = new JLabel("");
		lblAviso.setForeground(Color.WHITE);
		lblAviso.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		lblAviso.setBounds(593, 169, 207, 106);
		lblAviso.setText("<html><body>Só é possível alterar ou excluir a quantidade de produtos e alterar a forma de pagamento! <br&gtcom HTML!</body></html>");
		panelAlteracaoDeVendas.add(lblAviso);
		
		JButton btnAtualizar = new JButton("");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PedidoDAO pedidoDAO = new PedidoDAO();
				Pedido pedido = new Pedido();
				ItensPedidos itensPedidos = new ItensPedidos ();
				String auxiliar = spinner.getValue().toString();
				
				itensPedidos.setIdLivro(armazenaIdLivro);
				itensPedidos.setIdPedido(Integer.parseInt(textFieldIdPedido.getText()));
				itensPedidos.setQuantidade(Integer.parseInt(auxiliar));
				
				pedidoDAO.AlterarItem(itensPedidos);
				
				pedido.setTotal(pedidoDAO.obterTotal(itensPedidos.getIdPedido()));
				pedido.setFormaPagamento(textFieldFormaPagameto.getText());
				pedido.setId(Integer.parseInt(textFieldIdPedido.getText()));
				
				pedidoDAO.AtualizarPedido(pedido);
				lerTabelaItensVendidos(armazenaLinhaSelecionadaTabelaPedidos);
				limparCampos();
			}
		});
		btnAtualizar.setIcon(new ImageIcon(TelaPedidos.class.getResource("/Imagens/imgCadastroAtualizacaoLivro.png")));
		btnAtualizar.setBounds(268, 197, 89, 78);
		btnAtualizar.setBorder(null);
		btnAtualizar.setForeground(new Color(255, 255, 255));
		btnAtualizar.setBackground(new Color(51, 51, 51));
		btnAtualizar.setContentAreaFilled(false);		
		panelAlteracaoDeVendas.add(btnAtualizar);
		
		JButton btnCancelar = new JButton("");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				panelAlteracaoDeVendas.setVisible(false);
				limparCampos();
			}
		});
		btnCancelar.setIcon(new ImageIcon(TelaPedidos.class.getResource("/Imagens/imgCancelarLivro.png")));
		btnCancelar.setBounds(378, 197, 89, 78);
		btnCancelar.setBorder(null);
		btnCancelar.setForeground(new Color(255, 255, 255));
		btnCancelar.setBackground(new Color(51, 51, 51));
		btnCancelar.setContentAreaFilled(false);
		panelAlteracaoDeVendas.add(btnCancelar);
					
	}
	
	void consultarVenda (ClienteFisico clienteFisico, ClienteJuridico clienteJuridico, Pedido pedido) {
		if(pedido.getIdClienteJuridico() == 1){
			textFieldCliente.setText(clienteFisico.getNome());
			textFieldCPFCNPJ.setText(clienteFisico.getCpf());
			textFieldIdCliente.setText(String.valueOf(pedido.getIdClienteFisico()));
			System.out.println("entrou f");
		}
		else if(pedido.getIdClienteFisico() == 1) {
			textFieldCliente.setText(clienteJuridico.getNome());
			System.out.println(clienteJuridico.getNome());
			textFieldCPFCNPJ.setText(clienteJuridico.getCnpj());
			textFieldIdCliente.setText(String.valueOf(pedido.getIdClienteJuridico()));			
			System.out.println("entrou j");
		}

		textFieldFormaPagameto.setText(pedido.getFormaPagamento());
		textFieldIdPedido.setText(String.valueOf(pedido.getId()));
		textFieldTotalVenda.setText(String.valueOf(pedido.getTotal()));
		textFieldDataVenda.setText(String.valueOf(pedido.getDataPedido()));
		textFieldFuncionarioResponsavel.setText(String.valueOf(pedido.getIdFuncionario()));
	}

	void limparCampos() {
		
		textFieldCliente.setText("");
		textFieldCPFCNPJ.setText("");
		textFieldFormaPagameto.setText("");
		textFieldIdCliente.setText("");
		textFieldIdPedido.setText("");
		textFieldTotalVenda.setText("");
		textFieldDataVenda.setText("");
		textFieldFuncionarioResponsavel.setText("");
		textFieldTitulo.setText("");
		
		spinner.setValue(0);
		
		flagClienteFisico = false;
		flagClienteJuridico = false;

		armazenaIdLivro = 0;
		armazenaClienteFisico = 0;
		armazenaClienteJuridico = 0;
		armazenaLinhaSelecionadaTabelaItensPedidos = 0;
		armazenaLinhaSelecionadaTabelaPedidos = 0;
		
		flagAtualizarVenda = false;
		flagCadastrarVenda = false;
		flagExcluirVenda = false;			
	}
	
}
