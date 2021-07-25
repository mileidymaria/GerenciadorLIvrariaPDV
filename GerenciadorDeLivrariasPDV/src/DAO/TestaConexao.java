package DAO;

import javax.swing.JOptionPane;

public class TestaConexao {

	public TestaConexao () {
		
	}
	
	public static void main(String[] args) {
		try {
			new ModuloConexao().ModuloConexao();
			JOptionPane.showMessageDialog(null, "Conectado com sucesso!");
		}
		catch (Exception erro){
			JOptionPane.showMessageDialog(null, "Conexão não estabelecida!" + erro);
		}

	}

}

