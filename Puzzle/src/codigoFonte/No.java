package codigoFonte;

public class No 
{
	public No noPai;
	public String[][] noAtual;
	
	public No()
	{
		noAtual = new String[3][3];
	}
	
	public No getNoPai() {
		return noPai;
	}
	public void setNoPai(No noPai) {
		this.noPai = noPai;
	}
	public String[][] getNoAtual() {
		return noAtual;
	}
	public void setNoAtual(String[][] noAtual) {
		this.noAtual = noAtual;
	}	
}
