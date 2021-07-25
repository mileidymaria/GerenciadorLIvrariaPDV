package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.ClienteJuridico;
import Model.Endereco;

public class ClienteJuridicoDAO {

	public void Cadastrar (ClienteJuridico clienteJuridico, Endereco endereco) {
		
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
			sql = "INSERT INTO tb_cliente_juridico (razao_social, data_criacao, cnpj, telefone, email, cliente_desde, id_endereco, status) VALUES(?,?,?,?,?,now(),?,true)";
			stmt = conexao.prepareCall(sql);
			System.out.println(clienteJuridico.getNome());
			stmt.setString(1, clienteJuridico.getNome());
			System.out.println(clienteJuridico.getDataCriacao());
			stmt.setString(2, clienteJuridico.getDataCriacao());
			stmt.setString(3, clienteJuridico.getCnpj());
			stmt.setString(4, clienteJuridico.getCelular());
			stmt.setString(5, clienteJuridico.getEmail());
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
	
	public void Atualizar (ClienteJuridico clienteJuridico, Endereco endereco) {
		
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
			stmt.setInt(7, clienteJuridico.getIdEndereco());
            stmt.execute();
            stmt.close();   
			
			//Cliente
			sql = "UPDATE tb_cliente_juridico SET razao_social=?, data_criacao=?, cnpj=?, telefone=?, email=?, id_endereco=?, status=? WHERE id_cliente_juridico = ?";
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, clienteJuridico.getNome());
			stmt.setString(2, clienteJuridico.getDataCriacao());
			stmt.setString(3, clienteJuridico.getCnpj());
			stmt.setString(4, clienteJuridico.getCelular());
			stmt.setString(5, clienteJuridico.getEmail());
			stmt.setInt(6, clienteJuridico.getIdEndereco());
			stmt.setBoolean(7, true);
			stmt.setInt(8, clienteJuridico.getId());
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

	public void Deletar (int idClienteJuridico) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "UPDATE tb_cliente_juridico SET status=false WHERE id_cliente_juridico = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idClienteJuridico);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}

	public void Consultar (ClienteJuridico clienteJuridico, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_cliente_juridico where id_cliente_juridico = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteJuridico.getId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				clienteJuridico.setNome(rs.getString("razao_social"));
				clienteJuridico.setEmail(rs.getString("email"));
				clienteJuridico.setCelular(rs.getString("telefone"));
				clienteJuridico.setCnpj(rs.getString("cnpj"));
				clienteJuridico.setDataCadastro(rs.getString("cliente_desde"));
				clienteJuridico.setDataCriacao(rs.getString("data_criacao"));
				clienteJuridico.setStatus(rs.getBoolean("status"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteJuridico.getId());
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

	public void ConsultarPeloCNPJ (ClienteJuridico clienteJuridico, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_cliente_juridico where cnpj = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, clienteJuridico.getCnpj());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				clienteJuridico.setNome(rs.getString("razao_social"));
				clienteJuridico.setEmail(rs.getString("email"));
				clienteJuridico.setCelular(rs.getString("telefone"));
				clienteJuridico.setCnpj(rs.getString("cnpj"));
				clienteJuridico.setDataCadastro(rs.getString("cliente_desde"));
				clienteJuridico.setDataCriacao(rs.getString("data_criacao"));
				clienteJuridico.setStatus(rs.getBoolean("status"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteJuridico.getId());
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

	public void ConsultarPelaRazaoSocial (ClienteJuridico clienteJuridico, Endereco endereco) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_cliente_juridico where razao_social = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, clienteJuridico.getNome());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				clienteJuridico.setId(rs.getInt("id_cliente_juridico"));
				clienteJuridico.setNome(rs.getString("razao_social"));
				clienteJuridico.setEmail(rs.getString("email"));
				clienteJuridico.setCelular(rs.getString("telefone"));
				clienteJuridico.setCnpj(rs.getString("cnpj"));
				clienteJuridico.setDataCadastro(rs.getString("cliente_desde"));
				clienteJuridico.setDataCriacao(rs.getString("data_criacao"));
				clienteJuridico.setStatus(rs.getBoolean("status"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sql = "Select * from tb_endereco where id_endereco = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, clienteJuridico.getId());
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

	
	public List<ClienteJuridico> ListarClientesAtivos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();


		ResultSet rs = null;
				
		List<ClienteJuridico> clientesJuridicos = new ArrayList();
		
		try {
			
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM tb_cliente_juridico where status = true");
			rs = stmt.executeQuery();		
			
			while (rs.next()) {
				ClienteJuridico clienteJuridico = new ClienteJuridico ();
				clienteJuridico.setNome(rs.getString("razao_social"));
				clienteJuridico.setCelular(rs.getString("telefone"));
				clienteJuridico.setEmail(rs.getString("email"));
				clienteJuridico.setCnpj(rs.getString("cnpj"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));
				clienteJuridico.setId(rs.getInt("id_cliente_juridico"));
				clientesJuridicos.add(clienteJuridico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientesJuridicos;
	
	}
	
	
	public List<ClienteJuridico> ListarClientesInativos () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ClienteJuridico> clientesJuridicos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_cliente_juridico where status = false");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				ClienteJuridico clienteJuridico = new ClienteJuridico ();
				clienteJuridico.setNome(rs.getString("nome"));
				clienteJuridico.setCnpj(rs.getString("cnpj"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));
				clienteJuridico.setId(rs.getInt("id_cliente_juridico"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));
				clientesJuridicos.add(clienteJuridico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientesJuridicos;
	
	}
	
	public List<ClienteJuridico> ListarClientesRelatorio (String parametroConsulta) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<ClienteJuridico> clientesJuridicos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_cliente_juridico where razao_social = ? or email = ? or cnpj = ? or id_cliente_juridico = ?");
			stmt.setString(1, parametroConsulta);
			stmt.setString(2, parametroConsulta);
			stmt.setString(3, parametroConsulta);
			stmt.setInt(4, Integer.parseInt(parametroConsulta));
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				ClienteJuridico clienteJuridico = new ClienteJuridico ();
				clienteJuridico.setNome(rs.getString("razao_social"));
				clienteJuridico.setCnpj(rs.getString("cnpj"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));
				clienteJuridico.setEmail(rs.getString("email"));
				clienteJuridico.setCelular(rs.getString("telefone"));
				clienteJuridico.setDataCadastro(rs.getString("data_criacao"));
				clienteJuridico.setStatus(rs.getBoolean("status"));
				clienteJuridico.setId(rs.getInt("id_cliente_juridico"));
				clienteJuridico.setIdEndereco(rs.getInt("id_endereco"));
				clientesJuridicos.add(clienteJuridico);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientesJuridicos;
	
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

	public int RetornarIdCliente (String cnpj) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		int idCliente = 0;
		
		String sql = "Select * from tb_cliente_juridico where cnpj = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, cnpj);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				idCliente = rs.getInt("id_cliente_juridico");

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
		
		String sql = "Select * from tb_cliente_juridico where id_cliente_juridico = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idCliente);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				nome = rs.getString("razao_social");

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nome;
			
	}		
	
}
