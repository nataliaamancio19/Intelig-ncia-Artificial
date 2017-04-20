package codigoFonte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class MetroDeParis {
	
	static int[][] custoReal = getCustosReal();
	static int[][] custosEmLinhaReta = getCustosEmLinhaReta();
	static ArrayList<Integer> estacaoAzul = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
	static ArrayList<Integer> estacaoVermelha = new ArrayList<>(Arrays.asList(11, 9, 3, 13));
	static ArrayList<Integer> estacaoAmarela = new ArrayList<>(Arrays.asList(10, 2, 9, 8, 5, 7));
	static ArrayList<Integer> estacaoVerde = new ArrayList<>(Arrays.asList(12, 8, 4, 13, 14));
	static int custo = 0;
	static int origem = 11;
	static int destino = 7;
	static int tempo = 0;
	
	public static void main(String[] args)
	{
		ArrayList<No> estacoesVisitadas = new ArrayList<No>();
		ArrayList<No> estacoes = new ArrayList<No>();
		
		No estadoInicial = getEstadoInicial();
		No estadoAtual;
		estacoes.add(estadoInicial);
		
		do
		{
			estadoAtual = estacoes.remove(0);
			
			if(!estacoesVisitadas.contains(estadoAtual) && estadoAtual.getEstadoAtual() != destino)
			{
				estacoes.addAll(expande(estadoAtual));
				estacoes = ordenaEstados(estacoes);
				estacoesVisitadas.add(estadoAtual);
			}
			
		}while(estadoAtual.getEstadoAtual() != destino);
		
		custo = estadoAtual.getCustoAtual();
		System.out.println("Custo Total (Km): " + custo);
		tempo = getTempoTotal(estadoAtual);
		System.out.println("Tempo Total (Km): " + tempo);
		imprimeNos(estadoAtual);
		
	}
	
	public static void imprimeNos(No estacaoAtual)
	{
		if(estacaoAtual.noPai!= null)
			imprimeNos(estacaoAtual.noPai);
		System.out.println("Passei pelo nó = "  + estacaoAtual.estadoAtual + " Percorri = " + estacaoAtual.distanciaPercorrida + " Custo Atual: " + estacaoAtual.custoAtual + " Heurística:" + estacaoAtual.faltaAteObjetivo );
	}
	
	public static int getTempoTotal(No estadoAtual)
	{
		tempo += estadoAtual.getCustoAtual() * 2;
			
		
		while(estadoAtual.noPai != null && estadoAtual.noPai.noPai != null)
		{
			if(!verificaSeEntaoNaMesmaLinha(estadoAtual.estadoAtual, estadoAtual.noPai.estadoAtual, estadoAtual.noPai.noPai.estadoAtual))
				tempo += 5;
			
			estadoAtual = estadoAtual.noPai;
		}
		return tempo;
	}
	
	public static boolean verificaSeEntaoNaMesmaLinha(int estacaoAtual, int estadoFilho, int estadoFilhoDoFilho)
	{
		int contemFilho = 0;
		int contemFilhoDoFilho = 0;
		ArrayList<Linhas> linhasEstacaoAtual = getLinha(estacaoAtual);
		ArrayList<Linhas> linhasEstacaoFilho = getLinha(estadoFilho);
		ArrayList<Linhas> linhasEstacaoFilhoDoFilho = getLinha(estadoFilhoDoFilho);
	
		for(Linhas linhaAtual : linhasEstacaoFilho)
		{
			if(linhasEstacaoAtual.contains(linhaAtual))
			{
				++contemFilho;
			}
			if(linhasEstacaoFilhoDoFilho.contains(linhaAtual))
			{
				++contemFilho;
			}
		}
		
		int diferente = 0, igual = 0;
		if(contemFilho == 2)
		{
			for (Linhas linha : linhasEstacaoFilhoDoFilho) 
			{
				if(linhasEstacaoAtual.contains(linha))
				{
					 ++igual;
				}
				else
				{
					++diferente;
				}
			}
		}
		
		if((contemFilho == 2 && igual == 0) || (contemFilho == 2 && igual == 1 && diferente == 1))
		{
			return false;
		}
		else
			return true;
	}
	
	public static ArrayList<No> expande( No estadoAtual)
	{
		ArrayList<No> novosNos = new ArrayList<No>();
		for(int i = 0; i < 17; i++)
		{
			for(int a = 0 ; a < 2 ; a++)
			{
				if(custoReal[i][a] == estadoAtual.getEstadoAtual())
				{
					No novo = new No();
					if(a == 0)
					{
						if(estadoAtual.getEstadoAtual() == origem) // Significa que ele não tem pai.
						{
							novo.setEstadoAtual(custoReal[i][a + 1]);
							novo.setFaltaAteObjetivo(custosEmLinhaReta[custoReal[i][a + 1]][destino]);
						}
						if(estadoAtual.noPai != null)
						{
							if(custoReal[i][a + 1] != estadoAtual.noPai.estadoAtual)
							{
								novo.setEstadoAtual(custoReal[i][a + 1]);
								novo.setFaltaAteObjetivo(custosEmLinhaReta[custoReal[i][a + 1]][destino]);
							}
						}
					}
					else if(a == 1)
					{
						if(estadoAtual.getEstadoAtual() == origem) // Significa que ele não tem pai.
						{
								novo.setEstadoAtual(custoReal[i][a - 1]);
								novo.setFaltaAteObjetivo(custosEmLinhaReta[custoReal[i][a - 1]][destino]);
						}
						if(estadoAtual.noPai != null)
						{
							if(custoReal[i][a - 1] != estadoAtual.noPai.estadoAtual)
							{
								novo.setEstadoAtual(custoReal[i][a - 1]);
								novo.setFaltaAteObjetivo(custosEmLinhaReta[custoReal[i][a - 1]][destino]);
							}
						}
					}
					novo.setNoPai(estadoAtual);
					novo.setDistanciaPercorrida(getDistanciaPercorrida(novo));
					novo.gerarCustoAtual();
					novosNos.add(novo);
				}
			}
			}
		return novosNos;
	}
	
	public static ArrayList<Linhas> getLinha(int posicao)
	{
		ArrayList<Linhas> linhas = new ArrayList<Linhas>();
		if(estacaoAmarela.contains(posicao))
			linhas.add(Linhas.Amarela);
		if(estacaoAzul.contains(posicao))
			linhas.add(Linhas.Azul);
		if(estacaoVerde.contains(posicao))
			linhas.add(Linhas.Verde);
		if(estacaoVermelha.contains(posicao))
			linhas.add(Linhas.Vermelha);
			
		return linhas;
	}
	
	public static int getDistanciaPercorrida(No estacaoAtual)
	{
		int somatorio = 0;
		while(estacaoAtual.noPai != null)
		{
			somatorio += getCustoEntreEstacoes(estacaoAtual.estadoAtual, estacaoAtual.noPai.estadoAtual);
			estacaoAtual = estacaoAtual.noPai;
		}
		return somatorio;
	}
	
	public static int getCustoEntreEstacoes(int estacao_a, int estacao_b)
	{
		for(int i = 0; i < 17; i++)
		{
			for(int a = 0 ; a < 2 ; a++)
			{
				if(a == 0)
				{
					if((custoReal[i][a] == estacao_a && custoReal[i][a + 1] == estacao_b) ||
					   (custoReal[i][a + 1] == estacao_a && custoReal[i][a] == estacao_b))
					{
						return custoReal[i][2];
					}
				}
				
				if(a == 1)
				{
					if((custoReal[i][a] == estacao_a && custoReal[i][a - 1] == estacao_b) ||
					   (custoReal[i][a - 1] == estacao_a && custoReal[i][a] == estacao_b))
					{
						return custoReal[i][2];
					}
				}
			}
		}
		return 0;
	}
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<No> ordenaEstados(ArrayList<No> estados)
	{
		
		 Collections.sort(estados, new Comparator() {
	         public int compare(Object o1, Object o2) {
	        	 No e1 = (No) o1;
	        	 No e2 = (No) o2;
	             return e1.getCustoAtual() < e2.getCustoAtual() ? -1 : (e1.getCustoAtual() < e2.getCustoAtual() ? +1 : 0);
	         }
	     });
		 
		 return estados;
	}

	public static No getEstadoInicial()
	{
		No novo = new No();
		novo.setDistanciaPercorrida(0);
		novo.setFaltaAteObjetivo(custosEmLinhaReta[origem][destino]);
		novo.gerarCustoAtual();
		novo.setEstadoAtual(origem);
		
		return novo;
	}
	
	// Heurística: Quanto falta para chegar no meu objetivo.
	// Custo: distância percorrida.
	// Custo Total : custo + heurística
	
	public static int[][] getCustosEmLinhaReta()
	{
		int[][] custos_emLinhaReta = new int[][]
				{
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 11, 20, 27, 40, 43, 39, 28, 18, 10, 18, 30, 30, 32},
			{0, 11, 0, 9, 16, 29, 32, 28, 19, 11, 4, 17, 23, 21, 24},
			{0, 20, 9, 0, 7, 20, 22, 19, 15, 10, 11, 21, 21, 13, 18},
			{0, 27, 16, 7, 0, 13, 16, 12, 13, 13, 18, 26, 21, 11, 17},
			{0, 40, 29, 20, 13, 0, 3, 2, 21, 25, 31, 38, 27, 16, 20},
			{0, 43, 22, 22, 16, 3, 0, 4, 23, 28, 33, 41, 30, 17, 20},
			{0, 39, 28, 19, 12, 2, 4, 0, 22, 25, 29, 38, 28, 13, 17},
			{0, 28, 19, 15, 13, 21, 23, 22, 0, 9, 22, 18, 7, 25, 30},
			{0, 18, 11, 10, 13, 25, 28, 25, 9, 0, 13, 12, 12, 23, 28},
			{0, 10, 4, 11, 18, 31, 33, 29, 22, 13, 0, 20, 27, 20, 23},
			{0, 18, 17, 21, 26, 38, 41, 38, 18, 12, 20, 0, 15, 35, 39},
			{0, 30, 23, 21, 21, 27, 30, 28, 7, 12, 27, 15, 0, 31, 37},
			{0, 30, 21, 13, 11, 16, 17, 13, 25, 23, 20, 35, 31, 0, 5},
			{0, 32, 24, 18, 17, 20, 20, 17, 30, 28, 23, 39, 37, 5, 0}
				};
				
		return custos_emLinhaReta;
	}
	
	public static int[][] getCustosReal()
	{
		int[][] custos_real = new int[][]
				{
						{1, 2, 11},
						{2, 3, 9},
						{2, 9, 11},
						{2, 10, 4},
						{3, 4, 7},
						{3, 9, 10},
						{3, 13, 19},
						{4, 5, 14},
						{4, 8, 16},
						{4, 13, 12},
						{5, 6, 3},
						{5, 7, 2},
						{5, 8, 33},
						{8, 9, 10},
						{8, 12, 7},
						{9, 11, 14},
						{13, 14, 5}
				};
		return custos_real;
	}
}
