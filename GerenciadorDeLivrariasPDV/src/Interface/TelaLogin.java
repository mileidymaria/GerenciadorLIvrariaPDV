package Interface;



import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import DAO.*;
import Model.Funcionario;

public class TelaLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JPasswordField passwordField;
	private boolean flagPermissaoLogin;
	
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
					TelaLogin frame = new TelaLogin();
					frame.setResizable(false);
					frame.setUndecorated(true);
					frame.setLocationRelativeTo(null);
					frame.setSize(610, 365);
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
	public TelaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelImagem = new JPanel();
		panelImagem.setBounds(0, 0, 325, 361);
		contentPane.add(panelImagem);
		panelImagem.setLayout(null);
		
		JLabel lblImagem = new JLabel("");
		lblImagem.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/imgLogin.jpg")));
		lblImagem.setBounds(0, 0, 325, 361);
		panelImagem.add(lblImagem);
		
		JPanel panelLogin = new JPanel();
		panelLogin.setBackground(new Color(51, 51, 51));
		panelLogin.setBounds(324, 0, 310, 361);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		
		JLabel lblBarraLogin = new JLabel("");
		lblBarraLogin.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/imgBarras.jpg")));
		lblBarraLogin.setBounds(57, 173, 190, 1);
		panelLogin.add(lblBarraLogin);
		
		JLabel labelBarraSenha = new JLabel("");
		labelBarraSenha.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/imgBarras.jpg")));
		labelBarraSenha.setBounds(57, 216, 190, 1);
		panelLogin.add(labelBarraSenha);
		
		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				LoginDAO loginDAO = new LoginDAO();
				Funcionario funcionario = new Funcionario();
				
				try {
					funcionario.setEmail(textFieldLogin.getText());
					loginDAO.ConsultarPeloEmail (funcionario);
					flagPermissaoLogin = loginDAO.Logar(textFieldLogin.getText(), passwordField.getText());
					//Se o funcionário for to tipo ADM, ele terá acesso a toda as telas do sistemas, caso contrário ele só terá acesso ao caixa 

					if (flagPermissaoLogin == true) {
						if (funcionario.getCargo().equals("ADMINISTRADOR")) {
							TelaPrincipal telaPrincipal = new TelaPrincipal();
							telaPrincipal.setVisible(true);
							telaPrincipal.setLocationRelativeTo(null);										
							dispose();	
						}
						
						else {
							TelaCaixa telaCaixa = new TelaCaixa();
							telaCaixa.setVisible(true);
							telaCaixa.setLocationRelativeTo(null);	
							telaCaixa.setExtendedState(MAXIMIZED_BOTH);
							dispose();	
						}	
					}
					else {
						JOptionPane.showMessageDialog(null, "Usuário inválido! Tente novamente!");
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnLogar.setContentAreaFilled(false);
		btnLogar.setOpaque(true);
		btnLogar.setForeground(new Color(0, 0, 0));
		btnLogar.setBackground(new Color(102, 205, 170));
		btnLogar.setBounds(96, 255, 89, 23);
		panelLogin.add(btnLogar);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/imgIcon.png")));
		lblIcon.setBounds(96, 11, 89, 89);
		panelLogin.add(lblIcon);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setForeground(new Color(240, 248, 255));
		textFieldLogin.setBackground(new Color(51, 51, 51));
		textFieldLogin.setBounds(57, 152, 190, 20);
		textFieldLogin.setBorder(null);
		textFieldLogin.setOpaque(true);		
		panelLogin.add(textFieldLogin);
		textFieldLogin.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(new Color(240, 248, 255));
		passwordField.setBackground(new Color(51, 51, 51));
		passwordField.setBounds(57, 195, 190, 20);
		passwordField.setOpaque(true);
		passwordField.setBorder(null);
		panelLogin.add(passwordField);
	}
}
