package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.*;

public class LivroDAO {

	//descricao, titulo, quantidade_estoque, quantidade_paginas, preco, isbn, editora, categoria, id_fornecedor
	
	
	public void Cadastrar (Livro livro) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		String sql = "INSERT INTO tb_livro (descricao, titulo, quantidade_estoque, quantidade_paginas, preco, isbn, editora, categoria, id_fornecedor) VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, livro.getDescricao());
			stmt.setString(2, livro.getTitulo());
			stmt.setInt(3, livro.getQuantidadeEstoque());
			stmt.setInt(4, livro.getQuantidadePaginas());
			stmt.setDouble(5, livro.getPreco());
			stmt.setString(6, livro.getIsbn());
			stmt.setString(7, livro.getEditora());
			stmt.setString(8, livro.getCategoria());
			stmt.setInt(9, livro.getIdFornecedor());
	        stmt.execute();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}

		
	}
	
	public void Atualizar (Livro livro) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		String sql = "UPDATE tb_livro SET descricao=?, titulo=?, quantidade_estoque=?, quantidade_paginas=?, preco=?, isbn=?, editora=?, categoria=?, id_fornecedor=? WHERE id_livro = ?";
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, livro.getDescricao());
			stmt.setString(2, livro.getTitulo());
			stmt.setInt(3, livro.getQuantidadeEstoque());
			stmt.setInt(4, livro.getQuantidadePaginas());
			stmt.setDouble(5, livro.getPreco());
			stmt.setString(6, livro.getIsbn());
			stmt.setString(7, livro.getEditora());
			stmt.setString(8, livro.getCategoria());
			stmt.setInt(9, livro.getIdFornecedor());
			stmt.setInt(10, livro.getId());
	        stmt.execute();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}

		
	}	

	public void AtualizarEstoque (int idLivro, int quantidade) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		String sql = "UPDATE tb_livro SET quantidade_estoque=? WHERE id_livro = ?";
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareCall(sql);
			stmt.setInt(1, RetornarQuantidadeProduto(idLivro)-quantidade);
			stmt.setInt(2, idLivro);
	        stmt.execute();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}

		
	}	
	
	
	public void Deletar (int idLivro) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "DELETE FROM tb_livro WHERE id_livro = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idLivro);
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(null, "Livro excluído com sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Livro não encontrado");
		}
	}
	
	public void Consultar (Livro livro) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_livro where id_livro = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, livro.getId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				livro.setTitulo(rs.getString("titulo"));
				livro.setDescricao(rs.getString("descricao"));
				livro.setCategoria(rs.getString("categoria"));
				livro.setEditora(rs.getString("editora"));
				livro.setIsbn(rs.getString("isbn"));
				livro.setPreco(rs.getFloat("preco"));
				livro.setQuantidadePaginas(rs.getInt("quantidade_paginas"));
				livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				livro.setIdFornecedor(rs.getInt("id_fornecedor"));
				livro.setId(rs.getInt("id_livro"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();		
		}
	}
	
	public int RetornarQuantidadeProduto (int idLivro) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		int quantidade = 0;
		
		String sql = "Select * from tb_livro where id_livro = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idLivro);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				quantidade = rs.getInt("quantidade_estoque");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();		
		}
		
		return quantidade;
	}
	
	public List<Livro> Listar () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Livro> livros = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_livro");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Livro livro = new Livro ();
				livro.setTitulo(rs.getString("titulo"));
				livro.setDescricao(rs.getString("descricao"));
				livro.setCategoria(rs.getString("categoria"));
				livro.setEditora(rs.getString("editora"));
				livro.setIsbn(rs.getString("isbn"));
				livro.setPreco(rs.getFloat("preco"));
				livro.setQuantidadePaginas(rs.getInt("quantidade_paginas"));
				livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				livro.setIdFornecedor(rs.getInt("id_fornecedor"));
				livro.setId(rs.getInt("id_livro"));
				livros.add(livro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return livros;
	
	}	

	public List<Livro> ListarLivrosRelatorio (String parametroConsulta) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Livro> livros = new ArrayList();
		
		try {
			//id, título, ISBN ou editora
			stmt = conexao.prepareStatement("SELECT * FROM tb_livro where titulo = ? or isbn = ? or editora = ? or categoria = ? or id_livro = ?");
			stmt.setString(1, parametroConsulta);
			stmt.setString(2, parametroConsulta);
			stmt.setString(3, parametroConsulta);
			stmt.setString(4, parametroConsulta);
			stmt.setInt(5, Integer.parseInt(parametroConsulta));
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.println("\n\n\nentrou");
				Livro livro = new Livro ();
				livro.setTitulo(rs.getString("titulo"));
				livro.setDescricao(rs.getString("descricao"));
				livro.setCategoria(rs.getString("categoria"));
				livro.setEditora(rs.getString("editora"));
				livro.setIsbn(rs.getString("isbn"));
				livro.setPreco(rs.getFloat("preco"));
				livro.setQuantidadePaginas(rs.getInt("quantidade_paginas"));
				livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				livro.setIdFornecedor(rs.getInt("id_fornecedor"));
				livro.setId(rs.getInt("id_livro"));
				livros.add(livro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return livros;
	
	}	
	
}	
	


