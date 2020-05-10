import java.util.Scanner;
import java.text.Normalizer;

/**
 * 
 * @author Thiago Castro
 *
 */


public class Main {	
		
	
	private static String entradaUsuario = null;	
		
	
	/**
	 * Remove sinais gráficos
	 * @param texto String
	 * @return String sem acentos
	 */
	public static String removerAcentos(String texto){
		   return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	/**
	 * Recebe entrada do usuário para o ínicio da geração do gráfico
	 */
	
	public void recebeEntrada()
	{
		System.out.println("Digite um estado ou cidade. Para o estado de São Paulo ou do Rio de Janeiro, digite São Paulo estado/Rio de Janeiro estado. "
				+ "Caso queira o total de casos e mortes, digite total: ");
		
		Scanner recebe = new Scanner(System.in);
		
		entradaUsuario = recebe.nextLine().trim().toUpperCase().replace(" ","");
		entradaUsuario = removerAcentos(entradaUsuario);
		
		recebe.close();
	}

	public static void main(String[] args) {
		Main exe = new Main();
		Grafico grafico = new Grafico();
		exe.recebeEntrada();
		
		try
		{
			if (entradaUsuario.intern() == "TOTAL")
			{
				grafico.geraGraficoTotal();
			}
			else if (grafico.buscaCidade(entradaUsuario) == true)
			{
				grafico.geraGraficoCidade(entradaUsuario);
			}
			else if (Estados.valueOf(entradaUsuario).toString() == entradaUsuario.intern())
			{
				grafico.geraGraficoEstado(entradaUsuario);
			}
			
		}
		
		catch (IllegalArgumentException e)
		{
			System.out.println("Digite um estado ou cidade corretos");
			System.out.println(entradaUsuario);
		}


	}

}
