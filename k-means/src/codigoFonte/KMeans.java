package codigoFonte;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class KMeans {

	static ArrayList<Amostra> amostras;
	static ArrayList<Centroide> centroides;
	static boolean alterouClasse = true;
	final static int quantidadeDeClasses = 2;
	
	public static void main(String[] args)
	{
		int contador = 0;
		amostras = new ArrayList<Amostra>();
		centroides = new ArrayList<Centroide>();
		leituraDaBaseDeDados();

		while(alterouClasse == true)
		{
			++contador;
			System.out.println("Iteração " + contador + "\n");
			calculaCentroides();
			geraCentros();
			analisaClasses();
			imprimir();
		}
		
		
	}
	
	public static void imprimir()
	{
		for(Centroide centroideAtual: centroides)
		{
			System.out.println("Classe: " + centroideAtual.getClasse() +  "  Qtd: " +  centroideAtual.getQuantidadeDeAmostras());
			System.out.println();
			
			for(Amostra amostraAtual: amostras)
			{
				if(amostraAtual.getClasse() == centroideAtual.getClasse())
					System.out.println("X: " +  amostraAtual.getPosicaoX() + " Y: " +  amostraAtual.getPosicaoY());
			}
		}
	}
	
	public static void leituraDaBaseDeDados()
	{	
		int aleatorio;
		String[] dados = new String[3];
		
		String nome = "baseDeDados.txt";
		
		try 
	    {
	      BufferedReader lerArq = new BufferedReader(new FileReader(nome));
	      String linha = lerArq.readLine(); 
	      
	      while (linha != null) {
	    	
	    	dados = new String[3];
	    	dados = linha.split(";");
	    
	    	
	    	Amostra p = new Amostra();
	    	p.setPosicaoX(Double.valueOf(dados[0]));
	    	p.setPosicaoY(Double.valueOf(dados[1]));
	    	
	    	
	    	aleatorio = new Random().nextInt(quantidadeDeClasses + 1);
	    	
	    	while(aleatorio == 0)
	    	{
	    		aleatorio = new Random().nextInt(quantidadeDeClasses + 1);
	    	}
	    
	    	p.setClasse(aleatorio);
	    	amostras.add(p);
	    	
	        linha = lerArq.readLine(); 
	       
	      }
	      lerArq.close();
	    } 
	    catch (IOException e) 
	    {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",
	          e.getMessage());
	    }
	}
	
	public static void calculaCentroides()
	{
		double centroX = 0, centroY = 0;
		Centroide centroide;
		int contador = 0;
		
		centroides = new ArrayList<Centroide>();
		for(int i = 1; i <= quantidadeDeClasses; i++)
		{
			centroide = new Centroide();
			centroide.setClasse(i);
			centroX = 0.0;
			centroY = 0.0;
			contador = 0;
			for(Amostra amostraAtual : amostras)
			{
				if(amostraAtual.getClasse() == i)
				{
					centroX = centroX + amostraAtual.getPosicaoX();
					centroY = centroY + amostraAtual.getPosicaoY();
					++contador;
				}
			}
			centroide.setPosicaoX(centroX / contador);
			centroide.setPosicaoY(centroY / contador);
			centroide.setQuantidadeDeAmostras(contador);
			centroides.add(centroide);
		}
	}

	public static double retornaCentroide(int classe, int posicao)
	{
		for(Centroide centroide: centroides)
		{
			if(centroide.getClasse() == classe)
			{
				if(posicao == 0)
					return centroide.getPosicaoX();
				else if(posicao == 1)
					return centroide.getPosicaoY();
			}
		}
		return 0.0;
	}
	
	public static void geraCentros()
	{
		Centro centro;
		double valor_do_centro;
		
		for(int i = 1; i <= quantidadeDeClasses; i++)
		{
			for(Amostra amostraAtual: amostras)
			{
				valor_do_centro = Math.abs(retornaCentroide(i, 0) - amostraAtual.getPosicaoX()) + 
						Math.abs(retornaCentroide(i, 1) - amostraAtual.getPosicaoY());
				
				centro = new Centro();
				centro.setClasse(i);
				centro.setCentro(valor_do_centro);
				amostraAtual.getCentros().add(centro);
			}
		}
	}
	
	public static void analisaClasses()
	{
		int classe_mais_proxima; 
		alterouClasse = false;
		for(Amostra amostraAtual : amostras)
		{
			amostraAtual.setCentros(ordenaCentros(amostraAtual.getCentros()));
			classe_mais_proxima = amostraAtual.getCentros().get(0).getClasse();
			if(amostraAtual.getClasse() != classe_mais_proxima)
			{
				amostraAtual.classe = classe_mais_proxima;
				alterouClasse = true;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Centro> ordenaCentros(ArrayList<Centro> centros)
	{
		 Collections.sort(centros, new Comparator() {
	         public int compare(Object o1, Object o2) {
	        	 Centro p1 = (Centro) o1;
	        	 Centro p2 = (Centro) o2;
	             return p1.centro < p2.centro ? -1 : (p1.centro > p2.centro ? +1 : 0);
	         }
	     });
		 
		 return centros;
	}
}
