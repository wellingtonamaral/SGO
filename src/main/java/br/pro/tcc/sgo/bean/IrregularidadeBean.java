package br.pro.tcc.sgo.bean;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.pro.tcc.sgo.dao.IrregularidadeDAO;
import br.pro.tcc.sgo.domain.Irregularidade;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class IrregularidadeBean implements Serializable {
	private Irregularidade irregularidade;
	private List<Irregularidade> irregularidades;
	private StreamedContent foto;
	
	public StreamedContent getFoto() {
		return foto;
	}
		public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}
	public Irregularidade getIrregularidade() {
		return irregularidade;
	}

	public void setIrregularidade(Irregularidade irregularidade) {
		this.irregularidade = irregularidade;
	}

	public List<Irregularidade> getIrregularidades() {
		return irregularidades;
	}

	public void setIrregularidades(List<Irregularidade> irregularidades) {
		this.irregularidades = irregularidades;
	}


	@PostConstruct
	public void listar() {
		try {
			IrregularidadeDAO irregularidadeDAO = new IrregularidadeDAO();
			irregularidades = irregularidadeDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os irregularidades");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			irregularidade = new Irregularidade();
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo Irregularidade");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			irregularidade = (Irregularidade) evento.getComponent().getAttributes().get("irregularidadeSelecionado");
			

			IrregularidadeDAO irregularidadeDAO = new IrregularidadeDAO();
			irregularidades = irregularidadeDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma Irregularidade");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (irregularidade.getCaminho() == null) {
				Messages.addGlobalError("O campo multimidia é obrigatório");
			}
			
			IrregularidadeDAO irregularidadeDAO = new IrregularidadeDAO();
			Irregularidade irregularidadeRetorno = irregularidadeDAO.merge(irregularidade);
			
		
			Path origem = Paths.get(irregularidade.getCaminho());
			Path destino = Paths.get("C:/multimidia/irregularidade/" + irregularidadeRetorno.getCodigo() + ".mp4");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			

			irregularidade = new Irregularidade();
			irregularidades = irregularidadeDAO.listar("nome");
			
			Messages.addGlobalInfo("Infração cadastrada com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar uma Infração");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			irregularidade = (Irregularidade) evento.getComponent().getAttributes().get("irregularidadeSelecionado");

			IrregularidadeDAO irregularidadeDAO = new IrregularidadeDAO();
			irregularidadeDAO.excluir(irregularidade);

			irregularidades = irregularidadeDAO.listar();

			Messages.addGlobalInfo("Irregularidade removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a Irregularidade");
			erro.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			irregularidade.setCaminho(arquivoTemp.toString());

			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}
	
	public void download(ActionEvent evento) {
		try {
			irregularidade = (Irregularidade) evento.getComponent().getAttributes().get("irregularidadeSelecionada");
			
			InputStream stream = new FileInputStream("C:/multimidia/irregularidade/" + irregularidade.getCodigo() + ".mp4");
			foto = new DefaultStreamedContent(stream, "video/mp4", irregularidade.getCodigo() + ".mp4");
		} catch (FileNotFoundException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar efetuar o download do multimidia");
			erro.printStackTrace();
		}
	}
	
	
}