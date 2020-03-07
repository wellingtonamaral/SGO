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

import br.pro.tcc.sgo.dao.InfracaoDAO;
import br.pro.tcc.sgo.domain.Infracao;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class InfracaoBean implements Serializable {
	private Infracao infracao;
	private List<Infracao> infracoes;
	private StreamedContent foto;


	




	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

	public Infracao getInfracao() {
		return infracao;
	}

	public void setInfracao(Infracao infracao) {
		this.infracao = infracao;
	}

	public List<Infracao> getInfracoes() {
		return infracoes;
	}

	public void setInfracoes(List<Infracao> infracoes) {
		this.infracoes = infracoes;
	}

	@PostConstruct
	public void listar() {
		try {
			InfracaoDAO infracaoDAO = new InfracaoDAO();
			infracoes = infracaoDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os infrações");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			infracao = new Infracao();
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo Infração");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			infracao = (Infracao) evento.getComponent().getAttributes().get("infracaoSelecionada");
			

			InfracaoDAO infracaoDAO = new InfracaoDAO();
			infracoes = infracaoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma Infração");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			if (infracao.getCaminho() == null) {
				Messages.addGlobalError("O campo multimidia é obrigatório");
			}
			
			
			InfracaoDAO infracaoDAO = new InfracaoDAO();
			Infracao infracaoRetorno = infracaoDAO.merge(infracao);
			
			
			Path origem = Paths.get(infracao.getCaminho());
			Path destino = Paths.get("C:/multimidia/infracao/" + infracaoRetorno.getCodigo() + ".mp4");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			
			
			infracao = new Infracao();
			infracoes = infracaoDAO.listar("nome");
			
			Messages.addGlobalInfo("Infração cadastrada com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar uma Infração");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			infracao = (Infracao) evento.getComponent().getAttributes().get("infracaoSelecionada");

			InfracaoDAO infracaoDAO = new InfracaoDAO();
			infracaoDAO.excluir(infracao);

			infracoes = infracaoDAO.listar();

			Messages.addGlobalInfo("Infração removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a Infração");
			erro.printStackTrace();
		}
	}

	
	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			infracao.setCaminho(arquivoTemp.toString());

			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}
	
	public void download(ActionEvent evento) {
		try {
			infracao = (Infracao) evento.getComponent().getAttributes().get("infracaoSelecionada");
			
			InputStream stream = new FileInputStream("C:/multimidia/infracao/" + infracao.getCodigo() + ".mp4");
			foto = new DefaultStreamedContent(stream, "video/mp4", infracao.getCodigo() + ".mp4");
		} catch (FileNotFoundException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar efetuar o download do multimidia");
			erro.printStackTrace();
		}
	}
	
	
}