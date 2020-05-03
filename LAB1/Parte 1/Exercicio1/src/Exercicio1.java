import java.util.Scanner;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * @author Thiago Castro
 */

public class Exercicio1{

    /**
     * Distancia entre pontos
     * <p>
     *     A funcao nao recebe argumentos, porem recebe como entrada coordenadas de dois pontos e retorna a distancia
     *     entre eles.
     * </p>
     * @return Distancia entre pontos
     */
    public static double distanciaEntrePontos()
    {
        double x1, y1, x2, y2;
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite as coordenadas de dois pontos no plano: ");
        x1 = entrada.nextDouble();
        y1 = entrada.nextDouble();
        x2 = entrada.nextDouble();
        y2 = entrada.nextDouble();

        return sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
    }
    public static void main(String[] args)
    {
        double dist;
        dist = distanciaEntrePontos();
        System.out.printf("A distancia entre os pontos é: %.2f", dist);

    }
}
