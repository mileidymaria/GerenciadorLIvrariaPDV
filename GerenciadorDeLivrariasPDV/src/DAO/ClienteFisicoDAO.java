package DAO;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import javax.swing.JOptionPane;

import Model.*;

public class ClienteFisicoDAO {

	// DAO Cliente Físico
	
	public void Cadastrar (ClienteFisico clienteFisico, Endereco endereco) {
		
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
			
			
			//Cliente
			sql = "INSERT INTO tb_cliente_fisico (nome, data_de_nascimento, cpf, celular, email, cliente_desde, id_endereco, status) VALUES(?,?,?,?,?,now(),?,true)";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, clienteFisico.getNome());
			stmt.setString(2, clienteFisico.getDataCriacao());
			stmt.setString(3, clienteFisico.getCpf());
			stmt.setString(4, clienteFisico.getCelular());
			stmt.setString(5, clienteFisico.getEmail());
			stmt.setInt(6, RetornarIdEndereco());
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
	
	public void Atualizar (ClienteFisico clienteFisico, Endereco endereco) {
		
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
			stmt.setInt(7, clienteFisico.getIdEndereco());
            stmt.execute();
            stmt.close();  
			
			//Cliente
			sql = "UPDATE tb_cliente_fisico SET nome=?, data_de_nascimento=?, cpf=?, celular=?, email=?, id_endereco=?, status=? WHERE id_cliente_fisico = ?";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, clienteFisico.getNome());
			stmt.setString(2, clienteFisico.getDataCriacao());
			stmt.setString(3, clienteFisico.getCpf());
			stmt.setString(4, clienteFisico.getCelular());
			stmt.setString(5, clienteFisico.getEmail());
			stmt.setInt(6, clienteFisico.getIdEndereco());
			stmt.setBoolean(7, true);
			stmt.setInt(8, clienteFisico.getId());
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

	public void Deletar (int idClienteFisico) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "UPDATE tb_cliente_fisico SET status=false WHERE id_cliente_fisico = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idClienteFisico);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void Consultar (ClienteFisico clienteFisico, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_cliente_fisico where id_cliente_Fisico = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteFisico.getId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				clienteFisico.setNome(rs.getString("nome"));
				clienteFisico.setEmail(rs.getString("email"));
				clienteFisico.setCelular(rs.getString("celular"));
				clienteFisico.setCpf(rs.getString("cpf"));
				clienteFisico.setDataCadastro(rs.getString("cliente_desde"));
				clienteFisico.setDataCriacao(rs.getString("data_de_nascimento"));
				clienteFisico.setStatus(rs.getBoolean("status"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteFisico.getId());
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
	
	public void ConsultarPeloCPF (ClienteFisico clienteFisico, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_cliente_fisico where cpf = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, clienteFisico.getCpf());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				clienteFisico.setNome(rs.getString("nome"));
				clienteFisico.setEmail(rs.getString("email"));
				clienteFisico.setCelular(rs.getString("celular"));
				clienteFisico.setCpf(rs.getString("cpf"));
				clienteFisico.setDataCadastro(rs.getString("cliente_desde"));
				clienteFisico.setDataCriacao(rs.getString("data_de_nascimento"));
				clienteFisico.setStatus(rs.getBoolean("status"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteFisico.getId());
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

	public void ConsultarPeloNome (ClienteFisico clienteFisico, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_cliente_fisico where nome = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, clienteFisico.getNome());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				clienteFisico.setNome(rs.getString("nome"));
				clienteFisico.setEmail(rs.getString("email"));
				clienteFisico.setCelular(rs.getString("celular"));
				clienteFisico.setCpf(rs.getString("cpf"));
				clienteFisico.setId(rs.getInt("id_cliente_fisico"));
				clienteFisico.setDataCadastro(rs.getString("cliente_desde"));
				clienteFisico.setDataCriacao(rs.getString("data_de_nascimento"));
				clienteFisico.setStatus(rs.getBoolean("status"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteFisico.getId());
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
	
	
	
	public List<ClienteFisico> ListarClienteAtivos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ClienteFisico> clientesFisicos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_cliente_fisico where status = true");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				ClienteFisico clienteFisico = new ClienteFisico ();
				clienteFisico.setNome(rs.getString("nome"));
				clienteFisico.setCpf(rs.getString("cpf"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));
				clienteFisico.setId(rs.getInt("id_cliente_fisico"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));
				clienteFisico.setEmail(rs.getString("email"));
				clienteFisico.setCelular(rs.getString("celular"));
				clientesFisicos.add(clienteFisico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientesFisicos;
	
	}	
	
	public List<ClienteFisico> ListarClientesInativos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ClienteFisico> clientesFisicos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_cliente_fisico where status = false");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				ClienteFisico clienteFisico = new ClienteFisico ();
				clienteFisico.setNome(rs.getString("nome"));
				clienteFisico.setCpf(rs.getString("cpf"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));
				clienteFisico.setEmail(rs.getString("email"));
				clienteFisico.setCelular(rs.getString("celular"));
				clienteFisico.setId(rs.getInt("id_cliente_fisico"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));
				clientesFisicos.add(clienteFisico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientesFisicos;
	
	}		

	public List<ClienteFisico> ListarClientesRelatorio (String parametroConsulta) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ClienteFisico> clientesFisicos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_cliente_fisico where nome = ? or cpf = ? or id_cliente_fisico = ? or email=?");
			stmt.setString(1, parametroConsulta);
			stmt.setString(2, parametroConsulta);
			stmt.setInt(3, Integer.parseInt(parametroConsulta));
			stmt.setString(4, parametroConsulta);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				ClienteFisico clienteFisico = new ClienteFisico ();
				clienteFisico.setNome(rs.getString("nome"));
				clienteFisico.setCpf(rs.getString("cpf"));
				clienteFisico.setIdEndereco(rs.getInt("id_endereco"));
				clienteFisico.setEmail(rs.getString("email"));
				clienteFisico.setCelular(rs.getString("celular"));
				clienteFisico.setId(rs.getInt("id_cliente_fisico"));
				clienteFisico.setStatus(rs.getBoolean("status"));
				clientesFisicos.add(clienteFisico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientesFisicos;
	
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

	public int RetornarIdCliente (String cpf) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		int idCliente = 0;
		
		String sql = "Select * from tb_cliente_fisico where cpf = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, cpf);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				idCliente = rs.getInt("id_cliente_fisico");

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idCliente;
			
	}
	
	public String RetornarNomeCliente (int idCliente) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		String nome = null;
		
		String sql = "Select * from tb_cliente_fisico where id_cliente_fisico = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				nome = rs.getString("nome");

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nome;
			
	}	
}
