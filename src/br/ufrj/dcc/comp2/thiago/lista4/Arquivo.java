package br.ufrj.dcc.comp2.thiago.lista4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import br.ufrj.dcc.comp2.thiago.lista4.entidades.Autor;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.CategoriaLivro;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Emprestimo;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Estudante;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Fila;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Livro;

/**
 * Essa classe implementa métodos para a abertura dos arquivos TSV e para o carregamento dos dados.
 * @author Thiago Castro
 *
 */

public class Arquivo
{
	private HashMap<Integer, String> hash = new HashMap<Integer, String>();
	private ArrayList<String> fila = new ArrayList<String>();
	
	private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
	
	private Dados dados = Dados.retornaInstancia();
	
	/**
	 * Verifica se um arquivo existe antes de chamar a abertura do mesmo
	 * @param localArquivo o caminho do arquivo
	 * @return true se existe, false c.c.
	 */
	
	public boolean verificaExistenciaArquivo(String localArquivo)
	{
		File f = new File(localArquivo);
		try
		{
			@SuppressWarnings({ "unused", "resource" })
			FileReader fr = new FileReader(f);
		}
		catch (FileNotFoundException e) 
		{
			System.out.printf("Arquivo %s não encontrado.\n", f.getName());
			return false;
		}
		return true;
	}
	
	/**
	 * Verifica existência do arquivo queue.tsv
	 * <p>
	 * 		Na primeira execução, o arquivo não está presente. Então, se o usuário passar uma string vazia,
	 * 		ela deve ser tratada de forma diferente. Tecnicamente, a fila não é tão importante, então
	 * 		ela pode ser abdicada.
	 * </p>
	 * @param localArquivo o local do arquivo ou uma string vazia
	 * @return true se a string estiver vazia ou o arquivo existir, false c.c.
	 */
	
	public boolean verificaExistenciaArquivoQueue(String localArquivo)
	{
		if (localArquivo.trim().intern() == "")
			return true;
		
		File f = new File(localArquivo);
		try
		{
			@SuppressWarnings({ "unused", "resource" })
			FileReader fr = new FileReader(f);
		}
		catch (FileNotFoundException e) 
		{
			System.out.printf("Arquivo %s não encontrado.\n", f.getName());
			return false;
		}
		return true;
	}
	
	/**
	 * Abre arquivos TSV passando para um HashMap intermediário
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void abreArquivoTSV(String localArquivo)
	{
		File f = new File(localArquivo);
		String aux = "";
		try
		{
			FileReader fr = new FileReader(f);
			BufferedReader leitor = new BufferedReader(fr);
			
			leitor.readLine();
			
			while (leitor.ready())
			{
				aux = leitor.readLine();
				hash.put(Integer.parseInt(aux.split("\t")[0]), aux);
			}
			leitor.close();		
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Arquivo não encontrado");			
		}
		catch(IOException e)
		{
			System.out.println("Erro ao ler o arquivo");
		}		
	}
	
	/**
	 * Abre o arquivo TSV contendo a fila da biblioteca
	 * <p>
	 * 		Como a ordem importa na fila, é utilizado um ArrayList, que por sua vez 
	 * 		possui métodos de controle de itens diferente de um HashMap.
	 * </p>
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void abreArquivoFilaTSV(String localArquivo)
	{
		File f = new File(localArquivo);
		try
		{
			FileReader fr = new FileReader(f);
			BufferedReader leitor = new BufferedReader(fr);
			
			leitor.readLine();
			
			while (leitor.ready())
			{
				fila.add(leitor.readLine());
			}
			leitor.close();		
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Arquivo não encontrado");			
		}
		catch(IOException e)
		{
			System.out.println("Erro ao ler o arquivo");
		}		
	}
	
	/**
	 * Reseta o hash intermediário após a abertura do arquivo para servir de intermediário para outro
	 */
	
	public void resetaHash()
	{
		hash.clear();
	}
	
	/**
	 * Inicializa o HashMap de autores
	 * @param localArquivo a string contendo o local do arquivo
	 */

	public void recebeAutoresTSV(String localArquivo)
	{
		String[] aux;
		abreArquivoTSV(localArquivo);
		
		int id;
		String nome;
		String sobreNome;
		Autor testeAutor;
		
		Set<Integer> s = hash.keySet();
		
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			aux = hash.get(i.next()).split("\t");
			id = Integer.parseInt(aux[0]);
			nome = aux[1];
			sobreNome = aux[2];
			
			testeAutor = new Autor(id, nome, sobreNome);
			
			if (dados.getAutores().containsValue(testeAutor))
			{
				System.out.println("Um dos autores já está presente na lista.");
				continue;
			}
			
			dados.getAutores().put(id, new Autor(id, nome, sobreNome));
		}
		
		resetaHash();
	}
	
	/**
	 * Inicializa o HashMap de gêneros literários
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void recebeTiposTSV(String localArquivo)
	{
		String[] aux;
		abreArquivoTSV(localArquivo);
		
		int id;
		String nome;
		CategoriaLivro testeTipo;
		
		Set<Integer> s = hash.keySet();
		
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			aux = hash.get(i.next()).split("\t");
			
			id = Integer.parseInt(aux[0]);
			nome = aux[1];
			testeTipo = new CategoriaLivro(id, nome);
			
			if (dados.getCategoriasLivros().containsValue(testeTipo))
			{
				System.out.println("Um dos tipos já está cadastrado.");
				continue;
			}
			
			dados.getCategoriasLivros().put(id, new CategoriaLivro(id, nome));
		}
		
		resetaHash();		
		
	}
	
	/**
	 * Inicializa o HashMap de livros
	 * <p>
	 * 		O método não cadastra livros de autores que não estejam cadastrados ou
	 * 		que de gêneros que também não estejam cadastrados.
	 * </p>
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void recebeLivrosTSV(String localArquivo)
	{
		String[] aux;
		int id;
		String nome;
		int contadorPag;
		int pontos;
		Integer idAutor;
		Integer idTipo;
		Autor testeAutor;
		CategoriaLivro testeTipo;
		Livro testeLivro;
		abreArquivoTSV(localArquivo);
		
		Set<Integer> s = hash.keySet();
		
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			aux = hash.get(i.next()).split("\t");
			
			id = Integer.parseInt(aux[0]);
			nome = aux[1];
			contadorPag = Integer.parseInt(aux[2]);
			pontos = Integer.parseInt(aux[3]);
	
			idAutor = Integer.parseInt(aux[4]);
			idTipo = Integer.parseInt(aux[5]);
			
			testeAutor = new Autor(idAutor);
			testeTipo = new CategoriaLivro(idTipo);
			
			if (!dados.getAutores().containsValue(testeAutor))
			{
				System.out.println("Um dos livros é de um autor que não está cadastrado");
				continue;
			}
			
			if (!dados.getCategoriasLivros().containsValue(testeTipo))
			{
				System.out.println("Um dos livros é de um tipo que não está cadastrado");
				continue;
			}
			
			testeLivro = new Livro(id, nome, contadorPag, pontos, idAutor, idTipo);
			
			if (dados.getLivros().containsValue(testeLivro))
			{
				System.out.println("Um dos livros já está cadastrado na biblioteca.");
				continue;
			}
			dados.getLivros().put(id, new Livro(id, nome, contadorPag, pontos, idAutor, idTipo));
		}
		resetaHash();
	}
	
	/**
	 * Inicializa o HashMap de estudantes
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void recebeEstudantesTSV(String localArquivo)
	{
		String[] aux;
		abreArquivoTSV(localArquivo);
		
		int id;
		String nome;
		String sobreNome;
		LocalDate dataDeNascimento;
		String genero;
		String turma;
		Estudante testeEstudante;
		int pontos;
		
		Set<Integer> s = hash.keySet();
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			aux = hash.get(i.next()).split("\t");
			
			id = Integer.parseInt(aux[0]);
			nome = aux[1];
			sobreNome = aux[2];
			dataDeNascimento = LocalDate.parse(aux[3]);
			genero = aux[4];
			turma = aux[5];
			pontos = Integer.parseInt(aux[6]);
			testeEstudante = new Estudante(id, nome, sobreNome, dataDeNascimento, genero, turma, pontos);
			
			if (dados.getEstudantes().containsValue(testeEstudante))
			{
				System.out.println("Um dos estudantes já está cadastrado.");
				continue;
			}			
			
			dados.getEstudantes().put(id, new Estudante(id, nome, sobreNome, dataDeNascimento, genero, turma, pontos));
		}
		
		resetaHash();
	}
	
	/**
	 * Inicializa o HashMap de empréstimos
	 * <p>
	 * 		O método não cadastra empréstimos de livros ou estudantes que não
	 * 		estejam cadastrados na biblioteca.
	 * </p>
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void recebeEmprestimosTSV(String localArquivo)
	{
		String[] aux;
		abreArquivoTSV(localArquivo);
		
		int id;
		int idEstudante;
		int idLivro;
		LocalDateTime dataRetirada;
		LocalDateTime dataEntrega;
		Livro testeLivro;
		Estudante testeEstudante;
		Emprestimo testeEmprestimo;
		
		Set<Integer> s = hash.keySet();
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			aux = hash.get(i.next()).split("\t");
			
			id = Integer.parseInt(aux[0]);
			idEstudante = Integer.parseInt(aux[1]);
			idLivro = Integer.parseInt(aux[2]);
			
			if (aux[3].replaceAll(" ", "").isBlank())
			{
				System.out.println("Um dos empréstimos não possui data de retirada.");
				continue;
			}
			
			if (aux.length == 4)
			{
				dataEntrega = null;
				dados.getLivros().get(idLivro).reduzNumExemplares();
			}
			else
			{
				dataEntrega = LocalDateTime.parse(aux[4], formatador);
			}
			
			dataRetirada = LocalDateTime.parse(aux[3], formatador);
			
			testeLivro = new Livro(idLivro);
			testeEstudante = new Estudante(idEstudante);
			
			if (!dados.getLivros().containsValue(testeLivro))
			{
				System.out.println("Um dos empréstimos contém um livro que não está cadastrado na biblioteca.");
				continue;
			}
			
			if (!dados.getEstudantes().containsValue(testeEstudante))
			{
				System.out.println("Um dos empréstimos contém um estudante que não está cadastrado na biblioteca.");
				continue;
			}
			
			testeEmprestimo = new Emprestimo(id, idEstudante, idLivro, dataRetirada, dataEntrega);
			
			if (dados.getEmprestimos().containsValue(testeEmprestimo))
			{
				System.out.println("Um dos empréstimos já está cadastrado.");
				continue;
			}
			
			
			dados.getEmprestimos().put(id, new Emprestimo(id, idEstudante, idLivro, dataRetirada, dataEntrega));
		}
		
		resetaHash();
	}
	
	/**
	 * Inicializa o ArrayList de fila
	 * @param localArquivo a string contendo o local do arquivo
	 */
	
	public void recebeFilaTSV(String localArquivo)
	{
		String[] aux;
		abreArquivoFilaTSV(localArquivo);
		
		int idLivro;
		int idEstudante;
		int posicao;
		
		Iterator<String> i = fila.iterator();
		
		while (i.hasNext())
		{
			aux = i.next().split("\t");
			
			idLivro = Integer.parseInt(aux[0]);
			idEstudante = Integer.parseInt(aux[1]);
			posicao = Integer.parseInt(aux[2]);

			
			dados.getFila().add(new Fila(idLivro, idEstudante, posicao));
		}
		
		fila.clear();		
		
	}

}
