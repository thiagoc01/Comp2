package br.ufrj.dcc.comp2.thiago.lista4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import br.ufrj.dcc.comp2.thiago.lista4.entidades.Autor;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.CategoriaLivro;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Emprestimo;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Estudante;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Fila;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Livro;

/**
 * Essa classe implementa a central de Dados da biblioteca.
 * <p>
 * 		Por ser uma central, é baseada no padrão Singleton para não permitir cópias e redundância.
 * 		A instância é inicializada no começo do programa.
 * </p>
 * @author Thiago Castro
 *
 */

public class Dados 
{
	private static Dados d;
	
	private HashMap<Integer, Livro> livros = new HashMap<Integer,Livro>();
	private HashMap<Integer, Estudante> estudantes = new HashMap<Integer, Estudante>();
	private HashMap<Integer, Autor> autores = new HashMap<Integer,Autor>();
	private HashMap<Integer, CategoriaLivro> categoriasLivros = new HashMap<Integer, CategoriaLivro>();
	private HashMap<Integer, Emprestimo> emprestimos = new HashMap<Integer, Emprestimo>();
	private ArrayList<Fila> fila = new ArrayList<Fila>();

	private Dados()
	{
		
	}
	
	/**
	 * Carrega o banco de dados no começo do programa na abertura dos arquivos TSV.
	 * <p>
	 * 		É chamado o método recebeLocaisArquivos da classe Menu.
	 * 		O programa não inicializa se não possuir os arquivos para carregamento dos dados.
	 * </p>
	 * @param m instância da classe Menu
	 * @return true se o usuário forneceu os arquivos de forma correta ou se desejou sair do programa.
	 * false se forneceu algum arquivo errado e deseja tentar novamente.
	 */
	
	public boolean carregaDataBase(Menu m)
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		boolean verifica = false;
		String escolha;
		
		while (!verifica)
		{
			if (m.recebeLocaisArquivos())
				verifica = true;
			else
			{
				System.out.println("Se deseja sair, digite sair. Se deseja continuar, aperte ENTER.");
				escolha = entrada.nextLine();
				while (escolha.trim().toLowerCase().intern() != "sair" && escolha.trim().intern() != "")
				{
					System.out.println("Digite sair ou aperte ENTER.");
					escolha = entrada.nextLine();
				}
				if (escolha.trim().toLowerCase().intern() == "sair")
					return true;
			}
		}
		return !verifica;
	}
	
	/**
	 * Baseado no padrão Singleton, retorna a instância da classe Dados se ela existir ou cria se ela não existir.
	 * @return a instância da classe Dados
	 */
	
	public static Dados retornaInstancia()
	{
		if (d == null)
			d = new Dados();
		return d;
	}
	
	/**
	 * Retorna o HashMap contendo os livros da biblioteca
	 * @return o hash para leitura e armazenamento dos livros
	 */

	public HashMap<Integer, Livro> getLivros() 
	{
		return livros;
	}
	
	/**
	 * Retorna o HashMap contendo os estudantes da biblioteca
	 * @return o hash para leitura e armazenamento dos estudantes
	 */

	public HashMap<Integer, Estudante> getEstudantes() 
	{
		return estudantes;
	}
	
	/**
	 * Retorna o HashMap contendo os livros da biblioteca
	 * @return o hash para leitura e armazenamento dos livros
	 */

	public HashMap<Integer, CategoriaLivro> getCategoriasLivros()
	{
		return categoriasLivros;
	}
	
	/**
	 * Retorna o HashMap contendo os autores da biblioteca
	 * @return o hash para leitura e armazenamento dos autores
	 */

	public HashMap<Integer, Autor> getAutores() 
	{
		return autores;
	}
	
	/**
	 * Retorna o HashMap contendo os empréstimos da biblioteca
	 * @return o hash para leitura e armazenamento dos empréstimos
	 */
	
	public HashMap<Integer, Emprestimo> getEmprestimos() 
	{
		return emprestimos;
	}
	
	/**
	 * Retorna o ArrayList contendo a fila da biblioteca
	 * @return o array para leitura e armazenamento dos estudantes e livros na fila
	 */
	
	public ArrayList<Fila> getFila()
	{
		return fila;
	}
	

}
