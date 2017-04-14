package codigoFonte;


public class Estado {

	public Estado estadoAnterior;
	public Integer[] estadoAtual;
	public int qtdChoques;
	
	public Estado()
	{
		estadoAtual = new Integer[9];
	}

	public Estado getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(Estado estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}
	public Integer[] getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(Integer[] estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public int getQtdChoques() {
		return qtdChoques;
	}

	public void setQtdChoques(int qtdChoques) {
		this.qtdChoques = qtdChoques;
	}

}
