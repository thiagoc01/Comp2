package br.ufrj.dcc.comp2.thiago.lista4.entidades;

/**
 * Essa classe implementa a entidade de um autor na biblioteca.
 * @author Thiago Castro
 *
 */

public class Autor 
{
	private int ID;
	private String nome;
	private String sobreNome;
	
	/**
	 * Verifica se o objeto é do tipo Autor e possui o ID de um autor cadastrado na biblioteca
	 * @param o o objeto a ser verificado
	 * @return true se possui o ID igual, false c.c.
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (! (o instanceof Autor))
			return false;
		if (o == this)
			return true;
		return this.getID() == ( (Autor) o).getID();
	}
	
	/**
	 * Altera o hashCode para ser o ID do Autor
	 * @return o ID do autor
	 */
	
	@Override
	public int hashCode()
	{
		return ID;
	}
	
	/**
	 * Inicializa todos os campos do autor
	 * @param ID o id designado
	 * @param nome o nome designado
	 * @param sobreNome o sobrenome designado
	 */
	
	public Autor(int ID, String nome, String sobreNome)
	{
		this.ID = ID;
		this.nome = nome;
		this.sobreNome = sobreNome;
	}
	
	public Autor()
	{
		
	}
	
	/**
	 * Inicializa apenas o ID do autor para fins de teste
	 * @param ID o ID do autor
	 */
	
	public Autor(int ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Retorna o ID do autor
	 * @return o ID do autor
	 */
	
	public int getID() 
	{
		return ID;
	}
	
	/**
	 * Configura o ID do autor
	 * @param iD o ID do autor
	 */
	
	public void setID(int iD) 
	{
		ID = iD;
	}
	
	/**
	 * Retorna o nome do autor
	 * @return o nome do autor
	 */

	public String getNome() 
	{
		return nome;
	}
	
	/**
	 * Retorna o sobrenome do autor
	 * @return o sobrenome do autor
	 */

	public String getSobreNome() 
	{
		return sobreNome;
	}

}
