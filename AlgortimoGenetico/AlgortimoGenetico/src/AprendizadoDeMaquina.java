import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * A idéia principal deste conjunto de dados é preparar o algoritmo do sistema especialista, que 
	irá realizar o diagnóstico presuntivo de duas doenças do sistema urinário. Será 
	o exemplo de diagnóstico das inflamações agudas da bexiga urinária e 
	nefrite aguda.
	
	A inflamação aguda da bexiga urinária é caracterizada pela ocorrência súbita de dores na região
	do abdômen ea micção na forma de pressão constante na urina, dores de micção e, por vezes, falta
	de retenção de urina.
	
	A nefrite aguda da origem da pelve renal ocorre consideravelmente mais freqüentemente nas mulheres do que nos 
	homens. Começa com febre repentina, que atinge, e às vezes excede 40C. A febre é acompanhada por arrepios e 
	dores lombares de um ou ambos os lados, que às vezes são muito fortes. 
	
 * **/

public class AprendizadoDeMaquina {

	static String[][] dadosOriginais = new String[120][8];
	static float[][] dadosNormalizados = new float[120][8];
	static float[][] dadosTreinamento = new float[70][8];
	static float[][] dadosTeste = new float[50][8];
	static final int TAMANHO_BASE_DE_TESTE = 50;
	static final int TAMANHO_BASE_DE_TREINAMENTO = 70;
	static final int TAMANHO_BASE_ORIGINAL = 120;
	static Integer tabelaConfusao[][] = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
	
	public static void main(String [] args)
	{
		leituraDaBaseDeDados();
		normalizacaoDosDados();
		separaDados();
		 
		Scanner ler = new Scanner(System.in);
		System.out.println("Escolha a distância: ");
		System.out.println("1 -  Distância Euclidiana\n2 - Distância de Manhattan\n3 - Distância de Minkowski");
		int distancia;
		distancia = ler.nextInt();
		
		for(int k = 1; k <= 11; k = k + 2)
			calcularDistancia(k, distancia);
		
		ler.close();
	}
	
	public static void leituraDaBaseDeDados()
	{	
		//String nome = "C:\\Users\\Aluno\\workspace\\AlgortimoGenetico\\src\\diagnostico.txt";
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
	}

	public static void normalizacaoDosDados()
	{
		for(int a = 0; a < TAMANHO_BASE_ORIGINAL; a++)
	    {
			for(int b = 0; b < 8; b++)
			{
				if(dadosOriginais[a][b].equals("yes"))
					dadosNormalizados[a][b] = 1 ;
				else if(dadosOriginais[a][b].equals("no"))
					dadosNormalizados[a][b] = 0 ;
				else 
				{
					dadosNormalizados[a][b] = Float.valueOf(dadosOriginais[a][b].replace(",",".")) / 10;
				}
			}
	    }
	}

	public static void separaDados()
	{
		int linha = 0;
		for(int a = 0; a < TAMANHO_BASE_ORIGINAL; a++)
	    {
			for(int b = 0; b < 8; b++)
			{
				if(a < TAMANHO_BASE_DE_TREINAMENTO)
					dadosTreinamento[a][b] = dadosNormalizados[a][b];
				else
					dadosTeste[linha][b] = dadosNormalizados[a][b];
			}
			if(a >= TAMANHO_BASE_DE_TREINAMENTO)
				++linha;
	    }
	}
	
	public static float verificaQuemMaisSeRepete(float[][] distancias_mais_proximas, int k)
	{
		ArrayList<Integer> contadores = new ArrayList<Integer>();
		ArrayList<Float> valores = new ArrayList<Float>();
		int contador = 0;
		float mais_se_repete = 0;
		int maiorValor = 0;
		
		for(int a = 0; a < distancias_mais_proximas.length; a++)
		{
			for(int b = 0; b < distancias_mais_proximas.length; b++)
			{
				if(distancias_mais_proximas[a][0] == distancias_mais_proximas[b][0])
				++contador;
			}
			
			if(!valores.contains(distancias_mais_proximas[a][0]))
			{
				valores.add(distancias_mais_proximas[a][0]);
				contadores.add(contador);
			}
			contador = 0;
		}
		
		// Pego o maior contador
		for(int valor : contadores) {  
		      if(valor > maiorValor) {
		            maiorValor = valor;
		      }
		}
	
		// Vejo se o maior contador se repete.
		int contadorDoMaiorContador = 0;
		for(int contadorAtual : contadores)
		{
			if(contadorAtual == maiorValor)
				++ contadorDoMaiorContador;
		}
		
		// Pego o menor valor dos contadores.
		float menorValor = valores.get(0);
		if(contadorDoMaiorContador != 1)
		{
			contadorDoMaiorContador = 0;
			for(int a = 0; a < valores.size(); a++)
			{
				float valorAtual = valores.get(a);
				int indexValorAtual = valores.indexOf(valorAtual);
				if( contadores.get(indexValorAtual) == maiorValor && valorAtual < menorValor )
					menorValor = valorAtual;
			}
		}
		return menorValor;
	}
	
	public static void calcularDistancia(int k, int distancia)
	{
		float[][] distancias = new float[80][2];
		float[][] distancias_mais_proximas = new float[k][2];
		float menor_distancia;
		
		for(int linhaTeste = 0; linhaTeste < TAMANHO_BASE_DE_TESTE; linhaTeste++)  
		{
			for(int linhaTreinamento = 0; linhaTreinamento < TAMANHO_BASE_DE_TREINAMENTO; linhaTreinamento++ )
			{
				switch (distancia) {
				case 1: // Distância de Manhattan 
					distancias[linhaTreinamento][0] = Math.abs(dadosTeste[linhaTeste][0] - dadosTreinamento[linhaTreinamento][0]) +
					Math.abs(dadosTeste[linhaTeste][1] - dadosTreinamento[linhaTreinamento][1]) + 
					Math.abs(dadosTeste[linhaTeste][2] - dadosTreinamento[linhaTreinamento][2]) + 
					Math.abs(dadosTeste[linhaTeste][3] - dadosTreinamento[linhaTreinamento][3]) +
					Math.abs(dadosTeste[linhaTeste][4] - dadosTreinamento[linhaTreinamento][4]) + 
					Math.abs(dadosTeste[linhaTeste][5] - dadosTreinamento[linhaTreinamento][5]);
					break;

				case 2: // Distância Euclidiana
					distancias[linhaTreinamento][0] = (float) Math.sqrt((Math.pow((double)dadosTeste[linhaTeste][0] - dadosTreinamento[linhaTreinamento][0], 2) +
							Math.pow((double)dadosTeste[linhaTeste][1] - dadosTreinamento[linhaTreinamento][1], 2) + 
							Math.pow((double)dadosTeste[linhaTeste][2] - dadosTreinamento[linhaTreinamento][2], 2) + 
							Math.pow((double)dadosTeste[linhaTeste][3] - dadosTreinamento[linhaTreinamento][3], 2) +
							Math.pow((double)dadosTeste[linhaTeste][4] - dadosTreinamento[linhaTreinamento][4], 2) + 
							Math.pow((double)dadosTeste[linhaTeste][5] - dadosTreinamento[linhaTreinamento][5], 2)));
					break;
				case 3:
					break;
				default:
					break;
				}
				
				distancias[linhaTreinamento][1] = linhaTreinamento;
			}
		
			distancias = ordenaLista(distancias);
			int linhaTreinamentoDaMenorDistancia = 0;
			
			for(int a = 0; a < k ; a++)
			{
				distancias_mais_proximas[a][0] = distancias[a][0];
				distancias_mais_proximas[a][1] = distancias[a][1];
			}
			
			if(k == 1)
			{
				menor_distancia = distancias_mais_proximas[0][0];
				linhaTreinamentoDaMenorDistancia = (int) distancias_mais_proximas[0][1];
			}
			else
			{
				menor_distancia = verificaQuemMaisSeRepete(distancias_mais_proximas, k);
				for(int a = 0; a < distancias_mais_proximas.length; a++)
				{
					if(distancias_mais_proximas[a][0] == menor_distancia){
						linhaTreinamentoDaMenorDistancia = (int) distancias_mais_proximas[a][1];
						break;
					}
				}
			}
		
			int classeObtida = 0, classeEsperada = 0;
			
			if(dadosTeste[linhaTeste][6] == 0 && dadosTeste[linhaTeste][7] == 0)
				classeEsperada = 0;
			else if(dadosTeste[linhaTeste][6] == 0 && dadosTeste[linhaTeste][7] == 1)
				classeEsperada = 1;
			else if(dadosTeste[linhaTeste][6] == 1 && dadosTeste[linhaTeste][7] == 0)
				classeEsperada = 2;
			else if(dadosTeste[linhaTeste][6] == 1 && dadosTeste[linhaTeste][7] == 1)
				classeEsperada = 3;
			
			if(dadosTreinamento[linhaTreinamentoDaMenorDistancia][6] == 0 && dadosTreinamento[linhaTreinamentoDaMenorDistancia][7] == 0)
				classeObtida = 0;
			else if(dadosTreinamento[linhaTreinamentoDaMenorDistancia][6] == 0 && dadosTreinamento[linhaTreinamentoDaMenorDistancia][7] == 1)
				classeObtida = 1;
			else if(dadosTreinamento[linhaTreinamentoDaMenorDistancia][6] == 1 && dadosTreinamento[linhaTreinamentoDaMenorDistancia][7] == 0)
				classeObtida = 2;
			else if(dadosTreinamento[linhaTreinamentoDaMenorDistancia][6] == 1 && dadosTreinamento[linhaTreinamentoDaMenorDistancia][7] == 1)
				classeObtida = 3;
			
			++tabelaConfusao[classeObtida][classeEsperada];
			}
		imprimeTabelaConfusao(k);
		zerarTabelaConfusao();
	}
	
	public static void zerarTabelaConfusao()
	{
		for(int a = 0; a < 4; a++)
		{
			for(int b = 0; b < 4 ; b++)
			{
				tabelaConfusao[a][b] = 0;
			}
		}
	}
	
 	public static void imprimeTabelaConfusao(int k)
	{
		System.out.println("\n\nTabela confusão k = " +  k + "\n");
	
		System.out.println("I - Não possue nenhuma inflamação.");
		System.out.println("II - Inflamação da bexiga urinária.");
		System.out.println("III - Nefrite de origem da pelve renal.");
		System.out.println("IV - Inflamação da bexiga urinária e Nefrite de origem da pelve renal.\n\n");
		
		System.out.println("    I  II  III  IV");
		
		for(int a = 0; a < 4; a++)
		{
			for(int b = 0; b < 4 ; b++)
			{
				if(a == 0 && b == 0)
					System.out.print(" I  ");
				else if(a == 1 && b == 0)
					System.out.print("II  ");
				else if(a == 2 && b == 0)
					System.out.print("III ");
				else if(a == 3 && b == 0)
					System.out.print("IV  ");
					
				if(b != 3)
					System.out.print(tabelaConfusao[a][b] + "   ");
				else 
					System.out.println(tabelaConfusao[a][b]);
			}
		}
	}
	
	public static float[][] ordenaLista(float[][] listaParaSerOrdenada)
	{
		float auxLinha, auxDistancia;
		
		for(int a = 0; a < listaParaSerOrdenada.length; a++)
		{
			for(int b = 0; b < listaParaSerOrdenada.length; b++)
			{
				if(listaParaSerOrdenada[a][0] < listaParaSerOrdenada[b][0])
				{
					auxDistancia = listaParaSerOrdenada[a][0];
					listaParaSerOrdenada[a][0] = listaParaSerOrdenada[b][0];
					listaParaSerOrdenada[b][0] = auxDistancia;
					
					auxLinha = listaParaSerOrdenada[a][1];
					listaParaSerOrdenada[a][1] = listaParaSerOrdenada[b][1];
					listaParaSerOrdenada[b][1] = auxLinha;
				}
			}
		}
		return listaParaSerOrdenada;
	} 
}
