package codigoFonte;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Puzzle {

	public static void main(String[] args) {
		
		HashSet<No> estadosJaVisitados = new HashSet<No>();
        No puzzle = preencherPuzzle();
        No estadoAtual = new No();
        String[][] objetivo = preencherObjetivo();
        List<No> filaDeEstados = new ArrayList<No>();
       
        filaDeEstados.add(puzzle);
        
        do
        {
        	if(!filaDeEstados.isEmpty()){
        		estadoAtual = filaDeEstados.remove(0);
        	}
        	else 
        		break;
        	
        	if( !comparaEstados(estadoAtual.getNoAtual(), objetivo) && ! estadosJaVisitados.contains(estadoAtual))
        	{
        		expande(filaDeEstados, estadoAtual);
        		estadosJaVisitados.add(estadoAtual);
        	}
        	
        	
        }while(!comparaEstados(estadoAtual.getNoAtual(), objetivo) && !filaDeEstados.isEmpty());
        
       if(filaDeEstados.isEmpty())
        {
        	System.out.print("Não existe solução!");
        }
        else 
        {
        	imprimeSolucao(estadoAtual);
        }  
    }
	
	public static void expande(List<No> filaDeEstados, No estadoAtual)
	{
		No novoEstadoDireita = new No();
		No novoestadoEsquerda = new No();
		No novoEstadoCima = new No();
		No novoEstadoBaixo = new No();
		
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
	             if(estadoAtual.getNoAtual()[i][j] == " ")
	             {
	            	 // Posso mover para direita
	            	 if(j != 2)
	            	 {
	            		 novoEstadoDireita.setNoAtual(clonarEstatos(new String[3][3], estadoAtual.getNoAtual()));
	            		 novoEstadoDireita.getNoAtual()[i][j] = novoEstadoDireita.getNoAtual()[i][j + 1];
	            		 novoEstadoDireita.getNoAtual()[i][j + 1] = " ";
	            		 novoEstadoDireita.setNoPai(estadoAtual);
	            		 filaDeEstados.add(novoEstadoDireita); 
	            	 }
	            	// Posso mover para esquerda
	            	 if(j != 0)
	            	 {
	            		 novoestadoEsquerda.setNoAtual(clonarEstatos(new String[3][3], estadoAtual.getNoAtual()));
	            		 novoestadoEsquerda.getNoAtual()[i][j] = novoestadoEsquerda.getNoAtual()[i][j - 1];
	            		 novoestadoEsquerda.getNoAtual()[i][j - 1] = " ";
	            		 novoestadoEsquerda.setNoPai(estadoAtual);
	            		 filaDeEstados.add(novoestadoEsquerda); 
	            	 }
	            	// Posso mover para cima	 
	            	 if(i != 0)
	            	 {
	            		 novoEstadoCima.setNoAtual(clonarEstatos(new String[3][3], estadoAtual.getNoAtual()));
	            		 novoEstadoCima.getNoAtual()[i][j] = novoEstadoCima.getNoAtual()[i - 1][j];
	            		 novoEstadoCima.getNoAtual()[i - 1][j] = " ";
	            		 novoEstadoCima.setNoPai(estadoAtual);
	            		 filaDeEstados.add(novoEstadoCima); 
	            	 }
	            	// Posso mover para baixo
	            	 if(i != 2)
	            	 {
	            		 novoEstadoBaixo.setNoAtual(clonarEstatos(new String[3][3], estadoAtual.getNoAtual()));
	            		 novoEstadoBaixo.getNoAtual()[i][j] = novoEstadoBaixo.getNoAtual()[i + 1][j];
	            		 novoEstadoBaixo.getNoAtual()[i + 1][j] = " ";
	            		 novoEstadoBaixo.setNoPai(estadoAtual);
	            		 filaDeEstados.add(novoEstadoBaixo); 
	            	 }
	             }
            }
        }
			
	}
	
	public static String[][] clonarEstatos(String[][] novoEstado, String[][] estadoAtual)
	{
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            	novoEstado[i][j] = estadoAtual[i][j];
            }
        }
		return novoEstado;
	}
	
	public static boolean comparaEstados(String[][] estadoAtual, String[][] objetivo)
	{
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
             if(estadoAtual[i][j] != objetivo[i][j])
                 return false;
            }
        }
		return true;
	}
	
	public static void imprimirEstadoAtual(String[][] estadoAtual)
	{
		for (int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 2)
                    System.out.println("| " + estadoAtual[i][j]+ " | ");
                else
                    System.out.print("| " + estadoAtual[i][j] + " | ");
            }
        }
		System.out.println("---------------------------------------");
	}
	
	public static void imprimeSolucao(No estadoAtual)
	{
		if(estadoAtual.noPai != null)
		{
			imprimeSolucao(estadoAtual.noPai);
		}
		imprimirEstadoAtual(estadoAtual.noAtual);
	}
	
	public static No preencherPuzzle()
	{
		String[][] puzzle = { { "7", "2", "4" }, { "5", " ", "6" }, { "8", "3", "1" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "5", "6" }, { "8", "7", " " } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "5", "6" }, { "7", "8", " " } };
		//String[][] puzzle = { { "1", "2", "3" }, { "5", "4", " " }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "5", " " }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "2", " " }, { "4", "5", "3" }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "5", "2" }, { "4", " ", "3" }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "5", "2" }, { " ", "4", "3" }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "5", "6" }, { "7", " ", "8" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "5", "6" }, { " ", "7", "8" } };
		//String[][] puzzle = { { "1", "2", "3" }, { " ", "5", "6" }, { "4", "7", "8" } };
		//String[][] puzzle = { { " ", "2", "3" }, { "1", "5", "6" }, { "4", "7", "8" } };
		//String[][] puzzle = { { "2", "3", " " }, { "1", "5", "6" }, { "4", "7", "8" } };
		//String[][] puzzle = { { "2", "3", "6" }, { "1", "5", " " }, { "4", "7", "8" } };
		//String[][] puzzle = { { "2", "3", "6" }, { "1", "5", "8" }, { "4", "7", " " } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "5", " " }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", " ", "5" }, { "7", "8", "6" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "8", "5" }, { "7", " ", "6" } };
		//String[][] puzzle = { { "1", "2", "3" }, { "4", "8", "5" }, { " ", "7", "6" } };
		//String[][] puzzle = { { "1", "2", "3" }, { " ", "8", "5" }, { "4", "7", "6" } };
		//String[][] puzzle = { { " ", "2", "3" }, { "1", "8", "5" }, { "4", "7", "6" } };
        //String[][] puzzle = { { "1", "2", "3" }, { "4", "5", " " }, { "7", "8", "6" } };
	    //String[][] puzzle = { { "1", "2", " " }, { "4", "5", "3" }, { "7", "8", "6" } };
       
		No noAtual = new No();
		noAtual.noAtual = puzzle;
	        
	        return noAtual;
	    }

	public static String[][] preencherObjetivo()
	    {
	        String[][] puzzle = {{" ", "1", "2"}, { "3", "4", "5",}, {"6", "7", "8"}};
	    return puzzle;
	    }

	public static boolean contais(No elemento, List<No> lista)
	  {
		  int contem = 0;
		  
	    for(No estadoAtual: lista)
	    {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	             if(elemento.getNoAtual()[i][j] == estadoAtual.getNoAtual()[i][j])
	                 ++contem;
	            }
	        }
	        if(contem == 9)
	        	return true;
	        contem = 0;
	    }
	      return false;
	  } 
}
