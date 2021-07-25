/**
 * 
 */
package Model;

import java.util.Date;

/**
 * @author m9
 *
 */
public abstract class Pessoa {

	protected int id;
	protected String nome;
	protected String celular;
	protected String email;
	protected String dataCadastro;
	protected String dataCriacao;
	protected boolean status;
	protected int idEndereco;
	
	public Pessoa() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() { 
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getIdEndereco() {
		return idEndereco;
	}
	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}
	
}
