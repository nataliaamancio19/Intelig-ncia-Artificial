package codigoFonte;


public class Centroide {

	public double posicaoX;
	public double posicaoY;
	public int classe;
	public int quantidadeDeAmostras;
	
	public int getQuantidadeDeAmostras() {
		return quantidadeDeAmostras;
	}

	public void setQuantidadeDeAmostras(int quantidadeDeAmostras) {
		this.quantidadeDeAmostras = quantidadeDeAmostras;
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
