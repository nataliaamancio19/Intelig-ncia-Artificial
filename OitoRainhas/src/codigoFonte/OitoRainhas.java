package codigoFonte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class OitoRainhas {

	
	static int contador = 0;
	public static void main(String[] args)
	{
		Estado inicial = getEstadoInicial();
		List<Estado> estadosvisitados = new ArrayList<Estado>();
		List<Estado> todosEstados = new ArrayList<Estado>();
		Estado estadoAtual = new Estado();
		int posicao = 1;
		boolean achou = false;
		
		
		todosEstados.add(inicial);
		
		do
		{
			estadoAtual = todosEstados.remove(0);
			if(getQtdChoques(estadoAtual.getEstadoAtual()) == 0)
			{
				achou = true;
				getQtdChoques(estadoAtual.getEstadoAtual());
			}
			else 
				achou = false;
			
			if(!estadosvisitados.contains(estadoAtual) && achou == false)
			{
				todosEstados.addAll(permutaPosicoes(posicao, estadoAtual));
				estadosvisitados.add(estadoAtual);
				todosEstados = ordenaEstados(todosEstados);
			}

		}while(getQtdChoques(estadoAtual.getEstadoAtual()) != 0);
		
		imprimeSolucao(estadoAtual);
		System.out.println("Qtd de passos : " + contador);
	}
	
	
	public static void imprimeSolucao(Estado estadoAtual)
	{ 
		
		if(estadoAtual.estadoAnterior != null)
		{
			++ contador;
			imprimeSolucao(estadoAtual.estadoAnterior);
		}
		imprimirTabuleiro(estadoAtual);
	}
	
	public static List<Estado> permutaPosicoes(int posicao, Estado estado)
	{
		int aux;
		List<Estado> estadosPermutados = new ArrayList<Estado>();
		
		for(int b = posicao; b < 7; b++)
		{
			for(int a = b + 1; a < 8; a++)
			{
				Integer[] array = estado.getEstadoAtual().clone();
				aux = array[b];
				array[b] = array[a];
				array[a] = aux;
				
				Estado novoEstado = new Estado();
				novoEstado.setEstadoAtual(array);
				novoEstado.setEstadoAnterior(estado);
				novoEstado.setQtdChoques(getQtdChoques(array));
				estadosPermutados.add(novoEstado);
			}
		}
		
		return estadosPermutados;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Estado> ordenaEstados(List<Estado> estados)
	{
		
		 Collections.sort(estados, new Comparator() {
	         public int compare(Object o1, Object o2) {
	        	 Estado p1 = (Estado) o1;
	        	 Estado p2 = (Estado) o2;
	             return p1.qtdChoques < p2.qtdChoques ? -1 : (p1.qtdChoques > p2.qtdChoques ? +1 : 0);
	         }
	     });
		 
		 return estados;
	}
	
	public static void imprimirTabuleiro(Estado estadoAtual)
	{
		System.out.println("Qtd de choques: " + estadoAtual.qtdChoques);
		for(int i = 1; i <= 8; i++) // linhas
		{
			for(int a = 1; a <= 8; a++) // colunas
			{
			if(estadoAtual.estadoAtual[i] == a)
					System.out.print(" R  ");
			else 
					System.out.print(" *  ");
			}
			System.out.println("\n-------------------------------");
		}
		System.out.println("\n\n");
	}

	public static int getQtdChoques(Integer[] estado)
	{
		int linha, coluna, choques = 0;
		List<Integer> aux = Arrays.asList(estado);
		for(int a = 1 ; a <= 8; a++)
		{
			for(int b = 1; b <= 8; b++)
			{
				if(b != a){
					linha =  aux.indexOf(estado[b])- aux.indexOf(estado[a]);
					coluna = estado[b] - estado[a];
					
					if(Math.abs(linha) == Math.abs(coluna))
						++choques;
				}
			}
		}
		return choques;
	}
	
	public static Estado getEstadoInicial()
	{
		Integer[] inicial = new Integer[9];
		inicial[0] = 0;
		inicial[1] = 1;
		inicial[2] = 2;
		inicial[3] = 3;
		inicial[4] = 4;
		inicial[5] = 5;
		inicial[6] = 6;
		inicial[7] = 7;
		inicial[8] = 8; 
		
		/* FUNCIONOU
		inicial[0] = 0;
		inicial[1] = 1;
		inicial[2] = 3;
		inicial[3] = 5;
		inicial[4] = 2;
		inicial[5] = 4;
		inicial[6] = 7;
		inicial[7] = 8;
		inicial[8] = 6; */
		
		/*inicial[0] = 0;
		inicial[1] = 1;
		inicial[2] = 5;
		inicial[3] = 3;
		inicial[4] = 4;
		inicial[5] = 2;
		inicial[6] = 6;
		inicial[7] = 7;
		inicial[8] = 8; 
		
		inicial[0] = 0;
		inicial[1] = 1;
		inicial[2] = 3;
		inicial[3] = 5;
		inicial[4] = 2;
		inicial[5] = 4;
		inicial[6] = 7;
		inicial[7] = 8;
		inicial[8] = 6; */
		
		Estado estadoInicial = new Estado();
		estadoInicial.setEstadoAtual(inicial);
		estadoInicial.setQtdChoques(getQtdChoques(inicial));
		
		return estadoInicial;
	}
	
}
