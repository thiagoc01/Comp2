import java.util.Scanner;
import java.util.ArrayList;

/**
 * 
 * @author Thiago Castro
 *
 */

public class Exercicio3
{
	/**
	 * Verificar o lexico de strings
	 * <p>
	 * Essa funçao recebe duas palavras e verifica quanto ao tamanho lexical.
	 * </p>
	 *  
	 * @param a Primeira palavra
	 * @param b Segunda palavra
	 * @return 1 se b menor que a, -1 se a menor que b ou 0 se a e b iguais.
	 */
	
	public static int tamanhoLexicalStrings(String a, String b)
	{
		int i = 0;
		while(i < a.length() && i < b.length())
		{
			if (a.charAt(i) > b.charAt(i))
			{
				return 1;
			}
			else if (a.charAt(i) < b.charAt(i))
				return -1;
			else 
				i++;
		}
		return 0;
	}
	
	/**
	 * Ordenar palavras
	 * <p>
	 * Essa funçao recebe um vetor de palavras, ordena de forma crescente e imprime na tela.
	 * </p>
	 *  
	 * @param palavras Vetor de palavras
	 */
	
	public static void ordenaAlfabeticamente(String[] palavras)
	{
		ArrayList<String> resultado = new ArrayList<String>();
		String aux;

		for (int i = 0 ; i < palavras.length ; i++)
		{
			resultado.add(palavras[i]);
		}
		
		for (int i = 0 ; i < resultado.size() ; i++)
		{
			for (int j = i+1 ; j < resultado.size();j++)
			{
			    if (tamanhoLexicalStrings(resultado.get(i),resultado.get(j)) == 1)
			    {
				     aux = resultado.get(i);
				     resultado.set(i, resultado.get(j));
				     resultado.set(j, aux);
				
			    }
			}
		}
		
		
		for(String contador: resultado){
			System.out.println(contador);
		}

	}
	public static void main(String[] args)
	{
		Scanner entrada = new Scanner(System.in);

		System.out.println("Digite algumas palavras: ");

		String palavras = entrada.nextLine();

		String[] vetor = palavras.split(" ");
		
		ordenaAlfabeticamente(vetor);


	}

}