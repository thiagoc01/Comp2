import java.util.Scanner;
import java.text.Normalizer;

/**
 * 
 * @author Thiago Castro
 *
 */


public class Main {	
		
	
	private static String entradaUsuario = null;
	private String localArquivo = "";
		
	
	/**
	 * Remove sinais graficos
	 * @param texto String
	 * @return String sem acentos
	 */
	public static String removerAcentos(String texto){
		   return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	/**
	 * Recebe entrada do usuario para o inicio da geracao do grafico
	 */
	
	public void recebeEntrada()
	{
		System.out.println("Digite um estado ou cidade. Para o estado de São Paulo ou do Rio de Janeiro, digite São Paulo estado/Rio de Janeiro estado. "
				+ "Caso queira o total de casos e mortes, digite total: ");
		
		Scanner recebe = new Scanner(System.in);
		
		entradaUsuario = recebe.nextLine().trim().toUpperCase().replace(" ","");
		entradaUsuario = removerAcentos(entradaUsuario);
		
		System.out.println("Digite o local do arquivo: ");
		
		localArquivo = recebe.nextLine().intern();
		
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
				grafico.geraGraficoTotal(exe.localArquivo);
			}
			else if (grafico.buscaCidade(entradaUsuario, exe.localArquivo) == true)
			{
				grafico.geraGraficoCidade(entradaUsuario, exe.localArquivo);
			}
			else if (Estados.valueOf(entradaUsuario).toString() == entradaUsuario.intern())
			{
				grafico.geraGraficoEstado(entradaUsuario, exe.localArquivo);
			}
			
		}
		
		catch (IllegalArgumentException e)
		{
			System.out.println("Digite um estado ou cidade corretos");
		}


	}

}
