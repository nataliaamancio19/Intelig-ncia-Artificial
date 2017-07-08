import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class AlgoritmoGenetico {

	static int contador = 0;
	static final int TAMANHO_D0_ARRAY = 8;
	static final int TAMANHO_DA_POPULACAO = 20;
	static ArrayList<Estado> populacaoGeral;
	static ArrayList<Estado> populacaoFilhos;
	
	public static void main(String[] args)
	{
			contador = 0;
			populacaoGeral = new ArrayList<Estado>();
			populacaoFilhos = new ArrayList<Estado>();
			geraPopulacaoInicial();
			++contador;
			imprimir();
			do
			{
				
				if(verificaSeChegouNoObjetivo(populacaoGeral) == false){
					++contador;
					torneio();
					imprimir();
				}
	
			}while(verificaSeChegouNoObjetivo(populacaoGeral) == false && contador < 5000);
			if(contador == 5000)
			{
				System.out.println("Não achou solução!");
			}
			else
				System.out.println("Qtd de passos : " + contador);
	}
	
	public static void torneio()
	{
		populacaoFilhos = new ArrayList<Estado>();
		Random random  = new Random();
		int aleatorio;
		Estado pai1, pai2, mae1, mae2;
		List<Estado> maisAptos = new ArrayList<Estado>();
		int contador = 0;
		for(int a = 0; a < populacaoGeral.size() / 2 ; a++)
		{
			aleatorio = random.nextInt(populacaoGeral.size()); 
			pai1 = populacaoGeral.get(aleatorio);
			
			aleatorio = random.nextInt(populacaoGeral.size()); 
			pai2 = populacaoGeral.get(aleatorio);
			
			aleatorio = random.nextInt(populacaoGeral.size()); 
			mae1 = populacaoGeral.get(aleatorio);
			
			aleatorio = random.nextInt(populacaoGeral.size()); 
			mae2 = populacaoGeral.get(aleatorio);
			
//			if(pai1.getQtdChoques() <= pai2.getQtdChoques())
//				maisAptos.add(pai1);
//			else 
//				maisAptos.add(pai2);
//			
//			if(mae1.getQtdChoques() <= mae2.getQtdChoques())
//				maisAptos.add(mae1);
//			else 
//				maisAptos.add(mae2);
			

			if(pai1.getQtdChoques() <= pai2.getQtdChoques())
				pai2 = pai1;
			
			
			if(mae1.getQtdChoques() <= mae2.getQtdChoques())
				mae2 = mae1;
			
			crossover(pai2, mae2);
			contador += 2;
		}
		populacaoGeral = (ArrayList<Estado>) populacaoFilhos.clone();
		mutacao();
	}
	
	public static void mutacao()
	{
		Random random = new Random();
		
		int aleatorio = random.nextInt(TAMANHO_DA_POPULACAO);
		String bits = "";
		String[] arrayDeBits = new String[8];
		
		for(int a = 0; a < 8; a++)
		{
			bits += populacaoGeral.get(aleatorio).getEstadoAtual()[a];
		}
	
		arrayDeBits = bits.split("");
		
		aleatorio = random.nextInt(24);
		
		if(arrayDeBits[aleatorio].equals("1"))
			arrayDeBits[aleatorio] = "0";
		else if(arrayDeBits[aleatorio].equals("0"))
			arrayDeBits[aleatorio] = "1";
		
	}
	
	public static void crossover(Estado estado_mae, Estado estado_pai)
	{
		Random random = new Random();
		String[] mascaraAleatoria = new String[24];
		String[] auxiliar = new String[3];
		String papai = "", mamae = "", elemento1 = "" , elemento2 = "";
		int contador = 0;
		String[] mae, pai;
		Estado filho1, filho2;
		double aleatorio;
		
		for(int a = 0; a < 24; a++)
		{
			mascaraAleatoria[a] = Integer.toString(random.nextInt(2));
		}
		
			for(int a = 0; a < 8; a++)
			{
				papai += estado_mae.getEstadoAtual()[a];
				mamae += estado_pai.getEstadoAtual()[a];
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
					populacaoFilhos.add(filho1);
					populacaoFilhos.add(filho2);
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
		
		for(int a = 0 ; a < TAMANHO_DA_POPULACAO; a++)
		{
			auxiliar = embaralhaArray(inicial);
			Estado estado = new Estado();
			estado.setEstadoAtual(auxiliar);
			estado.setQtdChoques(getQtdChoques(converteArrayParaInteiro(auxiliar)));
			populacaoGeral.add(estado);
		}
	}
}