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

import br.pro.tcc.sgo.dao.ReclamacaoDAO;
import br.pro.tcc.sgo.domain.Reclamacao;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ReclamacaoBean implements Serializable {
	private Reclamacao reclamacao;
	private List<Reclamacao> reclamacoes;
	private StreamedContent foto;


	public Reclamacao getReclamacao() {
		return reclamacao;
	}

	public void setReclamacao(Reclamacao reclamacao) {
		this.reclamacao = reclamacao;
	}

	public List<Reclamacao> getReclamacoes() {
		return reclamacoes;
	}

	public void setReclamacoes(List<Reclamacao> reclamacoes) {
		this.reclamacoes = reclamacoes;
	}

	@PostConstruct
	public void listar() {
		try {
			ReclamacaoDAO reclamacaoDAO = new ReclamacaoDAO();
			reclamacoes = reclamacaoDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as reclamações");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			reclamacao = new Reclamacao();
		
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova Reclamação");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			reclamacao = (Reclamacao) evento.getComponent().getAttributes().get("reclamacaoSelecionada");
			

			ReclamacaoDAO reclamacaoDAO = new ReclamacaoDAO();
			reclamacoes = reclamacaoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma Reclamação");
			erro.printStackTrace();
		}
	}

	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

	public void salvar() {
		try {
			if (reclamacao.getCaminho() == null) {
				Messages.addGlobalError("O campo multimidia é obrigatório");
			}
			ReclamacaoDAO reclamacaoDAO = new ReclamacaoDAO();
			Reclamacao reclamacaoRetorno = reclamacaoDAO.merge(reclamacao);
			
			
			Path origem = Paths.get(reclamacao.getCaminho());
			Path destino = Paths.get("C:/multimidia/reclamacao/" + reclamacaoRetorno.getCodigo() + ".mp4");
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			
			reclamacao = new Reclamacao();
			reclamacoes = reclamacaoDAO.listar("nome");
			
			Messages.addGlobalInfo("Reclamação cadastrada com sucesso");
		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar cadastrar uma Reclamação");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			reclamacao = (Reclamacao) evento.getComponent().getAttributes().get("reclamacaoSelecionada");

			ReclamacaoDAO reclamacaoDAO = new ReclamacaoDAO();
			reclamacaoDAO.excluir(reclamacao);

			reclamacoes = reclamacaoDAO.listar();

			Messages.addGlobalInfo("Reclamação removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a Reclamação");
			erro.printStackTrace();
		}
	}
	

	
	public void upload(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			reclamacao.setCaminho(arquivoTemp.toString());

			Messages.addGlobalInfo("Upload realizado com sucesso");
		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar realizar o upload de arquivo");
			erro.printStackTrace();
		}
	}
	
	public void download(ActionEvent evento) {
		try {
			reclamacao = (Reclamacao) evento.getComponent().getAttributes().get("reclamacaoSelecionada");
			
			InputStream stream = new FileInputStream("C:/multimidia/reclamacao/" + reclamacao.getCodigo() + ".mp4");
			foto = new DefaultStreamedContent(stream, "video/mp4", reclamacao.getCodigo() + ".mp4");
		} catch (FileNotFoundException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar efetuar o download do multimidia");
			erro.printStackTrace();
		}
	}

	
}