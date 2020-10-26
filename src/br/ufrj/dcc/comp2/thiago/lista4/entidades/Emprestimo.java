package br.ufrj.dcc.comp2.thiago.lista4.entidades;

import java.time.LocalDateTime;

/**
 * Essa classe implementa os métodos importantes para a manipulação de empréstimos e de seus atributos.
 * @author Thiago Castro
 *
 */

public class Emprestimo 
{
	private int ID;
	private int idEstudante;
	private int idLivro;
	private LocalDateTime dataRetirada;
	private LocalDateTime dataEntrega;
	
	/**
	 * Verifica se o objeto é do tipo Emprestimo e possui o ID de um empréstimo cadastrado na biblioteca
	 * @param o o objeto a ser verificado
	 * @return true se possui o ID igual, false c.c.
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (! (o instanceof Emprestimo))
			return false;
		if (o == this)
			return true;
		return this.getID() == ( (Emprestimo) o).getID();
	}
	
	/**
	 * Altera o hashCode para ser o ID do empréstimo
	 * @return o ID do empréstimo
	 */	
	
	@Override
	public int hashCode()
	{
		return ID;
	}
	
	/**
	 * Inicializa todos os campos do empréstimo
	 * @param ID o id designado
	 * @param idEstudante o id do estudante que pegou o livro
	 * @param idLivro o id do livro emprestado
	 * @param dataRetirada a data e hora que o livro foi emprestado
	 * @param dataEntrega a data de entrega caso o empréstimo tenha sido finalizado. 
	 * Se não foi finalizada, dataEntrega é null
	 */	
	
	public Emprestimo(int ID, int idEstudante, int idLivro, LocalDateTime dataRetirada, LocalDateTime dataEntrega)
	{
		this.ID = ID;
		this.idEstudante = idEstudante;
		this.idLivro = idLivro;
		this.dataRetirada = dataRetirada;
		this.dataEntrega = dataEntrega;
	}
	
	/**
	 * Inicializa apenas o ID do empréstimo para fins de teste
	 * @param ID o ID do empréstimo
	 */
	
	public Emprestimo(int ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Retorna o ID do empréstimo
	 * @return o ID do empréstimo
	 */
	
	public int getID() 
	{
		return ID;
	}
	
	/**
	 * Retorna o ID do estudante deste empréstimo
	 * @return o ID do estudante referente a este empréstimo
	 */
	
	public int getIdEstudante() 
	{
		return idEstudante;
	}
	
	/**
	 * Retorna o ID do livro deste empréstimo
	 * @return o ID do livro referente a este empréstimo
	 */

	public int getIdLivro() 
	{
		return idLivro;
	}
	
	/**
	 * Retorna a data e hora de empréstimo do livro
	 * @return a data e hora que o livro foi emprestado
	 */

	public LocalDateTime getDataRetirada() 
	{
		return dataRetirada;
	}
	
	/**
	 * Retorna a data e hora de devolução do livro
	 * <p>
	 * 		Se o empréstimo está em aberto, dataEntrega será null.
	 * 		Se o empréstimo está encerrado, dataEntrega terá data e hora válidas.
	 * </p>
	 * @return a data e hora que o livro foi devolvido ou null se não foi devolvido
	 */

	public LocalDateTime getDataEntrega()
	{
		return dataEntrega;
	}
	
	/**
	 * Ajusta a data e hora de devolução do livro
	 * <p>
	 * 		Se o empréstimo está em aberto, dataEntrega será null.
	 * 		Se o empréstimo está encerrado, dataEntrega terá data e hora válidas.
	 * </p>
	 * @param dataEntrega a data e hora que o livro foi devolvido ou null se não foi devolvido
	 */

	public void setDataEntrega(LocalDateTime dataEntrega) 
	{
		this.dataEntrega = dataEntrega;
	}

}
