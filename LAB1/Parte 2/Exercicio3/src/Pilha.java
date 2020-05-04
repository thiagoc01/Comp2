public class Pilha<T> { // Vetor generico
	
	private T[] vetor;
	
	private int tamanho;
	private int topo = -1;
	
	/**
	 * Construtor
	 * @param n Tamanho da pilha
	 */
	
	public Pilha(int n)
	{
		vetor = (T[]) new Object[n];
		tamanho = n;		
	}
	
	/**
	 * Verificar se uma pilha esta cheia
	 * @return Booleano indicando o status
	 */
	public boolean estaCheia()
	{
		return (topo == tamanho - 1);
	}
	
	/**
	 * Verificar se uma pilha esta vazia
	 * @return Booleano indicando o status
	 */
	
	public boolean estaVazia()
	{
		return (topo == -1);
	}
	
	/**
	 * Adiciona um novo elemento a pilha
	 * <p>
	 * Verifica inicialmente se esta cheia. Se sim, lança uma excecao indicando.
	 * Senao, adiciona o elemento no topo e soma um no indicador de elementos da pilha.
	 * </p>
	 * @param item Elemento a ser adicionado
	 * @throws IllegalThreadStateException Se houver a tentativa de adicionar um elemento, caso a pilha esteja cheia.
	 */
	
	public void novoElemento(T item)
	{
		if (estaCheia() == true)
		{
			throw new IllegalThreadStateException("Pilha cheia");
		}
		topo++;
		vetor[topo] = item;
	}
	
	/**
	 * "Remove" o elemento mais acima na pilha.
	 * <p>
	 * Verifica inicialmente se esta vazia. Se sim, lança uma excecao indicando.
	 * Senao, diminui um espaço da pilha e retorna o elemento mais acima dela.
	 * </p>
	 * @throws IllegalThreadStateException Se houver a tentativa de retirar um elemento, caso a pilha esteja vazia.
	 * @return saida O elemento mais acima
	 */
	public T ultimoElemento()
	{
		if (estaVazia() == true)
		{
			throw new IllegalThreadStateException("Pilha vazia");
		}
		T saida = vetor[topo];
		topo--;
		return saida;
	}

	

}
