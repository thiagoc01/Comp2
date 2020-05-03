public class Exercicio3 {

	public static void main(String[] args) {
		Pilha<Integer> p = new Pilha<Integer>(10);
		
		p.novoElemento(10);
		p.novoElemento(9);
		p.novoElemento(8);
		p.novoElemento(7);
		p.novoElemento(6);
		p.novoElemento(5);
		p.novoElemento(4);
		p.novoElemento(3);
		p.novoElemento(2);
		p.novoElemento(1);
		
		while(!p.estaVazia())
		{
			System.out.println(p.ultimoElemento());
		}
		
		p.ultimoElemento();
		
		

	}

}
