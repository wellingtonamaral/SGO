package br.pro.tcc.sgo.util;

import org.hibernate.Session;
import org.junit.Test;

import br.pro.tcc.sgo.util.HibernateUtil;

public class HibernateUtilTest {
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}
