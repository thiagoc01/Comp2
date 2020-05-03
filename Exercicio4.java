import java.time.DateTimeException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 
 * @author Thiago Castro
 *
 */

public class Exercicio4
{
	/**
	 * Data de aniversario
	 * <p>
	 * Essa funçao recebe dia, mes e ano(a partir de 01/01/1900), calcula a idade de uma pessoa e imprime na tela.
	 * </p> 
	 */
	
	public static void main(String[] args)
	{
		Scanner entrada = new Scanner(System.in);
		System.out.println("Digite uma data(dia, mes e ano) sem as barras: ");
		int[] guardaData = new int[3];
		boolean fim = false;
		while (!fim)
		{
			try
			{
				guardaData[0] = entrada.nextInt();
				guardaData[1] = entrada.nextInt();
				guardaData[2] = entrada.nextInt();
				while (LocalDate.of(guardaData[2], guardaData[1], guardaData[0]).isBefore(LocalDate.ofYearDay(1900, 01)) || LocalDate.of(guardaData[2], guardaData[1], guardaData[0]).isAfter(LocalDate.now()))
				{
					System.out.println("Erro de entrada. Digite uma data compreendida entre "  + LocalDate.of(1900, 01, 01).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " e " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					System.out.println("Digite uma data(dia, mes e ano) valida: ");
					guardaData[0] = entrada.nextInt();
					guardaData[1] = entrada.nextInt();
					guardaData[2] = entrada.nextInt();
				}
				LocalDate dataUsuario = LocalDate.of(guardaData[2], guardaData[1], guardaData[0]);
				long anos = dataUsuario.until(LocalDate.now(), ChronoUnit.YEARS);
				long meses = dataUsuario.until(LocalDate.now(), ChronoUnit.MONTHS);
				System.out.printf("Idade: %d anos e %d meses", anos, meses % 12);
				fim = true;

			}
			catch (InputMismatchException d)
			{
				System.out.println("Digite a data apenas com numeros e espacos.");
				entrada.next();
			}
			catch (DateTimeException e)
			{
				System.out.println("Digite uma data valida.");
			}

		}

	}
	
}