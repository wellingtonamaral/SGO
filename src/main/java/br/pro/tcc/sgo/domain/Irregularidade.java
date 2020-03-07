package br.pro.tcc.sgo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Irregularidade extends GenericDomain {
	@Column(length = 70, nullable = false)
	private String nome;
	
	public String getNome() {
		return nome;
	}
	@Transient
	private String caminho;
	
	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length = 20)
	private String status;

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	

	
	public String getIrregularidade() {
		return irregularidade;
	}

	public void setIrregularidade(String irregularidade) {
		this.irregularidade = irregularidade;
	}

	public String getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}


	@Column(length = 5, nullable = false)
	private String matricula;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(length = 50, nullable = false)
	private String local;
	
	@Column(length = 70, nullable = false)
	private String irregularidade;
	
	@Column(length = 10, nullable = false)
	private String veiculo;
	
}
