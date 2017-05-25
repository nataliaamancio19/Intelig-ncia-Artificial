import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import codigoFonte.Estado;

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
	static float[][] dadosTreinamento = new float[80][8];
	static float[][] dadosTeste = new float[40][8];
	static final int TAMANHO_BASE_DE_TESTE = 40;
	static final int TAMANHO_BASE_DE_TREINAMENTO = 80;
	static final int TAMANHO_BASE_ORIGINAL = 120;
	
	public static void main(String [] args)
	{
		// Qtd de números ímpares entre 1 e 11
		float[] distancias_mais_proximas = new float[6]; 
		int contador = 0;
		leituraDaBaseDeDados();
		normalizacaoDosDados();
		separaDados();
		
		// O k determina a quantidade de vizinhos mais próximos que vamos pegar
		// daí a gente vê qual mais se repete
		for(int k = 1; k <= 11; k = k + 2)
		{
			System.out.println("k = " +  k);
			distancias_mais_proximas[contador] = calcularDistancia(k);
			// montar a tabela
			System.out.println("Distância mais próxima k = " + k + " : " +  distancias_mais_proximas[contador]);
			++contador;
		}
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
					dadosNormalizados[a][b] = Float.valueOf(dadosOriginais[a][b].replace(",","."));
				}
			}
	    }
		
		/* 	for(float[] linha : dadosNormalizados)
		    {
		    	System.out.printf("%s   ", linha[0]);
		    	System.out.printf("%s   ", linha[1]);
		    	System.out.printf("%s   ", linha[2]);
		    	System.out.printf("%s   ", linha[3]);
		    	System.out.printf("%s   ", linha[4]);
		    	System.out.printf("%s   ", linha[5]);
		    	System.out.printf("%s   ", linha[6]);
		    	System.out.printf("%s\n", linha[7]);
		    } */
	}

	public static void separaDados()
	{
		int linha = 0;
		for(int a = 0; a < TAMANHO_BASE_ORIGINAL; a++)
	    {
			for(int b = 0; b < 6; b++)
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
				if(distancias_mais_proximas[a] == distancias_mais_proximas[b])
				++contador;
			}
			contadores.add(contador);
			valores.add(distancias_mais_proximas[a][0]);
			contador = 0;
		}
		
		// Tenho que pegar o maior valor e vê se ele se repete, caso se repita eu pego o menor deles.(valor numérico);
		for(int valor : contadores) {  
		      if(valor > maiorValor) {
		            maiorValor = valor;
		      }
		}
		
		int index_do_maior_contador = contadores.indexOf(maiorValor);
		mais_se_repete = valores.get(index_do_maior_contador);
		
		int contadorDoMaiorContador = 0;
		
		for(int contadorAtual : contadores)
		{
			if(contadorAtual == maiorValor)
				++ contadorDoMaiorContador;
		}
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
	
	public static float calcularDistancia(int k)
	{
		float[][] distancias = new float[80][2];
		float[][] distancias_mais_proximas = new float[k][2];
		float menor_distancia;
		
		for(int linhaTeste = 0; linhaTeste < TAMANHO_BASE_DE_TESTE; linhaTeste++)  
		{
			for(int linhaTreinamento = 0; linhaTreinamento < TAMANHO_BASE_DE_TREINAMENTO; linhaTreinamento++ )
			{
				distancias[linhaTreinamento][0] = Math.abs(dadosTeste[linhaTeste][0] - dadosTreinamento[linhaTreinamento][0]) +
							Math.abs(dadosTeste[linhaTeste][1] - dadosTreinamento[linhaTreinamento][1]) + 
							Math.abs(dadosTeste[linhaTeste][2] - dadosTreinamento[linhaTreinamento][2]) + 
							Math.abs(dadosTeste[linhaTeste][3] - dadosTreinamento[linhaTreinamento][3]) +
							Math.abs(dadosTeste[linhaTeste][4] - dadosTreinamento[linhaTreinamento][4]) + 
							Math.abs(dadosTeste[linhaTeste][5] - dadosTreinamento[linhaTreinamento][5]);
				
				distancias[linhaTreinamento][1] = linhaTreinamento;
			}
			
			Arrays.sort(distancias);
			for(int a = 0; a < k ; a++)
			{
				distancias_mais_proximas[a][0] = distancias[a][0];
				distancias_mais_proximas[a][1] = distancias[a][1];
			}
			
			if(k == 1)
			{
				System.out.println("LINHA = " +  linhaTeste);
				menor_distancia = distancias_mais_proximas[0][0];
			}
			else
			{
				System.out.println("LINHA = " +  linhaTeste);
				menor_distancia = verificaQuemMaisSeRepete(distancias_mais_proximas, k);
			}
			int linhaTreinamentoDaMenorDistancia = 0;
			for(int a = 0; a < distancias_mais_proximas.length; a++)
			{
				if(distancias_mais_proximas[a][0] == menor_distancia)
					linhaTreinamentoDaMenorDistancia = (int) distancias_mais_proximas[a][1];
			}
			
			if(dadosTeste[linhaTeste][5] == dadosTreinamento[linhaTreinamentoDaMenorDistancia][5]
					&& dadosTeste[linhaTeste][6] == dadosTreinamento[linhaTreinamentoDaMenorDistancia][6])
			{
				System.out.println("ACERTOU");
			}
			else
				System.out.println("ERROU");
		}
		return 0;
	}
}
