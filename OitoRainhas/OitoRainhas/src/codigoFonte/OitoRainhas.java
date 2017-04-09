package codigoFonte;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class OitoRainhas {

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
			System.out.println("Estou tentando");
		}while(getQtdChoques(estadoAtual.getEstadoAtual()) != 0);
		System.out.println("encontrei a solução");
		
		for(int a = 0; a <= 8; a++ )
		{
			System.out.println( "Linha :" + a + " Coluna: " + estadoAtual.getEstadoAtual()[a]);
		}
		
	}
	
	public static List<Estado> permutaPosicoes(int posicao, Estado estado)
	{
		int aux;
		List<Estado> estadosPermutados = new ArrayList<Estado>();
		
		for(int a = posicao; a < 8; a++)
		{
			Integer[] array = estado.getEstadoAtual().clone();
			aux = array[a + 1];
			array[a + 1] = array[posicao];
			array[posicao] = aux;
			
			Estado novoEstado = new Estado();
			novoEstado.setEstadoAtual(array);
			novoEstado.setEstadoAnterior(estado);
			novoEstado.setQtdChoques(getQtdChoques(array));
			estadosPermutados.add(novoEstado);
		}
		
		return estadosPermutados;
	}
	
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
	
	public static void imprimirTabuleiro(int[] estadoAtual)
	{
		for(int i = 1; i <= 8; i++) // linhas
		{
			for(int a = 1; a <= 8; a++) // colunas
			{
			if(Arrays.asList(estadoAtual).indexOf(estadoAtual[a]) == i && estadoAtual[a] == a)
					System.out.print(" R |");
			else 
					System.out.print("    ");
			}
			System.out.println("\n-------------------------------");
		}
	}

	public static String[][] estadoInicial()
	{
		String[][] tabuleiro = new String[8][8];
		tabuleiro[0][0] = " R ";
		tabuleiro[1][1] = " R ";
		tabuleiro[2][2] = " R ";
		tabuleiro[3][3] = " R ";
		tabuleiro[4][4] = " R ";
		tabuleiro[5][5] = " R ";
		tabuleiro[6][6] = " R ";
		tabuleiro[7][7] = " R ";
		
		return tabuleiro;
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
					
					if(linha == coluna || linha - coluna == 0 || linha + coluna == 0)
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
		Estado estadoInicial = new Estado();
		estadoInicial.setEstadoAtual(inicial);
		estadoInicial.setQtdChoques(getQtdChoques(inicial));
		
		return estadoInicial;
	}
	
}
