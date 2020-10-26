package br.ufrj.dcc.comp2.thiago.lista4.entidades;

/**
 * Essa classe implementa a entidade livro na biblioteca
 * @author Thiago Castro
 *
 */

public class Livro
{
	private int ID;
	private String nome;
	private int numPaginas;
	private int pontos;
	private Autor autor = new Autor();
	private CategoriaLivro tipo = new CategoriaLivro();
	private int exemplaresDisponiveis = 2;
	
	/**
	 * Verifica se o objeto é do tipo Livro e possui o ID de um livro cadastrado na biblioteca
	 * @param o o objeto a ser verificado
	 * @return true se possui o ID igual, false c.c.
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (! (o instanceof Livro))
			return false;
		if (o == this)
			return true;
		return this.getID() == ( (Livro) o).getID();
	}
	
	/**
	 * Altera o hashCode para ser o ID do Livro
	 * @return o ID do livro
	 */	
	
	@Override
	public int hashCode()
	{
		return ID;
	}
	
	/**
	 * Inicializa todos os campos do livro
	 * @param ID o id designado
	 * @param nome o nome do livro
	 * @param numPaginas o número de páginas do livro
	 * @param pontos a pontuação do livro
	 * @param idAutor o id do autor do livro
	 * @param tipoLivro o gênero literário do livro
	 */	
	
	public Livro(int ID, String nome, int numPaginas, int pontos, int idAutor, int tipoLivro)
	{
		this.ID = ID;
		this.nome = nome;
		this.numPaginas = numPaginas;
		this.pontos = pontos;
		this.autor.setID(idAutor);
		this.tipo.setID(tipoLivro);
	}
	
	/**
	 * Inicializa apenas o ID do livro para fins de teste
	 * @param ID o ID do livro
	 */
	
	public Livro(int ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Retorna o ID do livro
	 * @return o ID do livro
	 */
	
	public int getID() 
	{
		return ID;
	}
	
	/**
	 * Configura o ID do livro
	 * @param id o ID do livro
	 */
	
	public void setID(int id) 
	{
		ID = id;
	}
	
	/**
	 * Retorna o nome do livro
	 * @return o nome do livro
	 */

	public String getNome() 
	{
		return nome;
	}
	
	/**
	 * Retorna a quantidade de pontos do livro
	 * @return a quantidade de pontos
	 */

	public int getPontos()
	{
		return pontos;
	}
	
	/**
	 * Retorna a quantidade de páginas do livro
	 * @return a quantidade de páginas
	 */

	public int getNumPaginas() 
	{
		return numPaginas;
	}
	
	/**
	 * Retorna o ID do autor do livro
	 * @return o ID do autor
	 */

	public int getAutorID() 
	{
		return autor.getID();
	}
	
	/**
	 * Retorna o nome do autor do livro
	 * @return o nome do autor
	 */
	
	public String getAutorNome() 
	{
		return autor.getNome();
	}
	
	/**
	 * Retorna o sobrenome do autor do livro
	 * @return o sobrenome do autor
	 */
	
	public String getAutorSobreNome() 
	{
		return autor.getSobreNome();
	}
	
	/**
	 * Retorna o gênero literário do livro
	 * @return o gênero literário
	 */

	public int getCategoriaLivroID() 
	{
		return tipo.getID();
	}
	
	/**
	 * Retorna o nome do gênero literário do livro
	 * @return o nome do gênero literário
	 */
	
	public String getCategoriaLivroNome() 
	{
		return tipo.getNome();
	}
	
	/**
	 * Retorna o número de exemplares disponível na biblioteca
	 * @return a quantidade de livros para empréstimo
	 */
	
	public int getNumExemplares()
	{
		return exemplaresDisponiveis;
	}
	
	/**
	 * Reduz em uma unidade o número de exemplares disponível para empréstimo
	 */
	
	public void reduzNumExemplares()
	{
		if (exemplaresDisponiveis == 0)
			return;
		exemplaresDisponiveis--;
	}
	
	/**
	 * Aumenta em uma unidade o número de exemplares disponível para empréstimo
	 */
	
	public void aumentaNumExemplares()
	{
		if (exemplaresDisponiveis == 2)
			return;
		exemplaresDisponiveis++;
	}
}
