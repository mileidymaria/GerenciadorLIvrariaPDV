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

public class FuncionarioDAO {

	public void Cadastrar (Funcionario funcionario, Endereco endereco) {
		
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
			
			//Funcionario
			sql = "INSERT INTO tb_funcionario (nome, cpf, celular, email, senha, cargo, funcionario_desde, id_endereco,data_de_nascimento, status) VALUES(?,?,?,?,?,?,now(),?,?,true)";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getCelular());
			stmt.setString(4, funcionario.getEmail());
			stmt.setString(5, funcionario.getSenha());
			stmt.setString(6, funcionario.getCargo());
			stmt.setInt(7, RetornarIdEndereco ());
			stmt.setString(8, funcionario.getDataCriacao());
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
	
	
	public void Atualizar (Funcionario funcionario, Endereco endereco) {
		
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
			stmt.setInt(7, endereco.getId());
            stmt.execute();
            stmt.close();   
			
			//Funcionario
			sql = "UPDATE tb_funcionario SET nome=?, cpf=?, celular=?, email=?, senha=?, cargo=?, id_endereco=?, status=true, data_de_nascimento=? where id_tb_funcionario = ?";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, funcionario.getNome());
			stmt.setString(2, funcionario.getCpf());
			stmt.setString(3, funcionario.getCelular());
			stmt.setString(4, funcionario.getEmail());
			stmt.setString(5, funcionario.getSenha());
			stmt.setString(6, funcionario.getCargo());
			stmt.setInt(7, funcionario.getIdEndereco());
			stmt.setString(8, funcionario.getDataCriacao());
			stmt.setInt(9, funcionario.getId());
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

	public void Deletar (int idFuncionario) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "UPDATE tb_funcionario SET status = false where id_tb_funcionario = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idFuncionario);
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(null, "Funcionário excluído!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}	
	
	public void Consultar (Funcionario funcionario, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_funcionario where id_tb_funcionario = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, funcionario.getId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				funcionario.setNome(rs.getString("nome"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setCelular(rs.getString("celular"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setDataCadastro(rs.getString("funcionario_desde"));
				funcionario.setDataCriacao(rs.getString("data_de_nascimento"));
				funcionario.setCargo(rs.getString("cargo"));
				funcionario.setSenha(rs.getString("senha"));
				funcionario.setStatus(rs.getBoolean("status"));
				funcionario.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, funcionario.getIdEndereco());
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
	
	public List<Funcionario> ListarFuncionariosAtivos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Funcionario> funcionarios = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_funcionario where status = true");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario ();
				funcionario.setNome(rs.getString("nome"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setCelular(rs.getString("celular"));
				funcionario.setDataCriacao(rs.getString("data_de_nascimento"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setIdEndereco(rs.getInt("id_endereco"));
				funcionario.setId(rs.getInt("id_tb_funcionario"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return funcionarios;
	
	}
	
	public List<Funcionario> ListarFuncionariosInativos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Funcionario> funcionarios = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_funcionario where status = false");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario ();
				funcionario.setNome(rs.getString("razao_social"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setIdEndereco(rs.getInt("id_endereco"));
				funcionario.setId(rs.getInt("id_tb_funcionario"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return funcionarios;
	
	}

	public List<Funcionario> ListarFuncionariosRelatorio (String parametroConsulta) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Funcionario> funcionarios = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_funcionario where nome = ? or cpf = ? or email = ? or id_tb_funcionario  = ?");
			stmt.setString(1, parametroConsulta);
			stmt.setString(2, parametroConsulta);
			stmt.setString(3, parametroConsulta);
			stmt.setInt(4, Integer.parseInt(parametroConsulta));
			rs = stmt.executeQuery();
			while (rs.next()) {
				Funcionario funcionario = new Funcionario ();
				funcionario.setNome(rs.getString("nome"));
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setCelular(rs.getString("celular"));
				funcionario.setDataCriacao(rs.getString("data_de_nascimento"));
				funcionario.setStatus(rs.getBoolean("status"));
				funcionario.setEmail(rs.getString("email"));
				funcionario.setDataCadastro(rs.getString("funcionario_desde"));
				funcionario.setIdEndereco(rs.getInt("id_endereco"));
				funcionario.setId(rs.getInt("id_tb_funcionario"));
				funcionarios.add(funcionario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return funcionarios;
	
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
}
