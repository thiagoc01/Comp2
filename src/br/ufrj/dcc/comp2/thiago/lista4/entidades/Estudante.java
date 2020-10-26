package br.ufrj.dcc.comp2.thiago.lista4.entidades;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Essa classe implementa a entidade estudante na biblioteca.
 * @author Thiago Castro
 *
 */

public class Estudante 
{
	private int ID;
	private String nome;
	private String sobreNome;
	private LocalDate dataDeNascimento;
	private String genero;
	private String turma;
	private int pontos;
	private HashSet<Integer> emprestimos = new HashSet<Integer>(0);
	
	/**
	 * Verifica se o objeto é do tipo Estudante e possui o ID de um estudante cadastrado na biblioteca
	 * @param o o objeto a ser verificado
	 * @return true se possui o ID igual, false c.c.
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (! (o instanceof Estudante))
			return false;
		if (o == this)
			return true;
		return this.getID() == ( (Estudante) o).getID();
	}
	
	/**
	 * Altera o hashCode para ser o ID do estudante
	 * @return o ID do estudante
	 */
	
	@Override
	public int hashCode()
	{
		return ID;
	}
	
	/**
	 * Inicializa todos os campos do estudante
	 * @param ID o id designado
	 * @param nome o nome designado
	 * @param sobreNome o sobrenome designado
	 * @param dataDeNascimento a data de nascimento do estudante
	 * @param genero o sexo do estudante
	 * @param turma a turma do estudante
	 * @param pontos a nota do estudante de 0 a 1000 pontos
	 */
	
	public Estudante(int ID, String nome, String sobreNome, LocalDate dataDeNascimento, String genero, String turma, int pontos)
	{
		this.ID = ID;
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.dataDeNascimento = dataDeNascimento;
		this.genero = genero;
		this.turma = turma;
		this.pontos = pontos;
	}
	
	/**
	 * Inicializa apenas o ID do estudante para fins de teste
	 * @param ID o ID do estudante
	 */
	
	public Estudante(int ID)
	{
		this.ID = ID;
	}
	
	/**
	 * Retorna o ID do estudante
	 * @return o ID do estudante
	 */
	
	public int getID() 
	{
		return ID;
	}
	
	/**
	 * Retorna o nome do estudante
	 * @return o nome do estudante
	 */
	
	public String getNome() 
	{
		return nome;
	}
	
	/**
	 * Retorna o sobrenome do estudante
	 * @return o sobrenome do estudante
	 */
	
	public String getSobreNome() 
	{
		return sobreNome;
	}
	
	/**
	 * Retorna a data de nascimento do estudante
	 * @return a data de nascimento do estudante
	 */
	
	public LocalDate getDataDeNascimento() 
	{
		return dataDeNascimento;
	}
	
	/**
	 * Retorna o sexo do estudante
	 * @return o sexo do estudante
	 */
	
	public String getGenero()
	{
		return genero;
	}
	
	/**
	 * Retorna a turma do estudante
	 * @return a turma do estudante
	 */
	
	public String getTurma() 
	{
		return turma;
	}
	
	/**
	 * Retorna a nota do estudante
	 * @return a nota do estudante
	 */
	
	public int getPontos() 
	{
		return pontos;
	}
	
	/**
	 * Altera a nota do estudante
	 * @param pontos a nota do estudante
	 */
	
	public void setPontos(int pontos) 
	{
		this.pontos = pontos;
	}
	
	/**
	 * Retorna o hash contendo os IDs dos livros que o estudante pegou emprestado
	 * @return o hash com nenhum, um ou dois IDs
	 */
	
	public HashSet<Integer> getEmprestimos()
	{
		return emprestimos;
	}
	
	/**
	 * Retorna o número de empréstimos que o estudante possui
	 * @return o tamanho do HashSet de empréstimos, que é equivalente ao número de empréstimos
	 */
	
	public int getNumEmprestimos()
	{
		return emprestimos.size();
	}
	
	

}
