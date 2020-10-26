package br.ufrj.dcc.comp2.thiago.lista4.entidades;

/**
 * Essa classe implementa a entidade gênero literário na biblioteca
 * @author Thiago Castro
 *
 */

public class CategoriaLivro 
{
	private int ID;
	private String nome;
	
	/**
	 * Verifica se o objeto é do tipo CategoriaLivro e possui o ID de um gênero literário cadastrado na biblioteca
	 * @param o o objeto a ser verificado
	 * @return true se possui o ID igual, false c.c.
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (! (o instanceof CategoriaLivro))
			return false;
		if (o == this)
			return true;
		return this.getID() == ( (CategoriaLivro) o).getID();
	}
	
	/**
	 * Altera o hashCode para ser o ID do gênero literário
	 * @return o ID do gênero literário
	 */
	
	@Override
	public int hashCode()
	{
		return ID;
	}
	
	/**
	 * Inicializa todos os campos do gênero literário
	 * @param ID o id designado
	 * @param nome o nome designado
	 */
	
	public CategoriaLivro(int ID, String nome)
	{
		this.ID = ID;
		this.nome = nome;
	}
	
	public CategoriaLivro()
	{
		
	}
	
	/**
	 * Inicializa apenas o ID do gênero literário para fins de teste
	 * @param ID o ID do gênero literário
	 */
	
	public CategoriaLivro(int ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Retorna o nome do gênero literário
	 * @return o nome do gênero literário
	 */
	
	public String getNome()
	{
		return nome;
	}
	
	/**
	 * Retorna o ID do gênero literário
	 * @return o ID do gênero literário
	 */
	
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Configura o ID do gênero literário
	 * @param ID o ID do gênero literário
	 */
	
	public void setID(int ID)
	{
		this.ID = ID;
	}

}
