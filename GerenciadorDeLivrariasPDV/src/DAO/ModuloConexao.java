package DAO;


import java.sql.*;

//Data Acess Layer 
//Camada de acesso à dados

public class ModuloConexao {
	
	//metodo responsavel por estabelecer a conexao com o banco
	
	public static Connection ModuloConexao (){
	
		try { 
			
			return  DriverManager.getConnection("jdbc:mysql://localhost:3306/livraria", "root", ""); 
		
		} 
		
		catch (Exception erro) {
			
			throw new RuntimeException(erro);
		
		}
	}

}

