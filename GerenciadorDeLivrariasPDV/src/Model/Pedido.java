package Model;

import java.util.Date;

//id_cliente_juridico, id_cliente_fisico, id_funcionario, forma_pagamento, total

public class Pedido {

	private int idClienteJuridico;
	private int idClienteFisico;
	private int idFuncionario;
	private String formaPagamento;
	private double total;
	private int id;
	private Date dataPedido;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdClienteJuridico() {
		return idClienteJuridico;
	}
	public void setIdClienteJuridico(int idClienteJuridico) {
		this.idClienteJuridico = idClienteJuridico;
	}
	public int getIdClienteFisico() {
		return idClienteFisico;
	}
	public void setIdClienteFisico(int idClienteFisico) {
		this.idClienteFisico = idClienteFisico;
	}
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	
}
