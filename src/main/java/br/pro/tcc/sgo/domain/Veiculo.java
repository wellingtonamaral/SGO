package br.pro.tcc.sgo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Veiculo extends GenericDomain {
	@Column(length = 10, nullable = false)
	private String numero;
	
	@Column(length = 8, nullable = false)
	private String placa;
	
	@Column(length = 6, nullable = false)
	private String tacografo;
	
	@Column(length = 9, nullable = false)
	private String tipoServico;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTacografo() {
		return tacografo;
	}

	public void setTacografo(String tacografo) {
		this.tacografo = tacografo;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}
	
/*	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade cidade;*/

}
