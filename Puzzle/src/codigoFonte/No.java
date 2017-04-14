package codigoFonte;

import java.util.Arrays;

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
	
	@Override
	public int hashCode()
	{
		// TODO Auto-generated method stub
		return Arrays.deepHashCode(noAtual);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		No no = (No) obj;
		return Puzzle.comparaEstados(no.noAtual, noAtual);
	}
}
