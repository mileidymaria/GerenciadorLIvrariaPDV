package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.*;

public class FornecedorDAO {

	public void Cadastrar (Fornecedor fornecedor, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		try {
	    	
			//Endereço
			String sql = "INSERT INTO tb_endereco (rua, bairro, cidade, estado, cep, numero) VALUES(?,?,?,?,?,?)";
			PreparedStatement stmt = conexao.prepareCall(sql);
			stmt.setString(1, endereco.getRua());
			stmt.setString(2, endereco.getBairro());
			stmt.setString(3, endereco.getCidade());
			stmt.setString(4, endereco.getEstado());
			stmt.setString(5, endereco.getCep());
			stmt.setInt(6, endereco.getNumero());
            stmt.execute();
            stmt.close();  
			
			//Fornecedor
			sql = "INSERT INTO tb_fornecedor (nome, cpf, cnpj, email, telefone, id_endereco,data_criacao, fornecedor_desde, status) VALUES(?,?,?,?,?,?,?,now(),true)";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, fornecedor.getNome());
			stmt.setString(2, fornecedor.getCpf());
			stmt.setString(3, fornecedor.getCnpj());
			stmt.setString(4, fornecedor.getCelular());
			stmt.setString(5, fornecedor.getEmail());
			stmt.setInt(6, RetornarIdEndereco ());
			stmt.setString(7, fornecedor.getDataCriacao());
            stmt.execute();
            stmt.close();           
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
		}
		
		catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Não foi possível cadastrar cliente!");
		} 
		
	}
	
	
	public void Atualizar (Fornecedor fornecedor, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		try {
	    	
			//Endereço
			String sql = "UPDATE tb_endereco SET rua=?, bairro=?, cidade=?,estado=?,cep=?,numero=? WHERE id_endereco = ?";
			PreparedStatement stmt = conexao.prepareCall(sql);
			stmt.setString(1, endereco.getRua());
			stmt.setString(2, endereco.getBairro());
			stmt.setString(3, endereco.getCidade());
			stmt.setString(4, endereco.getEstado());
			stmt.setString(5, endereco.getCep());
			stmt.setInt(6, endereco.getNumero());
			stmt.setInt(7, fornecedor.getIdEndereco());
            stmt.execute();
            stmt.close();   
			
			//Fornecedor
			sql = "UPDATE tb_fornecedor SET nome=?, cpf=?, cnpj=?, email=?, telefone=?, id_endereco=?, data_criacao = ?  where id_fornecedor = ?";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, fornecedor.getNome());
			stmt.setString(2, fornecedor.getCpf());
			stmt.setString(3, fornecedor.getCpf());
			stmt.setString(4, fornecedor.getEmail());
			stmt.setString(5, fornecedor.getCelular());			
			stmt.setInt(6, fornecedor.getIdEndereco());
			stmt.setString(7, fornecedor.getDataCriacao());
			stmt.setInt(8, fornecedor.getId());
            stmt.execute();
            stmt.close();           
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
		}
		
		catch(SQLException ex) {
			ex.printStackTrace();
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Não foi possível cadastrar cliente!");
		} 
		
	}

	public void Deletar (int idFornecedor) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "UPDATE tb_fornecedor SET status = false where id_fornecedor = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idFornecedor);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void Consultar (Fornecedor fornecedor, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_fornecedor where id_fornecedor = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, fornecedor.getId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setCelular(rs.getString("telefone"));
				fornecedor.setCpf(rs.getString("cpf"));
				fornecedor.setStatus(rs.getBoolean("status"));
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setDataCadastro(rs.getString("fornecedor_desde"));
				fornecedor.setDataCriacao(rs.getString("data_criacao"));
				fornecedor.setStatus(rs.getBoolean("status"));
				fornecedor.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, fornecedor.getIdEndereco());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				endereco.setRua(rs.getString("rua"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setEstado(rs.getString("estado"));
				endereco.setCep(rs.getString("cep"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setId(rs.getInt("id_endereco"));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}	
	
	public List<Fornecedor> ListarFornecedoresAtivos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Fornecedor> fornecedores = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_fornecedor where status = true");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor ();
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setCpf(rs.getString("cpf"));
				fornecedor.setCnpj(rs.getString("cnpj"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setCelular(rs.getString("telefone"));
				fornecedor.setIdEndereco(rs.getInt("id_endereco"));
				fornecedor.setDataCriacao(rs.getString("data_criacao"));
				fornecedor.setId(rs.getInt("id_fornecedor"));
				fornecedores.add(fornecedor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fornecedores;
	
	}
	
	public List<Fornecedor> ListarFornecedoresInativos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Fornecedor> fornecedores = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_fornecedor where status = false");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor ();
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setCpf(rs.getString("cpf"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setCelular(rs.getString("telefone"));
				fornecedor.setCpf(rs.getString("cnpj"));
				fornecedor.setDataCriacao(rs.getString("data_criacao"));
				fornecedor.setIdEndereco(rs.getInt("id_endereco"));
				fornecedor.setId(rs.getInt("id_fornecedor"));
				fornecedores.add(fornecedor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fornecedores;
	
	}
	
	public List<Fornecedor> ListarFornecedoresRelatorio (String parametroConsulta) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Fornecedor> fornecedores = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_fornecedor where id_fornecedor = ? or nome = ?");
			stmt.setInt(1, Integer.parseInt(parametroConsulta));
			stmt.setString(2, parametroConsulta);	
			System.out.println("\n\nentrou no relatar fornecedor\n\nstring consulta" + parametroConsulta);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor ();
				fornecedor.setNome(rs.getString("nome"));
				fornecedor.setCpf(rs.getString("cpf"));
				fornecedor.setStatus(rs.getBoolean("status"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setCelular(rs.getString("telefone"));
				fornecedor.setCpf(rs.getString("cnpj"));
				fornecedor.setDataCriacao(rs.getString("data_criacao"));
				fornecedor.setDataCadastro(rs.getString("fornecedor_desde"));
				fornecedor.setIdEndereco(rs.getInt("id_endereco"));
				fornecedor.setId(rs.getInt("id_fornecedor"));
				fornecedores.add(fornecedor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return fornecedores;
	
	}

	
	public int RetornarIdEndereco () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		ResultSet rs = null;
		Endereco endereco = new Endereco();		

		try {
			
			PreparedStatement stmt = conexao.prepareStatement("SELECT id_endereco FROM tb_endereco ");
			rs = stmt.executeQuery();					
			while (rs.next()) {				
				endereco.setId(rs.getInt("id_endereco"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(endereco.getId());
		return endereco.getId();
	
	}
	
	public int RetornarIdFornecedor (String nome) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		ResultSet rs = null;	
		int idFornecedor = 0;
		
		try {
			
			PreparedStatement stmt = conexao.prepareStatement("SELECT id_fornecedor FROM tb_fornecedor where nome = ? ");
			stmt.setString(1, nome);
			rs = stmt.executeQuery();					
			while (rs.next()) {				
				idFornecedor = rs.getInt("id_fornecedor");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return idFornecedor;
	
	}
	
	public String RetornarNomeFornecedor (int idFornecedor) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		ResultSet rs = null;	
		String nomeFornecedor = null;
		
		try {
			
			PreparedStatement stmt = conexao.prepareStatement("SELECT nome FROM tb_fornecedor where id_fornecedor = ? ");
			stmt.setInt(1, idFornecedor);
			rs = stmt.executeQuery();					
			while (rs.next()) {				
				nomeFornecedor = rs.getString("nome");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return nomeFornecedor;
	
	}	
	
}

