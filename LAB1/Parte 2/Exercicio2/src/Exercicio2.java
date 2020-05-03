import java.util.Scanner;
import java.lang.Math;

/**
 * 
 * @author Thiago Castro
 *
 */


public class Exercicio2 {
	
	/**
	 * Calculo do angulo de dois vetores
	 * <p>
	 * Utiliza a formula de angulo de vetores para o calculo. ( cos(x) = Produto interno entre v1 e v2 / modulo de v1 * modulo de v2)
	 * </p>
	 * @param v1 Vetor 1 de dimensao n 
	 * @param v2 Vetor 2 de dimensao n 
	 * @return O angulo em graus
	 */
	
	public static double calculaAngulo(double[] v1, double[] v2)
	{
		double produtoInterno = calculaProdutoInterno(v1, v2);
		
		double angulo = Math.acos(produtoInterno / (calculaModulo(v1) * calculaModulo(v2)));		
		
		return Math.toDegrees(angulo);			
	}
	
	/**
	 * Calculo do modulo do vetor
	 * <p>
	 * Eleva cada coordenada ao quadrado e retira a raiz quadrada disso.
	 * </p>
	 * @param v Vetor de dimensao n 
	 * @return O modulo do vetor
	 */
	public static double calculaModulo(double[] v)
	{
		double modulo = 0.0d;
		
		for (int i = 0 ; i < v.length ; i++)
		{
			modulo += Math.pow(v[i], 2);
		}
		
		return Math.sqrt(modulo);
	}
	
	/**
	 * Calculo do produto interno
	 * <p>
	 * Calcula o produto interno pela formula padrao. Multiplica cada coordenada do vetor 1 com o vetor 2 e soma ao resultado final.
	 * </p>
	 * @param v1 Vetor 1 de dimensao n
	 * @param v2 Vetor 2 de dimensao n
	 * @return O produto interno entre dois vetores
	 */
	
	public static double calculaProdutoInterno(double[] v1, double[] v2)
	{
		double produtoInterno = 0.0d ;
		
		for (int i = 0 ; i < v1.length ; i++)
		{
			produtoInterno += v1[i] * v2[i];			
		}
		
		return produtoInterno;
	}

	public static void main(String[] args) {
		
		int n;
		
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("Digite o tamanho dos 2 vetores: ");
		
		n = entrada.nextInt();
		
		double[] v1 = new double[n];
		
		double[] v2 = new double[n];
		
		System.out.println("Digite os valores do primeiro vetor: ");
		
		for (int i = 0 ; i < n ; i++)
		{
			v1[i] = entrada.nextDouble();
		}
		
        System.out.println("Digite os valores do segundo vetor: ");
		
		for (int i = 0 ; i < n ; i++)
		{
			v2[i] = entrada.nextDouble();
		}
		
		System.out.println("O ângulo entre os vetores é de: " + calculaAngulo(v1,v2));				
		

	}

}
