package br.ufrj.dcc.comp2.thiago.lista4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

import br.ufrj.dcc.comp2.thiago.lista4.entidades.Autor;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.CategoriaLivro;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Emprestimo;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Estudante;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Fila;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Livro;

/**
 * Essa classe implementa os métodos para a criação dos arquivos TSV para persistência dos dados, visando o padrão Memento.
 * Eles ficam na pasta dados no diretório raiz do programa.
 * @author Thiago Castro
 *
 */

public class Serializadora {

	private final String separador = File.separator;

	private Dados centralDados = Dados.retornaInstancia();
	
	/**
	 * Cria a pasta dados se ela não existir
	 */

	public void criaPastaDados() {
		try {
			File pasta = new File(".." + separador + "dados" + separador);

			if (!pasta.exists()) {
				pasta.mkdir();
			}
		} catch (SecurityException e) {
			System.out.println("Erro ao criar o diretório dados.");
		}
	}
	
	/**
	 * Gera o arquivo books.tsv com os atributos de Livro
	 */

	public void geraLivrosTSV() {
		try {
			File arq = new File(".." + separador + "dados" + separador + "books.tsv");

			FileOutputStream saida = new FileOutputStream(arq);

			PrintStream ps = new PrintStream(saida);
			ps.print("bookId" + "\t" + "name" + "\t" + "pagecount" + "\t" + "point" + "\t" + "authorId" + "\t"
					+ "typeId" + "\n");

			Set<Integer> s = centralDados.getLivros().keySet();
			Livro livro;
			Iterator<Integer> i = s.iterator();

			while (i.hasNext()) {
				livro = centralDados.getLivros().get(i.next());

				ps.print(livro.getID() + "\t" + livro.getNome() + "\t" + livro.getNumPaginas() + "\t"
						+ livro.getPontos() + "\t" + livro.getAutorID() + "\t" + livro.getCategoriaLivroID() + "\n");
			}

			System.out.println("Arquivo gerado.");
			ps.close();

		} catch (IOException e) {
			System.out.println("Erro ao gerar o arquivo books.tsv");
		}

	}
	
	/**
	 * Gera o arquivo students.tsv com os atributos de Estudante
	 */

	public void geraEstudantesTSV() {
		try {
			File arq = new File(".." + separador + "dados" + separador + "students.tsv");

			FileOutputStream saida = new FileOutputStream(arq);

			PrintStream ps = new PrintStream(saida);
			ps.print("studentId" + "\t" + "name" + "\t" + "surname" + "\t" + "birthdate" + "\t" + "gender" + "\t"
					+ "class" + "\t" + "point" + "\n");

			Set<Integer> s = centralDados.getEstudantes().keySet();
			Estudante estudante;
			Iterator<Integer> i = s.iterator();

			while (i.hasNext()) {
				estudante = centralDados.getEstudantes().get(i.next());

				ps.print(estudante.getID() + "\t" + estudante.getNome() + "\t" + estudante.getSobreNome() + "\t"
						+ estudante.getDataDeNascimento().toString() + "\t" + estudante.getGenero() + "\t"
						+ estudante.getTurma() + "\t" + estudante.getPontos() + "\n");
			}

			System.out.println("Arquivo gerado.");
			ps.close();

		} catch (IOException e) {
			System.out.println("Erro ao gerar o arquivo students.tsv");
		}

	}
	
	/**
	 * Gera o arquivo authorsFull.tsv com os atributos de Autor
	 */

	public void geraAutoresTSV() {
		try {
			File arq = new File(".." + separador + "dados" + separador + "authorsFull.tsv");

			FileOutputStream saida = new FileOutputStream(arq);

			PrintStream ps = new PrintStream(saida);
			ps.print("autor_id" + "\t" + "name" + "\t" + "surname" + "\n");

			Set<Integer> s = centralDados.getAutores().keySet();
			Autor autor;
			Iterator<Integer> i = s.iterator();

			while (i.hasNext()) {
				autor = centralDados.getAutores().get(i.next());

				ps.print(autor.getID() + "\t" + autor.getNome() + "\t" + autor.getSobreNome() + "\n");
			}

			System.out.println("Arquivo gerado.");
			ps.close();

		} catch (IOException e) {
			System.out.println("Erro ao gerar o arquivo authorsFull.tsv");
		}

	}
	
	/**
	 * Gera o arquivo types.csv com os atributos de CategoriaLivro
	 */

	public void geraCategoriasLivrosTSV() {
		try {
			File arq = new File(".." + separador + "dados" + separador + "types.tsv");

			FileOutputStream saida = new FileOutputStream(arq);

			PrintStream ps = new PrintStream(saida);
			ps.print("typeId" + "\t" + "name" + "\n");

			Set<Integer> s = centralDados.getCategoriasLivros().keySet();
			CategoriaLivro genero;
			Iterator<Integer> i = s.iterator();

			while (i.hasNext()) {
				genero = centralDados.getCategoriasLivros().get(i.next());

				ps.print(genero.getID() + "\t" + genero.getNome() + "\n");
			}

			System.out.println("Arquivo gerado.");
			ps.close();

		} catch (IOException e) {
			System.out.println("Erro ao gerar o arquivo types.tsv");
		}
	}
	
	/**
	 * Gera o arquivo borrows.tsv com os atributos de Emprestimo
	 */

	public void geraEmprestimosTSV()
	{	
		DateTimeFormatter diaMesAnoHoraMinuto = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		try
		{
			File arq = new File(".." + separador + "dados" + separador + "borrows.tsv");
			
			FileOutputStream saida = new FileOutputStream(arq);					
			
			PrintStream ps = new PrintStream(saida);
			ps.print("borrowId" + "\t" + "studentId" + "\t" + "bookId" + "\t" + "takenDate" + "\t"
					+ "broughtDate" + "\n");
			
			Set<Integer> s = centralDados.getEmprestimos().keySet();
			Emprestimo emprestimo;
			Iterator<Integer> i = s.iterator();
			
			while (i.hasNext())
			{
				emprestimo = centralDados.getEmprestimos().get(i.next());
				
				if (emprestimo.getDataEntrega() == null)
					ps.print(emprestimo.getID() + "\t" + emprestimo.getIdEstudante() + "\t" + emprestimo.getIdLivro() + "\t"
							+ emprestimo.getDataRetirada().format(diaMesAnoHoraMinuto) + "\t" + "" + "\n");
				else
				{
					ps.print(emprestimo.getID() + "\t" + emprestimo.getIdEstudante() + "\t" + emprestimo.getIdLivro() + "\t"
							+ emprestimo.getDataRetirada().format(diaMesAnoHoraMinuto) + "\t" 
							+ emprestimo.getDataEntrega().format(diaMesAnoHoraMinuto) + "\n");
				}
			}
	
			System.out.println("Arquivo gerado.");
			ps.close();
			
		}
		catch (IOException e)
		{
			System.out.println("Erro ao gerar o arquivo borrows.tsv");			
		}
		
	}
	
	/**
	 * Gera o arquivo queue.tsv com os atributos de Fila
	 */
	
	public void geraFilaTSV()
	{		
		try
		{
			File arq = new File(".." + separador + "dados" + separador + "queue.tsv");
			
			FileOutputStream saida = new FileOutputStream(arq);					
			
			PrintStream ps = new PrintStream(saida);
			ps.print("bookId" + "\t" + "studentId" + "\t" + "position" + "\n");
			
			Fila fila;
			Iterator<Fila> i = centralDados.getFila().iterator();
			
			while (i.hasNext())
			{
				fila = i.next();
				
				ps.print(fila.getIdLivro() + "\t" + fila.getIdEstudante() + "\t" + fila.getPosicao() + "\n");
			}
	
			System.out.println("Arquivo gerado.");
			ps.close();
			
		}
		catch (IOException e)
		{
			System.out.println("Erro ao gerar o arquivo queue.tsv");			
		}
		
	}

}
