import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MetroDeParis {
	
	static int[][] custos = getCustos();
	static ArrayList<Integer> estacaoAzul = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
	static ArrayList<Integer> estacaoVermelha = new ArrayList<>(Arrays.asList(11, 9, 3, 13));
	static ArrayList<Integer> estacaoAmarela = new ArrayList<>(Arrays.asList(10, 2, 9, 8, 5, 7));
	static ArrayList<Integer> estacaoVerde = new ArrayList<>(Arrays.asList(12, 8, 4, 13, 14));
	static int custo = 0;
	static int origem = 1;
	static int destino = 2;
	
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
		
		custo = getCustoTotal(estadoAtual);
		System.out.println("Custo Total: " + custo);
		
	}
	
	public static int getCustoTotal(No estadoAtual)
	{
		for(int i = 0; i < 17; i++)
		{
			for(int a = 0; a < 2; a++)
			{
				
				if(verificaSeEntaoNaMesmaLinha(i, a))
					custo += 5;
			}
		}
		
		custo += estadoAtual.getCustoAtual();
		return custo;
	}
	
	public static boolean verificaSeEntaoNaMesmaLinha(int estacaoAtual, int estadoFilho)
	{
		ArrayList<Linhas> linhasEstacaoAtual = getLinha(estacaoAtual);
		ArrayList<Linhas> linhasEstacaoFilho = getLinha(estadoFilho);
	
		for(int i = 0; i < linhasEstacaoAtual.size(); i++)
		{
			for(int a = 0; a < linhasEstacaoFilho.size(); a++)
			{
				if(linhasEstacaoAtual.get(i) == linhasEstacaoFilho.get(a))
					return true;
			}
		}
		return false;
	}
	
	public static ArrayList<No> expande( No estadoAtual)
	{
		ArrayList<No> novosNos = new ArrayList<No>();
		for(int i = 0; i < 17; i++)
		{
			for(int a = 0; a < 2; a++)
			{
				if(custos[i][a] == estadoAtual.getEstadoAtual())
				{
					No novo = new No();
					novo.setEstadoAtual(custos[i][a]);
					novo.setCustoAtual(custos[i][2]);
					novo.setFaltaAteObjetivo(getCustoAteObjetivo(custos[i][a]));
					novo.setNoPai(estadoAtual);
					novo.setDistanciaPercorrida(getDistanciaPercorrida(novo));
				
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
		if(estacaoAtual.getNoPai() != null)
		{
			return estacaoAtual.getDistanciaPercorrida() + getDistanciaPercorrida(estacaoAtual.noPai);
		}
		else 
			return estacaoAtual.getDistanciaPercorrida();
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
	
	public static int getCustoAteObjetivo(int estacaoAtual)
	{
		for(int i = 0; i < 17; i++)
		{
			for(int a = 0; a < 2; a++)
			{
				if((estacaoAtual == i && destino == a) || (estacaoAtual == a && destino == i))
				{
					return custos[i][2];
				}
			}
		}
		return 0;
	}
	
	public static No getEstadoInicial()
	{
		No novo = new No();
		novo.setDistanciaPercorrida(0);
		novo.setCustoAtual(getCustoAteObjetivo(origem));
		novo.setFaltaAteObjetivo(getCustoAteObjetivo(origem));
		novo.setEstadoAtual(origem);
		
		return novo;
	}
	
	public static int[][] getCustos()
	{
		int[][] custos = new int[][]
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
		
		return custos;
	}
}
