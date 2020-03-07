package br.pro.tcc.sgo.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.tcc.sgo.dao.VeiculoDAO;
import br.pro.tcc.sgo.domain.Veiculo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VeiculoBean implements Serializable {

	private Veiculo veiculo;
	private List<Veiculo> veiculos;



	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@PostConstruct
	public void listar() {
		try {
			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar("numero");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os veículos");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			veiculo = new Veiculo();
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo Veículo");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			veiculo = (Veiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");
			

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculos = veiculoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um veículo");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculoDAO.merge(veiculo);
			
			veiculo = new Veiculo();
			veiculos = veiculoDAO.listar("numero");
			
			Messages.addGlobalInfo("Veículo cadastrado com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar um veículo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			veiculo = (Veiculo) evento.getComponent().getAttributes().get("veiculoSelecionado");

			VeiculoDAO veiculoDAO = new VeiculoDAO();
			veiculoDAO.excluir(veiculo);

			veiculos = veiculoDAO.listar();

			Messages.addGlobalInfo("Veiculo removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o veiculo");
			erro.printStackTrace();
		}
	}

	/*public void popular() {
		try {
			if (estado != null) {
				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			} else {
				cidades = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}*/
	
}
