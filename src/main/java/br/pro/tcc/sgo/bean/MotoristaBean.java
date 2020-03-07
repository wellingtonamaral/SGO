package br.pro.tcc.sgo.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.tcc.sgo.dao.MotoristaDAO;
import br.pro.tcc.sgo.domain.Motorista;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MotoristaBean implements Serializable {

	private Motorista motorista;
	private List<Motorista> motoristas;
	
    private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private String matricula2; 
    private String motorista2;  
    private Map<String,String> matriculas2;
    private Map<String,String> motoristas2;

	

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public List<Motorista> getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}

	@PostConstruct
	public void listar() {
		try {
			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristas = motoristaDAO.listar("nome");
			motoristas = motoristaDAO.listar("matricula");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os motoristas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			motorista = new Motorista();
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo Motorista");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			motorista = (Motorista) evento.getComponent().getAttributes().get("motoristaSelecionado");
			

			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristas = motoristaDAO.listar();
			
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar um motorista");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristaDAO.merge(motorista);
			
			motorista = new Motorista();
			motoristas = motoristaDAO.listar("nome");
			motoristas = motoristaDAO.listar("matricula");
			
			Messages.addGlobalInfo("Motorista cadastrado com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar um motorista");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			motorista = (Motorista) evento.getComponent().getAttributes().get("motoristaSelecionado");

			MotoristaDAO motoristaDAO = new MotoristaDAO();
			motoristaDAO.excluir(motorista);

			motoristas = motoristaDAO.listar();

			Messages.addGlobalInfo("Motorista removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o motorista");
			erro.printStackTrace();
		}
	}
	@PostConstruct
    public void init() {
    	matriculas2  = new HashMap<String, String>();
    	matriculas2.put("11651", "11651");
    	matriculas2.put("11654", "11654");
    	matriculas2.put("Brazil", "Brazil");
         
        Map<String,String> map = new HashMap<String, String>();
        map.put("Wellington Amaral", "Wellington Amaral");
        
        data.put("11651", map);
         
        map = new HashMap<String, String>();
        map.put("Edson", "Edson");
        data.put("11654", map);
         
        map = new HashMap<String, String>();
        map.put("Rio de Janerio", "Rio de Janerio");
        data.put("Brazil", map);
    }
 
    public Map<String, Map<String, String>> getData() {
        return data;
    }
 
    public String getMatricula2() {
        return matricula2;
    }
 
    public void setMatricula2(String matricula2) {
        this.matricula2 = matricula2;
    }
 
    public String getMotorista2() {
        return motorista2;
    }
 
    public void setMotorista2(String motorista2) {
        this.motorista2 = motorista2;
    }
 
    public Map<String, String> getMatriculas2() {
        return matriculas2;
    }
 
    public Map<String, String> getMotoristas2() {
        return motoristas2;
    }
 
    public void onCountryChange() {
        if(matricula2 !=null && !matricula2.equals(""))
           motoristas2 = data.get(matricula2);
        else
        	motoristas2 = new HashMap<String, String>();
    }
     
    public void displayLocation() {
        FacesMessage msg;
        if(motorista2 != null && matricula2 != null)
            msg = new FacesMessage("Selected", motorista2 + " of " + matricula2);
        else
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected."); 
             
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
}
