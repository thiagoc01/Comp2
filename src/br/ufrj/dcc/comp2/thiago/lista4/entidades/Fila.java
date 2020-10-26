package br.ufrj.dcc.comp2.thiago.lista4.entidades;

/**
 * Essa classe implementa a entidade fila na biblioteca
 * @author Thiago Castro
 *
 */

public class Fila 
{
	private int idLivro;
	private int idEstudante;
	private int posicao;
	
	/**
	 * Verifica se o objeto é do tipo Fila e possui o ID de um livro cadastrado na biblioteca ou ID de um estudante
	 * @param o o objeto a ser verificado
	 * @return true se possui o ID igual, false c.c.
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (! (o instanceof Fila))
			return false;
		if (o == this)
			return true;
		return this.getIdLivro() == ( (Fila) o).getIdLivro() || this.getIdEstudante() == ( (Fila) o).getIdEstudante();
	}
	
	/**
	 * Inicializa todos os campos da fila
	 * @param idLivro o id do livro na fila
	 * @param idEstudante o id do estudante interessado no livro
	 * @param posicao a posicao do estudante na fila
	 */
	
	public Fila(int idLivro, int idEstudante, int posicao)
	{
		this.idLivro = idLivro;
		this.idEstudante = idEstudante;
		this.posicao = posicao;		
	}
	
	/**
	 * Inicializa apenas o ID do livro para fins de teste
	 * @param idLivro o ID do livro
	 */
	
	public Fila(int idLivro)
	{
		this.idLivro = idLivro;
	}
	
	/**
	 * Inicializa apenas o ID do estudante para fins de teste
	 * @param idEstudante o ID do estudante
	 */
	
	public Fila(Integer idEstudante)
	{
		this.idEstudante = idEstudante;
	}
	
	/**
	 * Retorna o id do livro
	 * @return o id do livro que está sendo aguardado
	 */
	
	public int getIdLivro()
	{
		return idLivro;
	}
	
	/**
	 * Retorna o id do estudante
	 * @return o id do estudante que está aguardando o livro
	 */
	
	public int getIdEstudante()
	{
		return idEstudante;
	}
	
	/**
	 * Retorna a posicão
	 * @return a posição do estudante desta instância
	 */
	
	public int getPosicao()
	{
		return posicao;
	}
	
	/**
	 * Reduz a posição do estudante na fila caso ocorra mudança na fila
	 */
	
	public void reduzPosicao()
	{
		posicao--;
	}

}
