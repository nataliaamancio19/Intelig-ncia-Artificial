package codigoFonte;

import java.util.Collection;
import java.util.HashSet;

public class No {

	No noPai;
	int estadoAtual;
	private Double custoEmMin; // Custo Real
	private Double faltaAteObjetivo; // Custo em linha reta
	private Collection<Linha> linhas;

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

	public Double getCustoEmMin() {
		return custoEmMin;
	}

	public void adicionarLinhas(Collection<Linha> linhas) {
		this.linhas = linhas;
	}

	public Boolean possuiLinha(Linha linha) {
		return this.linhas.contains(linha);
	}

	public Linha getLinhasIguais(No no) {
		Linha linhaIgual = null;
		for (Linha linha : linhas) {
			if (no.possuiLinha(linha)) {
				linhaIgual = linha;
				break;
			}
		}
		return linhaIgual;
	}

	public void setCustoEmMin(Double custoEmMin) {
		this.custoEmMin = custoEmMin;
	}

	public Double getFaltaAteObjetivo() {
		return faltaAteObjetivo;
	}

	public void setFaltaAteObjetivo(Double faltaAteObjetivo) {
		this.faltaAteObjetivo = faltaAteObjetivo;
	}

}
