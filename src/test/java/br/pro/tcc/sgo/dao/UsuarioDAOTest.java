package br.pro.tcc.sgo.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.pro.tcc.sgo.dao.PessoaDAO;
import br.pro.tcc.sgo.dao.UsuarioDAO;
import br.pro.tcc.sgo.domain.Pessoa;
import br.pro.tcc.sgo.domain.Usuario;

public class UsuarioDAOTest {
	@Test
	@Ignore
	public void salvar() throws ParseException{
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.buscar(1L);
		
		System.out.println("Pessoa Encontrada");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("CPF: " + pessoa.getCpf());
		System.out.println("CPF: " + pessoa.getMatricula());
		
		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("11651");
		usuario.setDataCadastro(new SimpleDateFormat("dd/MM/yyyy").parse("05/12/2017"));

		
		SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
		usuario.setSenha(hash.toHex());
		
		usuario.setTipo('A');
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
		System.out.println("Usuário salvo com sucesso.");
	}
	
	@Test
	@Ignore

	public void autenticar(){
		String matricula = "11651";
		String senha = "11651";
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.autenticar(matricula, senha);
		
		System.out.println("Usuário autentica: " + usuario);
	}
}	
