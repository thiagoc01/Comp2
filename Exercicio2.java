import java.util.Scanner;

/**
 *@author Thiago Castro
 */

public class Exercicio2
{
	/**
     * M�ia de n n�meros
     * <p>
     	A funcao recebe um inteiro n e um vetor de n numeros. Ela retorna a media deles.
      </p>
     *@param n Qtd. de numeros
     *@param vetor Lista de com n numeros
     *@return Media aritmetica
     */
    public static float media(int n, float[] vetor)
    {
        int i;
        float resultado = 0;

        for (i = 0 ; i < n ; i++)
        {
            resultado += vetor[i];
        }

        return resultado / n;
    }
    public static void main(String[] args)
    {
        Scanner entrada = new Scanner(System.in);

        System.out.println("Digite a quantidade de números: ");

        int n = entrada.nextInt();

        System.out.printf("Digite %d números: ", n);
        entrada = new Scanner(System.in);

        float[] vetor = new float[n];

        for (int i = 0 ; i < n ; i++)
        {
            vetor[i] = entrada.nextFloat();
        }

        System.out.println("A média desses números é: " + media(n,vetor));


    }
}