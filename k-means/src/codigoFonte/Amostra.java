package codigoFonte;

import java.util.ArrayList;

public class Amostra {

	public double posicaoX;
	public double posicaoY;
	public int classe;
	public ArrayList<Centro> centros;
	
	public Amostra()
	{
		centros = new ArrayList<Centro>();
	}
	
	public ArrayList<Centro> getCentros() {
		return centros;
	}
	public void setCentros(ArrayList<Centro> centros) {
		this.centros = centros;
	}
	public void setClasse(int classe){
		this.classe = classe;
	}
	public int getClasse(){
		return classe;
	}
	public double getPosicaoX() {
		return posicaoX;
	}
	public void setPosicaoX(double posicaoX) {
		this.posicaoX = posicaoX;
	}
	public double getPosicaoY() {
		return posicaoY;
	}
	public void setPosicaoY(double posicaoY) {
		this.posicaoY = posicaoY;
	}
}
