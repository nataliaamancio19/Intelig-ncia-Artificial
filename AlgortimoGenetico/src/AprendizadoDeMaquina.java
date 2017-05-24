import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class AprendizadoDeMaquina {

	static String[][] dadosOriginais = new String[120][8];
	static float[][] dadosNormalizados = new float[120][8];
	static float[][] dadosTreinamento = new float[80][8];
	static float[][] dadosTeste = new float[40][8];
	
	public static void main(String [] args)
	{
		leituraDaBaseDeDados();
		normalizacaoDosDados();
		separaDados();
	}
	
	public static void leituraDaBaseDeDados()
	{	 
	    String nome = "C:\\Users\\Natália\\workspace\\AlgortimoGenetico\\src\\diagnostico.txt"; 
	    int contador = 0;
	    try 
	    {
	    	
	      BufferedReader lerArq = new BufferedReader(new FileReader(nome));
	      String linha = lerArq.readLine(); 
	      
	      while (linha != null) {
	    
	    	dadosOriginais[contador] = linha.split("\t");
	    
	        linha = lerArq.readLine(); 
	        ++contador;
	      }
	      lerArq.close();
	    } 
	    catch (IOException e) 
	    {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",
	          e.getMessage());
	    }
	    
	 /*   for(String[] linha : dadosOriginais)
	    {
	    	System.out.printf("%s", linha[0]);
	    	System.out.printf("%s", linha[1]);
	    	System.out.printf("%s", linha[2]);
	    	System.out.printf("%s", linha[3]);
	    	System.out.printf("%s", linha[4]);
	    	System.out.printf("%s", linha[5]);
	    	System.out.printf("%s", linha[6]);
	    	System.out.printf("%s\n", linha[7]);
	    }*/
	}

	public static void normalizacaoDosDados()
	{
		for(int a = 0; a < 120; a++)
	    {
			for(int b = 0; b < 8; b++)
			{
				if(dadosOriginais[a][b].equals("yes"))
					dadosNormalizados[a][b] = 1 ;
				else if(dadosOriginais[a][b].equals("no"))
					dadosNormalizados[a][b] = 0 ;
				else 
				{
					dadosNormalizados[a][b] = Float.valueOf(dadosOriginais[a][b].replace(",","."));
				}
			}
	    }
		
		 	for(float[] linha : dadosNormalizados)
		    {
		    	System.out.printf("%s   ", linha[0]);
		    	System.out.printf("%s   ", linha[1]);
		    	System.out.printf("%s   ", linha[2]);
		    	System.out.printf("%s   ", linha[3]);
		    	System.out.printf("%s   ", linha[4]);
		    	System.out.printf("%s   ", linha[5]);
		    	System.out.printf("%s   ", linha[6]);
		    	System.out.printf("%s\n", linha[7]);
		    }
	}

	public static void separaDados()
	{
		int linha = 0;
		for(int a = 0; a < 120; a++)
	    {
			for(int b = 0; b < 8; b++)
			{
				if(a <= 80)
					dadosTreinamento[a][b] = dadosNormalizados[a][b];
				else
				{
					dadosTeste[linha][b] = dadosNormalizados[a][b];
					++linha;
				}
			}
	    }
		
		
		for(float[] a : dadosTreinamento)
	    {
	    	System.out.printf("%s   ", a[0]);
	    	System.out.printf("%s   ", a[1]);
	    	System.out.printf("%s   ", a[2]);
	    	System.out.printf("%s   ", a[3]);
	    	System.out.printf("%s   ", a[4]);
	    	System.out.printf("%s   ", a[5]);
	    	System.out.printf("%s   ", a[6]);
	    	System.out.printf("%s\n", a[7]);
	    }
		
		for(float[] a : dadosTeste)
	    {
	    	System.out.printf("%s   ", a[0]);
	    	System.out.printf("%s   ", a[1]);
	    	System.out.printf("%s   ", a[2]);
	    	System.out.printf("%s   ", a[3]);
	    	System.out.printf("%s   ", a[4]);
	    	System.out.printf("%s   ", a[5]);
	    	System.out.printf("%s   ", a[6]);
	    	System.out.printf("%s\n", a[7]);
	    }
	}
	
	public static void calcularDistancia()
	{
		
	}
}
