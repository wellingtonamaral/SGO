package br.pro.tcc.sgo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Motorista extends GenericDomain {
	@Column(length = 50)
	private String nome;
	
	@Column(length = 11, nullable = false)
	private String cnh;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date cnhValidade;
	
	
	
	
	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public Date getCnhValidade() {
		return cnhValidade;
	}

	public void setCnhValidade(Date cnhValidade) {
		this.cnhValidade = cnhValidade;
	}

	@Column(length = 14, nullable = false)
	private String cpf;
	
	@Column(length = 5)
	private String matricula;
	
	@Column(length =30, nullable = false)
	private String linha;
	
	
	@Column(length =30)
	private String motorista2;
	
	@Column(length =5)
	private String matricula2;
	
	public String getMotorista2() {
		return motorista2;
	}

	public void setMotorista2(String motorista2) {
		this.motorista2 = motorista2;
	}

	public String getMatricula2() {
		return matricula2;
	}

	public void setMatricula2(String matricula2) {
		this.matricula2 = matricula2;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@Column(length = 14, nullable = false)
	private String celular;
	
/*	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade cidade;*/

}
