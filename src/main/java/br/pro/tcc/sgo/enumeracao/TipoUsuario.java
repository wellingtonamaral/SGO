package br.pro.tcc.sgo.enumeracao;

public enum TipoUsuario {
	ADMINISTRADOR, JURIDICO, SAC, OPERACIONAL, MONITORAMENTO;

	@Override
	public String toString() {
		switch (this) {
		case ADMINISTRADOR:

			return "Administrador";
		case JURIDICO:

			return "Juridico";
		case SAC:

			return "Sac";
		case OPERACIONAL:

			return "Operacional";
		case MONITORAMENTO:

			return "Monitoramento";
			
		default:
			return null;
		}

	}
}
