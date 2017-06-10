import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class AlgoritmoGenetico {

	static int contador = 0;
	static final int TAMANHO_D0_ARRAY = 8;
	static List<Estado> populacaoGeral;
	static double mutacao = 0.01;
	
	public static void main(String[] args)
	{
		populacaoGeral = new ArrayList<Estado>();
		geraPopulacaoInicial();
		++contador;
		System.out.println("Geração " + contador);
		imprimir();
		do
		{
			
			if(verificaSeChegouNoObjetivo(populacaoGeral) == false){
				++contador;
				System.out.println("Geração " + contador);
				torneio();
				imprimir();
			}

		}while(verificaSeChegouNoObjetivo(populacaoGeral) == false);
		
		
		System.out.println("Qtd de passos : " + contador);
	}
	
	public static void torneio()
	{
		Random random  = new Random();
		int aleatorio;
		Estado pai, mae;
		List<Estado> maisAptos = new ArrayList<Estado>();
		
		for(int a = 0; a < populacaoGeral.size() ; a++)
		{
			aleatorio = random.nextInt(populacaoGeral.size()); 
			pai = populacaoGeral.get(aleatorio);
			
			aleatorio = random.nextInt(populacaoGeral.size()); 
			mae = populacaoGeral.get(aleatorio);
			
			if(pai.getQtdChoques() <= mae.getQtdChoques())
				maisAptos.add(pai);
			else 
				maisAptos.add(mae);
		}
		crossoverEMutacao(maisAptos);
	}
	
	public static void crossoverEMutacao(List<Estado> maisAptos)
	{
		Random random = new Random();
		String[] mascaraAleatoria = new String[24];
		String[] auxiliar = new String[3];
		String papai = "", mamae = "", elemento1 = "" , elemento2 = "";
		int contador = 0;
		String[] mae, pai;
		Estado filho1, filho2;
		double aleatorio;
		populacaoGeral = new ArrayList<Estado>();
		for(int a = 0; a < 24; a++)
		{
			mascaraAleatoria[a] = Integer.toString(random.nextInt(2));
		}
		
		
		for (int i = 0; i < (maisAptos.size() / 2); i++) // Aqui eu faço em ordem.
		{
			for(int a = 0; a < 8; a++)
			{
				papai += maisAptos.get(i).getEstadoAtual()[a];
				mamae += maisAptos.get(i + 1).getEstadoAtual()[a];
			}
			
			pai = papai.split("");
			mae = mamae.split("");
			contador = 0;
			filho1 = new Estado();
			filho1.setEstadoAtual(new String[8]);
			
			filho2 = new Estado();
			filho2.setEstadoAtual(new String[8]);
			
			for(int b = 0; b < 8; b++)
			{
				for(int c = 0; c < 3; c++)
				{
					if(mascaraAleatoria[contador].equals("1"))
						elemento1 += pai[contador];
					else if(mascaraAleatoria[contador].equals("0"))
						elemento1 += mae[contador];
					++contador;
					
					// mutacao
					aleatorio = random.nextDouble();
					if( aleatorio <= mutacao) 
					{
						String[] elementosSeparados = elemento1.split("");
						if(elementosSeparados[c].equals("1"))
							elementosSeparados[c] = "0";
						else if(elementosSeparados[c].equals("0"))
							elementosSeparados[c] = "1";
						
						elemento1 = "";
						for (String bit : elementosSeparados) {
							elemento1 += bit;
						}
					}
						
					
					
					if(c == 2)
					{
						filho1.estadoAtual[b] = elemento1;
						auxiliar = elemento1.split("");
						
						for(int d = 0; d < 3; d++)
						{
							if(auxiliar[d].equals("1"))
								elemento2 += "0";
							else if(auxiliar[d].equals("0"))
								elemento2 += "1";
							
							//mutação
							aleatorio = random.nextDouble();
							if( aleatorio <= mutacao) 
							{
								String[] elementosSeparados = elemento2.split("");
								if(elementosSeparados[d].equals("1"))
									elementosSeparados[d] = "0";
								else if(elementosSeparados[d].equals("0"))
									elementosSeparados[d] = "1";
								
								elemento2 = "";
								for (String bit : elementosSeparados) {
									elemento2 += bit;
								}
							}
								
							if(d == 2)
								filho2.estadoAtual[b] = elemento2;
						}
						
						elemento1 = "";
						elemento2 = "";
					}
				}
				if(b == 7)
				{
					papai = "";
					mamae = "";
					filho1.setQtdChoques(getQtdChoques(converteArrayParaInteiro(filho1.estadoAtual)));
					filho2.setQtdChoques(getQtdChoques(converteArrayParaInteiro(filho2.estadoAtual)));
					populacaoGeral.add(filho1);
					populacaoGeral.add(filho2);
				}
			}
		}
	
	}
	
	public static boolean verificaSeChegouNoObjetivo(List<Estado> populacaoAtual)
	{
		for (Estado estado : populacaoAtual) {
			if(estado.getQtdChoques() == 0)
				return true;
		}
		
		return false;
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
	
	
	public static void imprimir()
	{
		Integer[] estadoAtual;
		
		for(Estado estado : populacaoGeral)
		{
			estadoAtual =  converteArrayParaInteiro(estado.getEstadoAtual());
			System.out.print("Geração : " +  contador + " (Linha, Coluna) -> ");
			System.out.print("(0, " + estadoAtual[0] +" ) ");
			System.out.print("(1, " + estadoAtual[1] +" ) ");
			System.out.print("(2, " + estadoAtual[2] +" ) ");
			System.out.print("(3, " + estadoAtual[3] +" ) ");
			System.out.print("(4, " + estadoAtual[4] +" ) ");
			System.out.print("(5, " + estadoAtual[5] +" ) ");
			System.out.print("(6, " + estadoAtual[6] +" ) ");
			if(estado.getQtdChoques() == 0)
				System.out.println("(7, " + estadoAtual[7] +" ) -> " + estado.getQtdChoques() + " -> SOLUÇÃO");
			else 
				System.out.println("(7, " + estadoAtual[7] +" ) -> " + estado.getQtdChoques());
		}
	}

	public static int getQtdChoques(Integer[] estado)
	{
		int linha, coluna, choques = 0;
		List<Integer> aux = Arrays.asList(estado);
		for(int a = 0 ; a < TAMANHO_D0_ARRAY; a++)
		{
			for(int b = 0; b < TAMANHO_D0_ARRAY; b++)
			{
				if(b != a){
					linha =  aux.indexOf(estado[b])- aux.indexOf(estado[a]);
					coluna = estado[b] - estado[a];
					
					if(Math.abs(linha) == Math.abs(coluna) || estado[b] == estado[a])
						++choques;
				}
			}
		}
		return choques;
	}
	
	public static Integer[] converteArrayParaInteiro(String[] valores)
	{
		Integer[] valoresInteiros = new Integer[TAMANHO_D0_ARRAY];
		for(int a = 0; a < 8; a++)
		{
			switch (valores[a]) {
			case "000":
				valoresInteiros[a] = 0;
				break;
			case "001":
				valoresInteiros[a] = 1;
				break;
			case "010":
				valoresInteiros[a] = 2;
				break;
			case "011":
				valoresInteiros[a] = 3;
				break;
			case "100":
				valoresInteiros[a] = 4;
				break;
			case "101":
				valoresInteiros[a] = 5;
				break;
			case "110":
				valoresInteiros[a] = 6;
				break;
			case "111":
				valoresInteiros[a] = 7;
				break;
			}
		}
		return valoresInteiros;
	}
	
	public static String[] converteArrayParaBinario(Integer[] valores)
	{
		String[] valoresInteiros = new String[TAMANHO_D0_ARRAY];
		for(int a = 0; a < 8; a++)
		{
			switch (valores[a]) {
			case 0:
				valoresInteiros[a] = "000";
				break;
			case 1:
				valoresInteiros[a] = "001";
				break;
			case 2:
				valoresInteiros[a] = "010";
				break;
			case 3:
				valoresInteiros[a] = "011";
				break;
			case 4:
				valoresInteiros[a] = "100";
				break;
			case 5:
				valoresInteiros[a] = "101";
				break;
			case 6:
				valoresInteiros[a] = "110";
				break;
			case 7:
				valoresInteiros[a] = "111";
				break;
			}
		}
		return valoresInteiros;
	}
	
	
 	public static String[] embaralhaArray(String[] v)
	{
		Random random = new Random();
		
		String[] novoVetor =  new String[TAMANHO_D0_ARRAY];
		for (int a = 0; a < 8; a++) {
			novoVetor[a] = v[a];
		}
		
		for (int i=0; i < novoVetor.length - 1; i++) {

			int j = random.nextInt(novoVetor.length); 
			
			String temp = novoVetor[i];
			novoVetor[i] = novoVetor[j];
			novoVetor[j] = temp;
		}
		return novoVetor;
	}

	public static void geraPopulacaoInicial() 
	{
		String[] auxiliar;
		String[] inicial = new String[TAMANHO_D0_ARRAY];
		inicial[0] = "000";
		inicial[1] = "001";
		inicial[2] = "010";
		inicial[3] = "011";
		inicial[4] = "100";
		inicial[5] = "101";
		inicial[6] = "110";
		inicial[7] = "111";
		
		for(int a = 0 ; a < 500; a++)
		{
			auxiliar = embaralhaArray(inicial);
			Estado estado = new Estado();
			estado.setEstadoAtual(auxiliar);
			estado.setQtdChoques(getQtdChoques(converteArrayParaInteiro(auxiliar)));
			populacaoGeral.add(estado);
		}
	}
}