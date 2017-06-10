public class Estado {

	public Estado estadoAnterior;
	public String[] estadoAtual;
	public int qtdChoques;
	
	public Estado()
	{
		estadoAtual = new String[8];
	}

	public Estado getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(Estado estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}
	public String[] getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(String[] estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public int getQtdChoques() {
		return qtdChoques;
	}

	public void setQtdChoques(int qtdChoques) {
		this.qtdChoques = qtdChoques;
	}
}