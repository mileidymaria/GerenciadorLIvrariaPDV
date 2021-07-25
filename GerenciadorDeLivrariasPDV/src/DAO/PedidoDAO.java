package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.*;

public class PedidoDAO {

	//id_cliente_juridico, id_cliente_fisico, id_funcionario, forma_pagamento, total
	
	public void Cadastrar (Pedido pedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		String sql = "INSERT INTO tb_pedido (id_cliente_juridico, id_cliente_fisico, id_funcionario, data, forma_pagamento, total) VALUES(?,?,?,now(),?,?)";
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareCall(sql);
			stmt.setInt(1, pedido.getIdClienteJuridico());
			stmt.setInt(2, pedido.getIdClienteFisico());
			stmt.setInt(3, pedido.getIdFuncionario());
			stmt.setString(4, pedido.getFormaPagamento());
			stmt.setDouble(5, pedido.getTotal());
	        stmt.execute();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void Atualizar (Pedido pedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		String sql = "UPDATE tb_livro SET id_cliente_juridico=?, id_cliente_fisico=?, id_funcionario=?, forma_pagamento=?, total=? WHERE id_pedido = ?";
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareCall(sql);
			stmt.setInt(1, pedido.getIdClienteJuridico());
			stmt.setInt(2, pedido.getIdClienteFisico());
			stmt.setInt(3, pedido.getIdFuncionario());
			stmt.setString(4, pedido.getFormaPagamento());
			stmt.setDouble(5, pedido.getTotal());
			stmt.setInt(6, pedido.getId());
	        stmt.execute();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void AtualizarPedido (Pedido pedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		String sql = "UPDATE tb_pedido SET forma_pagamento=?, total=? WHERE id_pedido = ?";
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareCall(sql);
			stmt.setString(1, pedido.getFormaPagamento());
			stmt.setDouble(2, pedido.getTotal());
			stmt.setInt(3, pedido.getId());
	        stmt.execute();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void Deletar (int idPedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "DELETE FROM tb_pedido WHERE id_pedido = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idPedido);
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(null, "Pedido excluído com sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Pedido não encontrado");
		}
	}	
	
	
	public void Consultar (Pedido pedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		
		String sql = "Select * from tb_pedido where id_pedido = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, pedido.getId());
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				pedido.setIdClienteFisico(rs.getInt("id_cliente_fisico"));
				pedido.setIdClienteJuridico(rs.getInt("id_cliente_juridico"));
				pedido.setIdFuncionario(rs.getInt("id_funcionario"));
				pedido.setDataPedido(rs.getDate("data"));
				pedido.setFormaPagamento(rs.getString("forma_pagamento"));
				pedido.setTotal(rs.getDouble("total"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		}
	}
	
	
	public List<Pedido> Listar () {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Pedido> pedidos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("Select * from tb_pedido");
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setIdClienteFisico(rs.getInt("id_cliente_fisico"));
				pedido.setIdClienteJuridico(rs.getInt("id_cliente_juridico"));
				pedido.setIdFuncionario(rs.getInt("id_funcionario"));
				pedido.setDataPedido(rs.getDate("data"));
				pedido.setFormaPagamento(rs.getString("forma_pagamento"));
				pedido.setTotal(rs.getDouble("total"));
				pedido.setId(rs.getInt("id_pedido"));
				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pedidos;
	
	}		

	public List<Pedido> ListarPedidosRelatorio (String parametroConsulta) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Pedido> pedidos = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("Select * from tb_pedido where cliente = ? or funcionario = ? or cliente = ?");
			stmt.setString(1, parametroConsulta);
			stmt.setString(2, parametroConsulta);
			stmt.setString(3, parametroConsulta);			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setIdClienteFisico(rs.getInt("id_cliente_fisico"));
				pedido.setIdClienteJuridico(rs.getInt("id_cliente_juridico"));
				pedido.setIdFuncionario(rs.getInt("id_funcionario"));
				pedido.setDataPedido(rs.getDate("data"));
				pedido.setFormaPagamento(rs.getString("forma_pagamento"));
				pedido.setTotal(rs.getDouble("total"));
				pedido.setId(rs.getInt("id_pedido"));
				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pedidos;
	
	}	
	
	public int RetornarIdPedido () { //retorna o último pedido cadastrado no sistema
		
		Connection conexao = ModuloConexao.ModuloConexao();
		ResultSet rs = null;
		int idPedido = 0;		

		try {
			
			PreparedStatement stmt = conexao.prepareStatement("SELECT id_pedido FROM tb_pedido ");
			rs = stmt.executeQuery();					
			while (rs.next()) {				
				idPedido = rs.getInt("id_pedido");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println(idPedido);
		return idPedido;
	
	}	
	
	
// ITENS DO PEDIDO

	public void CadastrarItem (ItensPedidos itensPedidos) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		
		
		
		try {
			String sql = "INSERT INTO tb_item_pedido (id_pedido, id_livro, quantidade) VALUES(?,?,?)";
			PreparedStatement stmt = conexao.prepareCall(sql);
			stmt = conexao.prepareCall(sql);					
			stmt.setInt(1, itensPedidos.getIdPedido());  
			System.out.println(itensPedidos.getIdPedido());
			stmt.setInt(2, itensPedidos.getIdLivro());
			stmt.setInt(3, itensPedidos.getQuantidade());
            stmt.execute();
            stmt.close(); 
		}
				
		catch (SQLException ex) {
					
			ex.printStackTrace();
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Não foi possível cadastrar livros do pedido!");
				
		}
				
				
	}	
	
	public void AlterarItem (ItensPedidos itensPedidos) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		
		try {
		
			String sql = "UPDATE tb_item_pedido SET quantidade=? WHERE id_livro = ? AND id_pedido = ? ";
		
			PreparedStatement stmt = conexao.prepareCall(sql);
			
			stmt.setInt(1, itensPedidos.getQuantidade());
			stmt.setInt(2, itensPedidos.getIdLivro());
			stmt.setInt(3, itensPedidos.getIdPedido());
            stmt.execute();
            stmt.close();  		
		}
		catch (SQLException ex) {
			
			ex.printStackTrace();
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Não foi possível atualizar pedido");
		
		}
		
	}	
	
	public List<ItensPedidos> ConsultarItem (int codVenda) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		List<ItensPedidos> livrosPedido = new ArrayList();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_item_pedido WHERE id_pedido = ?");
			stmt.setInt(1, codVenda);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				ItensPedidos itensPedidos = new ItensPedidos ();
				itensPedidos.setIdPedido(rs.getInt("id_pedido"));
				itensPedidos.setQuantidade(rs.getInt("quantidade"));
				itensPedidos.setIdLivro(rs.getInt("id_livro"));
				livrosPedido.add(itensPedidos);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return livrosPedido;		
	
	}
	
	public String RetornarTituloLivro (int idLivro) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		ResultSet rs;
		String titulo = null;
		
		String sql = "Select * from tb_livro where id_livro = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idLivro);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				
				titulo = rs.getString("titulo");

			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return titulo;
			
	}	

	public void DeletarItens (int idPedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();	
		PreparedStatement stmt = null;
		String sql = "DELETE FROM tb_item_pedido WHERE id_pedido = ?";

		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, idPedido);
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(null, "Pedido excluído com sucesso");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Pedido não encontrado");
		}
	}	
	
	
	public double obterTotal (int idPedido) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		double total = 0;
		LivroDAO livroDAO = new LivroDAO ();
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM tb_item_pedido WHERE id_pedido = ?");
			stmt.setInt(1, idPedido);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				Livro livro = new  Livro ();
				ItensPedidos itensPedidos = new ItensPedidos ();
				itensPedidos.setIdLivro(rs.getInt("id_livro"));
				itensPedidos.setQuantidade(rs.getInt("quantidade"));
				livro.setId(itensPedidos.getIdLivro());
				livroDAO.Consultar(livro);
				total = total + (livro.getPreco() * itensPedidos.getQuantidade()); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return total;		
	
	}		

	/*public int RetornarQuantidadeDeLivros (int codVenda) {
		
		Connection conexao = ModuloConexao.ModuloConexao();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int quantidade = 0;
		
		try {
			stmt = conexao.prepareStatement("SELECT * FROM ItensPedidos WHERE codVenda = ?");
			stmt.setInt(1, codVenda);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				quantidade = quantidade + rs.getInt("quantidade");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quantidade;		
	
	}*/
	

}
