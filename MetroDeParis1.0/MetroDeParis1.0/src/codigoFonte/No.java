package codigoFonte;

public class No {

	No noPai;
	int estadoAtual;
	int distanciaPercorrida;
	int faltaAteObjetivo;
	int custoAtual;
	
	public void gerarCustoAtual()
	{
		custoAtual = distanciaPercorrida + faltaAteObjetivo;
	}
	
	public No getNoPai() {
		return noPai;
	}
	public void setNoPai(No noPai) {
		this.noPai = noPai;
	}

	public int getEstadoAtual() {
		return estadoAtual;
	}
	public void setEstadoAtual(int estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
	
	public int getDistanciaPercorrida() {
		return distanciaPercorrida;
	}
	public void setDistanciaPercorrida(int distanciaPercorrida) {
		this.distanciaPercorrida = distanciaPercorrida;
	}
	public int getFaltaAteObjetivo() {
		return faltaAteObjetivo;
	}
	public void setFaltaAteObjetivo(int faltaAteObjetivo) {
		this.faltaAteObjetivo = faltaAteObjetivo;
	}
	public int getCustoAtual() {
		return custoAtual;
	}
	public void setCustoAtual(int custoAtual) {
		this.custoAtual = custoAtual;
	}
}
