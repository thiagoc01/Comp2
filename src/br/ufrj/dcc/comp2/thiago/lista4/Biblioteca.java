package br.ufrj.dcc.comp2.thiago.lista4;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import br.ufrj.dcc.comp2.thiago.lista4.entidades.Autor;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.CategoriaLivro;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Emprestimo;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Estudante;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Fila;
import br.ufrj.dcc.comp2.thiago.lista4.entidades.Livro;

/**
 * Essa classe implementa o sistema principal da Biblioteca.
 * @author Thiago Castro
 *
 */

public class Biblioteca
{	
	private Dados dados = Dados.retornaInstancia();
	
	/**
	 * Verifica se o autor existe na lista de autores. Se não existir, é chamado o método cadastraAutor.
	 * @param idAutor o id do autor para verificação
	 */
	
	private void testaAutor(int idAutor)
	{
		if (dados.getAutores().get(idAutor) != null)
		{
			return;
		}
		cadastraAutor();
	}
	
	/**
	 * Verifica se o gênero literário existe na lista de autores. Se não existir, é chamado o método cadastraGenero.
	 * @param idTipo o id do gênero para verificação
	 */
	
	private void testaGenero(int idTipo)
	{
		if (dados.getCategoriasLivros().get(idTipo) != null)
		{
			return;
		}
		cadastraGenero();
	}
	
	/**
	 * Verifica se o estudante existe na lista de estudantes e imprime na tela se o retorno for falso.
	 * @param idEstudante o id do estudante para verificação
	 * @return true se existir, false c.c.
	 */
	
	private boolean testaEstudante(int idEstudante)
	{
		if (dados.getEstudantes().get(idEstudante) != null)
		{
			return true;
		}
		
		System.out.println("Estudante não cadastrado.");
		return false;
	}
	
	/**
	 * Verifica se o livro existe na lista de livros e imprime na tela se o retorno for falso.
	 * @param idLivro o id do livro para verificação
	 * @return true se existe, false c.c.
	 */
	
	private boolean testaLivro(int idLivro)
	{
		if (dados.getLivros().get(idLivro) != null)
		{
			return true;
		}
		
		System.out.println("Livro não cadastrado.");
		return false;
	}
	
	/**
	 * Verifica se o empréstimo existe na lista de empréstimos e imprime na tela a condição se o retorno for falso.
	 * <p>
	 * 		Se o empréstimo existe e possui a data de entrega em seu registro, é ignorado.
	 * </p>
	 * @param idEmprestimo o id do empréstimo para ser verificado
	 * @return true se existe e está em aberto, false c.c.
	 */
	
	private boolean testaEmprestimo(int idEmprestimo)
	{
		Emprestimo testeEmprestimo = new Emprestimo(idEmprestimo);
		
		if (dados.getEmprestimos().get(idEmprestimo) != null)
		{
			if (dados.getEmprestimos().get(testeEmprestimo.getID()).getDataEntrega() == null)
			{
				return true;
			}
			System.out.println("Esse empréstimo já foi encerrado");
			return false;
		}
		
		System.out.println("Empréstimo não cadastrado.");
		return false;
	}
	
	/**
	 * Verifica se um estudante pode pegar um livro emprestado e também se esse livro está disponível.
	 * <p>
	 * 		Se um estudante possui pontos entre 0 e 500, ele recebe 30 pontos se pegar um livro emprestado.
	 * 		É impresso na tela qualquer problema na verificação de empréstimo.
	 * </p>
	 * @param idAluno o id do aluno a ser verificado
	 * @param idLivro o id do livro a ser verificado
	 * @return maximo se o estudante possui 2 empréstimos no seu cadastro, adicionado se ele pode pegar o livro emprestado,
	 * fila se o livro já está esgotado e identico se ele possui um empréstimo do mesmo livro
	 */
	
	private String verificaPossibilidadeEmprestimo(int idAluno, int idLivro)
	{
		Estudante estudante = dados.getEstudantes().get(idAluno);
		Livro livro = dados.getLivros().get(idLivro);
		
		if (estudante.getNumEmprestimos() == 2)
		{
			return "maximo";
		}
		
		if (estudante.getNumEmprestimos() == 0)
		{
			if (livro.getNumExemplares() == 2 || livro.getNumExemplares() == 1)
			{
				if (estudante.getPontos() >= 0 && estudante.getPontos() <= 500)
				{
					System.out.println("A nota desse estudante foi aumentada com mais 30 pontos.");
					estudante.setPontos(estudante.getPontos() + 30);
				}
				livro.reduzNumExemplares();
				estudante.getEmprestimos().add(livro.getID());
				return "adicionado";
			}
			
			if (livro.getNumExemplares() == 0)
				return "fila";
		}
		
		if (estudante.getNumEmprestimos() == 1)
		{
			if (estudante.getEmprestimos().contains(livro.getID()))
			{
				return "identico";
			}
			
			if (livro.getNumExemplares() == 2 || livro.getNumExemplares() == 1)
			{
				if (estudante.getPontos() >= 0 && estudante.getPontos() <= 500)
				{
					System.out.printf("A nota do estudante %d foi aumentada com mais 30 pontos.\n", estudante.getID());
					estudante.setPontos(estudante.getPontos() + 30);
				}
				
				livro.reduzNumExemplares();
				estudante.getEmprestimos().add(livro.getID());
				return "adicionado";
			}
			
			if (livro.getNumExemplares() == 0)
				return "fila";
		}
		return null;
		
	}
	
	/**
	 * Realiza a devolução de um empréstimo. Após isso, verifica como está a fila para o determinado livro atavés dos método reduzFila
	 * @param idEmprestimo id do empréstimo em aberto
	 */
	
	private void realizaDevolucao(int idEmprestimo)
	{
		Emprestimo emprestimo = dados.getEmprestimos().get(idEmprestimo);
		
		Estudante estudante = dados.getEstudantes().get(emprestimo.getIdEstudante());
		Livro livro = dados.getLivros().get(emprestimo.getIdLivro());
		
		estudante.getEmprestimos().remove(livro.getID());
		livro.aumentaNumExemplares();
		
		String dataHoraAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
		LocalDateTime dataDevolucao = LocalDateTime.parse(dataHoraAtual , DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
		
		emprestimo.setDataEntrega(dataDevolucao);
		
		Fila candidato = new Fila(emprestimo.getIdLivro());
		
		if (dados.getFila().contains(candidato))
		{
			reduzFila(emprestimo.getIdLivro());
			System.out.println("Empréstimo encerrado.");
		}
		else
			System.out.println("Empréstimo encerrado.");
		
		
	}
	
	/**
	 * Reduz a fila se existirem pessoas na espera do livro do qual o empréstimo foi encerrado.
	 * <p>
	 * 		Nesse método, é procurado o primeiro candidato apto e mais a frente na fila através do método verificaCandidatoFila.
	 * 		Se ele está apto, é realizado um deslocamento da fila para frente e aquele estudante é removido da fila.
	 * 		Se não está, itera-se para o próximo candidato se houver.
	 * 		É impresso na tela quem pegou o livro.
	 * </p>
	 * @param idLivro o id do livro para verificação de ocorrência na fila
	 */
	
	private void reduzFila(int idLivro)
	{
		Fila candidato = new Fila(idLivro);
		int posicaoNaLista = 0;
		int posicao = candidato.getPosicao();
		
		for (Fila i : dados.getFila())
		{
			if (i.equals(candidato))
			{
				candidato = i;
				
				if (verificaCandidatoFila(candidato, posicaoNaLista))
				{
					realizaShiftFila(candidato, posicao, idLivro);
					System.out.printf("O estudante com ID %d pegou o livro com ID %d.\n", candidato.getIdEstudante(), candidato.getIdLivro());
					return;
				}
				else
				{
					posicao = candidato.getPosicao();
				}				
			}
			posicaoNaLista++;
		}
		
	}
	
	/**
	 * Verifica se o candidato da fila pode pegar o livro
	 * @param candidato o candidato para verificação
	 * @param posicaoNaLista sua posição na fila para servir de referência na remoção
	 * @return true se ele pode pegar o livro, false c.c.
	 */
	
	private boolean verificaCandidatoFila(Fila candidato, int posicaoNaLista)
	{
		if (verificaPossibilidadeEmprestimo(candidato.getIdEstudante(), candidato.getIdLivro()).intern() == "adicionado")
		{
			realizaOperacaoEmprestimo(candidato.getIdLivro(), candidato.getIdEstudante());
			dados.getFila().remove(posicaoNaLista);
			return true;
		}
		else
			return false;
		
	}
	
	/**
	 * Realiza o deslocamento da fila após a remoção do estudante que pegou o livro.
	 * @param candidato o candidato que pegou o livro
	 * @param posicao a posição na fila para referência de deslocamento
	 * @param idLivro o ID do livro que foi liberado
	 */
	
	public void realizaShiftFila(Fila candidato, int posicao, int idLivro)
	{
		for (Fila i : dados.getFila())
		{
			if (i.equals(candidato))
			{
				if (i.getPosicao() > posicao && i.getIdLivro() == idLivro)
					i.reduzPosicao();
			}
			
		}
	}
	
	/**
	 * Cadastra um autor que não existe na lista de autores se o usuário solicitar o cadastramento de um livro dele.
	 */
	
	private void cadastraAutor()
	{
		System.out.println("Autor não cadastrado.");
		
		System.out.println("Digite o nome do autor:");
		
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		
		String nome;
		String sobrenome;
		int maiorID = Collections.max(dados.getAutores().keySet());

		nome = entrada.nextLine();
		
		System.out.println("Digite o sobrenome do autor: ");
		
		sobrenome = entrada.nextLine();
		
		dados.getAutores().put(maiorID + 1, new Autor(maiorID + 1, nome, sobrenome));
	}
	
	/**
	 * Cadastra um gênero literário que não existe na lista de gêneros se o usuário solicitar o cadastramento de um livro desse tipo.
	 */
	
	private void cadastraGenero()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Gênero literário não cadastrado.");
		
		System.out.println("Digite o nome do gênero literário:");
		
		String nome;
		int maiorID = Collections.max(dados.getCategoriasLivros().keySet());
		
		nome = entrada.nextLine();
		
		dados.getCategoriasLivros().put(maiorID + 1, new CategoriaLivro(maiorID + 1, nome));
	}
	
	/**
	 * Cadastra alunos no banco de dados. O ID a ser atribuído é o do último estudante registrado mais um.
	 */
	
	public void cadastraAlunos()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String nome;
		String sobrenome;
		LocalDate dataDeNascimento;
		String sexo;
		String turma;
		int pontuacao;
		String linha;
		
		int maiorID = Collections.max(dados.getEstudantes().keySet());
		
		System.out.println("Digite o nome, o sobrenome, "
				+ "a data de nascimento(dia/mes/ano), o sexo, a turma e a pontuação de 0 a 1000 na mesma linha"
				+ " e separado por vírgulas: ");
		
		linha = entrada.nextLine().trim();
		
		try
		{
			dataDeNascimento = LocalDate.parse(linha.split(",")[2].trim(), formatador);
			sexo = linha.split(",")[3].trim();
			turma = linha.split(",")[4].trim();
			pontuacao = Integer.parseInt(linha.split(",")[5].trim());
			
			if (pontuacao < 0 || pontuacao > 1000)
			{
				System.out.println("Digite uma pontuação de 0 a 1000.");
				return;
			}
			
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Digite 6 elementos");
			return;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Digite um número correto.");
			return;
		}
		catch (DateTimeParseException e)
		{
			System.out.println("Digite uma data válida.");
			return;
		}
		
		
		nome = linha.split(",")[0].trim();
		sobrenome = linha.split(",")[1].trim();
		dataDeNascimento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));


		dados.getEstudantes().put(maiorID + 1, new Estudante(maiorID + 1, nome, sobrenome, dataDeNascimento, sexo, turma, pontuacao));
	}
	
	/**
	 * Cadastra livro no banco de dados. O ID a ser atribuído é o do último livro registrado mais um.
	 */
	
	public void cadastraLivros()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		String nome;
		int numDePaginas;
		int pontuacao;
		int idAutor;
		int idTipo;
		int maiorID = Collections.max(dados.getLivros().keySet());
		String linha;
		
		System.out.println("Digite o nome do livro, número de páginas, pontuação(maior que 0), ID do autor e ID do tipo todos separados por vírgulas.");
		
		linha = entrada.nextLine().trim();
		
		try
		{
			nome = linha.split(",")[0].trim();
			numDePaginas = Integer.parseInt(linha.split(",")[1].trim());
			pontuacao = Integer.parseInt(linha.split(",")[2].trim());
			idAutor = Integer.parseInt(linha.split(",")[3].trim());
			idTipo = Integer.parseInt(linha.split(",")[4].trim());
			
			if (pontuacao < 0)
			{
				System.out.println("Digite maior que 0.");
				return;
			}
			
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Digite 5 elementos");
			return;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Digite um número correto.");
			return;
		}
		
		testaAutor(idAutor);
		
		testaGenero(idTipo);
		
		idAutor = dados.getAutores().size();
		idTipo = dados.getCategoriasLivros().size();
		
		dados.getLivros().put(maiorID + 1, new Livro(maiorID + 1, nome, numDePaginas, pontuacao, idAutor, idTipo));	
	}
	
	/**
	 * Recebe dados para cadastro de empréstimos no banco de dados. O ID a ser atribuído é o do último empréstimo registrado mais um.
	 */
	
	public void cadastraEmprestimos()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		Integer idEstudante;
		int idLivro;
		String linha;
		
		System.out.println("Digite o ID do estudante e o ID do livro separados por vírgula: ");
		
		linha = entrada.nextLine().trim();
		
		try
		{
			idEstudante = Integer.parseInt(linha.split(",")[0].trim());
			idLivro = Integer.parseInt(linha.split(",")[1].trim());	
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Digite 2 elementos");
			return;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Digite um número correto.");
			return;
		}
		
		if (!testaEstudante(idEstudante) || !testaLivro(idLivro))
		{
			return;
		}
		
		if (verificaPossibilidadeEmprestimo(idEstudante, idLivro).intern() == "adicionado")
		{
			realizaOperacaoEmprestimo(idLivro, idEstudante);
		}
		
		else if (verificaPossibilidadeEmprestimo(idEstudante, idLivro).intern() == "fila")
		{
			realizaOperacoesFila(idLivro, idEstudante);
		}
		
		else if (verificaPossibilidadeEmprestimo(idEstudante, idLivro).intern() == "identico")
		{
			System.out.println("O estudante já possui como empréstimo este livro.");
			return;
		}
		
		else if (verificaPossibilidadeEmprestimo(idEstudante, idLivro).intern() == "maximo")
		{
			System.out.println("O estudante já possui o limite máximo de empréstimos.");
			return;
		}
		
			
	}
	
	/**
	 * Realiza as operações de cadastro do empréstimo
	 * @param idLivro o id do livro a ser cadastrado
	 * @param idEstudante o id do estudante a ser cadastrado
	 */
	
	private void realizaOperacaoEmprestimo(int idLivro, Integer idEstudante)
	{
		int maiorID = Collections.max(dados.getEmprestimos().keySet());
		LocalDateTime dataEmprestimo;
		String dataLocal = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
		dataEmprestimo = LocalDateTime.parse(dataLocal, DateTimeFormatter.ofPattern("dd/MM/yy HH:mm"));
		dados.getEmprestimos().put(maiorID + 1, new Emprestimo(maiorID + 1, idEstudante, idLivro, dataEmprestimo, null));
	}
	
	/**
	 * Realiza as operações de fila após a verificação de candidatos
	 * @param idLivro o id do livro na fila para ser cadastrado
	 * @param idEstudante o id do estudante na fila para ser cadastrado
	 */
	
	private void realizaOperacoesFila(int idLivro, Integer idEstudante)
	{
		Fila testeFila = new Fila(idLivro);
		Fila testeEstudante = new Fila(idEstudante);
		int ocorrencias;
		int ocorrenciaEstudante = dados.getFila().indexOf(testeEstudante);
		
		if (Collections.frequency(dados.getFila(), testeEstudante) >= 1)
		{
			if (dados.getFila().get(ocorrenciaEstudante).getIdLivro() == idLivro)
			{
				System.out.println("O estudante já está na fila para esse livro.");
				return;
			}
		}
		
		if (Collections.frequency(dados.getFila(), testeFila) >= 3 && dados.getLivros().get(idLivro).getPontos() > 80)
		{
			System.out.println("Esse livro possui mais de 80 pontos. O limite para a fila é de três pessoas.");
			return;
		}
		
		ocorrencias = Collections.frequency(dados.getFila(), testeFila);
		dados.getFila().add(new Fila(idLivro, idEstudante, ocorrencias + 1));
		System.out.printf("O estudante é o %dº da fila para esse livro.\n", ocorrencias + 1);
	}
	
	/**
	 * Registra a devolução de empréstimos em aberto
	 */
	
	public void registrarDevolucao()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		int idEmprestimo;
		String linha;
		
		System.out.println("Digite o ID do empréstimo");
		
		linha = entrada.nextLine().trim();
		
		try
		{
			idEmprestimo = Integer.parseInt(linha);
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Digite o ID apenas.");
			return;
		}
		catch (NumberFormatException e)
		{
			System.out.println("Digite um número correto.");
			return;
		}
		
		if (!testaEmprestimo(idEmprestimo))
		{
			return;
		}
		realizaDevolucao(idEmprestimo);
	}
	
	/**
	 * Imprime os últimos n empréstimos da biblioteca
	 * @param n o número de empréstimos solicitado pelo usuário
	 */
	
	public void consultaUltimosEmprestimos(int n)
	{
		DateTimeFormatter diaMesAnoHora = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		Set<Integer> s = dados.getEmprestimos().keySet();
		int maiorID = Collections.max(s);
		int parada = maiorID - n;
		Emprestimo emprestimo;
		
		if (parada < 0)
			parada = 0;
		
		for (int contador = maiorID ; contador > parada ; contador--)
		{
			if (s.contains(contador))
			{
				emprestimo = dados.getEmprestimos().get(contador);
			
				if (emprestimo.getDataEntrega() == null)
					System.out.println(emprestimo.getID() + "\t" + emprestimo.getIdEstudante() + "\t" + emprestimo.getIdLivro() + "\t"
							+ emprestimo.getDataRetirada().format(diaMesAnoHora) + "\t");
				else
					System.out.println(emprestimo.getID() + "\t" + emprestimo.getIdEstudante() + "\t" + emprestimo.getIdLivro() + "\t"
							+ emprestimo.getDataRetirada().format(diaMesAnoHora) + "\t" + emprestimo.getDataEntrega().format(diaMesAnoHora));
			}
		}
	}
	
	/**
	 * Imprime os empréstimos abertos ou não com mais de n dias da data atual
	 * @param n o número de dias solicitado pelo usuário
	 */
	
	public void consultaEmprestimosAlemDeN(int n)
	{
		LocalDateTime dataHoraAtuais = LocalDateTime.now();
		DateTimeFormatter diaMesAnoHora = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
		Set<Integer> s = dados.getEmprestimos().keySet();
		Iterator<Integer> i = s.iterator();
		LocalDateTime referencia = dataHoraAtuais.minusDays(n);
		Emprestimo emprestimo;
		
		while (i.hasNext())
		{
			emprestimo = dados.getEmprestimos().get(i.next());
			
			if (emprestimo.getDataRetirada().compareTo(referencia) < 0)
			{
				if (emprestimo.getDataEntrega() == null)
					System.out.println(emprestimo.getID() + "\t" + emprestimo.getIdEstudante() + "\t" + emprestimo.getIdLivro() + "\t"
							+ emprestimo.getDataRetirada().format(diaMesAnoHora) + "\t");
				else
					System.out.println(emprestimo.getID() + "\t" + emprestimo.getIdEstudante() + "\t" + emprestimo.getIdLivro() + "\t"
							+ emprestimo.getDataRetirada().format(diaMesAnoHora) + "\t" + emprestimo.getDataEntrega().format(diaMesAnoHora));
				
			}
		}
		
	}
	
	/**
	 * Realiza o ajuste de uma HashMap de forma decrescente
	 * @param numIndividuais o Hash contendo ID de uma entidade e o número de ocorrências em uma entidade
	 * @return um ArrayList ordenado oriundo do Hash
	 */
	
	public ArrayList<Map.Entry<Integer, Integer>> realizaSort(HashMap<Integer, Integer> numIndividuais)
	{
		// Cria um ArrayList para receber ID(chave) e o valor do Hash
		ArrayList<Map.Entry<Integer,Integer>> ranking = new ArrayList<Map.Entry<Integer,Integer>>(numIndividuais.entrySet());
		
		Comparator<Map.Entry<Integer,Integer>> comparadorNumEmprestimos = new Comparator<Map.Entry<Integer,Integer>>()
		{
			@Override
			public int compare(Map.Entry<Integer,Integer> i1, Map.Entry<Integer,Integer> i2)
			{
				return i2.getValue().compareTo(i1.getValue());
			}
		};
		
		Collections.sort(ranking, comparadorNumEmprestimos);
		
		return ranking;		
	}
	
	/**
	 * Captura todas as ocorrências de ID de estudantes na lista de empréstimos.
	 * Depois, verifica-se o número de ocorrências desse ID e adiciona-se ele junto ao ID em um HashMap.
	 * @param n o número de alunos solicitado pelo usuário
	 */
	
	public void consultaAlunosComMaisEmprestimos(int n)
	{
		ArrayList<Integer> idEstudantesEmprestimos = new ArrayList<Integer>(); 
		HashMap<Integer, Integer> numEmprestimosIndividuais = new HashMap<Integer, Integer>();
		Set<Integer> s = dados.getEmprestimos().keySet();
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			idEstudantesEmprestimos.add(dados.getEmprestimos().get(i.next()).getIdEstudante());			
		}
		
		for (int ID : idEstudantesEmprestimos)
		{
			numEmprestimosIndividuais.put(ID, Collections.frequency(idEstudantesEmprestimos, ID));			
		}
		
		ArrayList<Map.Entry<Integer, Integer>> ranking = realizaSort(numEmprestimosIndividuais);
		
		realizaImpressaoRankingEmprestimosAlunos(ranking, n);
	}
	
	/**
	 * Imprime na tela os n alunos que pegaram mais livros emprestados
	 * @param ranking a lista ordenada
	 * @param n o número de alunos solicitado pelo usuário
	 */
	
	public void realizaImpressaoRankingEmprestimosAlunos(ArrayList<Map.Entry<Integer, Integer>> ranking, int n)
	{
		Iterator<Map.Entry<Integer,Integer>> j = ranking.iterator();
		int contador = 1;
		String nome;
		String sobrenome;
		Entry<Integer,Integer> entrada;
		int idAtual;
		int numEmprestimos;
		
		while (j.hasNext() && contador <= n)
		{
			entrada = j.next();
			idAtual = entrada.getKey();
			numEmprestimos = entrada.getValue();
			nome = dados.getEstudantes().get(idAtual).getNome();
			sobrenome = dados.getEstudantes().get(idAtual).getSobreNome();
			System.out.println(contador + "º lugar: " + nome + " " + sobrenome + "(ID: " + idAtual + ")"  + "\t" + "Empréstimos:" + numEmprestimos);
			contador++;
		}
	}
	
	/**
	 * Captura todas as ocorrências de ID de livros na lista de empréstimos.
	 * Depois, verifica-se o número de ocorrências desse ID e adiciona-se ele junto ao ID em um HashMap.
	 * @param n o número de livros solicitado pelo usuário
	 */
	
	public void consultaLivrosMaisEmprestados(int n)
	{
		ArrayList<Integer> idLivrosEmprestimos = new ArrayList<Integer>(); 
		HashMap<Integer, Integer> numEmprestimosIndividuais = new HashMap<Integer, Integer>();
		Set<Integer> s = dados.getEmprestimos().keySet();
		Iterator<Integer> i = s.iterator();
		
		while (i.hasNext())
		{
			idLivrosEmprestimos.add(dados.getEmprestimos().get(i.next()).getIdLivro());			
		}
		
		for (int ID : idLivrosEmprestimos)
		{
			numEmprestimosIndividuais.put(ID, Collections.frequency(idLivrosEmprestimos, ID));			
		}
		
		ArrayList<Map.Entry<Integer, Integer>> ranking = realizaSort(numEmprestimosIndividuais);
		
		realizaImpressaoRankingEmprestimosLivros(ranking, n);
	}
	
	/**
	 * Imprime na tela os n livros mais emprestados
	 * @param ranking a lista ordenada
	 * @param n o número de livros solicitado pelo usuário
	 */
	
	public void realizaImpressaoRankingEmprestimosLivros(ArrayList<Map.Entry<Integer, Integer>> ranking, int n)
	{
		Iterator<Map.Entry<Integer,Integer>> j = ranking.iterator();
		int contador = 1;
		String nome;
		
		Entry<Integer,Integer> entrada;
		int idAtual;
		int numEmprestimos;
		
		while (j.hasNext() && contador <= n)
		{
			entrada = j.next();
			idAtual = entrada.getKey();
			numEmprestimos = entrada.getValue();
			nome = dados.getLivros().get(idAtual).getNome();
			System.out.println(contador + "º lugar: " + nome  + "(ID: " + idAtual + ")"  + "\t" + "Empréstimos:" + numEmprestimos);
			contador++;
		}
	}
	
	/**
	 * Captura todas as ocorrências de ID de autores na lista de empréstimos.
	 * Depois, verifica-se o número de ocorrências desse ID e adiciona-se ele junto ao ID em um HashMap.
	 * @param n o número de autores solicitado pelo usuário
	 */
	
	public void consultaAutoresMaisPopulares(int n)
	{
		ArrayList<Integer> idAutoresEmprestimos = new ArrayList<Integer>(); 
		HashMap<Integer, Integer> numEmprestimosIndividuais = new HashMap<Integer, Integer>();
		Set<Integer> s = dados.getEmprestimos().keySet();
		Iterator<Integer> i = s.iterator();
		int idLivro;
		Livro livro;
		
		while (i.hasNext())
		{
			idLivro = dados.getEmprestimos().get(i.next()).getIdLivro();
			livro = dados.getLivros().get(idLivro);
			idAutoresEmprestimos.add(livro.getAutorID());
		}
		
		for (int ID : idAutoresEmprestimos)
		{
			numEmprestimosIndividuais.put(ID, Collections.frequency(idAutoresEmprestimos, ID));			
		}
		
		ArrayList<Map.Entry<Integer, Integer>> ranking = realizaSort(numEmprestimosIndividuais);
		
		realizaImpressaoRankingAutores(ranking, n);		
	}
	
	/**
	 * Imprime na tela os n autores mais populares
	 * @param ranking a lista ordenada
	 * @param n o número de autores solicitado pelo usuário
	 */
	
	public void realizaImpressaoRankingAutores(ArrayList<Map.Entry<Integer, Integer>> ranking, int n)
	{
		Iterator<Map.Entry<Integer,Integer>> j = ranking.iterator();
		String nome;
		String sobrenome;
		int contador = 1;
		
		Entry<Integer,Integer> entrada;
		int idAtual;
		int numEmprestimos;
		
		while (j.hasNext() && contador <= n)
		{
			entrada = j.next();
			idAtual = entrada.getKey();
			numEmprestimos = entrada.getValue();
			nome = dados.getAutores().get(idAtual).getNome();
			sobrenome = dados.getAutores().get(idAtual).getSobreNome();
			System.out.println(contador + "º lugar: " + nome  + " " + sobrenome + "(ID: " + idAtual + ")"  + "\t" + "Lido:" + numEmprestimos + " vez(es)");
			contador++;
		}
	}
	
	/**
	 * Captura todas as ocorrências de ID de gêneros na lista de empréstimos.
	 * Depois, verifica-se o número de ocorrências desse ID e adiciona-se ele junto ao ID em um HashMap.
	 */
	
	public void consultaGenerosMaisPopulares()
	{
		ArrayList<Integer> idGenerosEmprestimos = new ArrayList<Integer>(); 
		HashMap<Integer, Integer> numEmprestimosIndividuais = new HashMap<Integer, Integer>();
		Set<Integer> s = dados.getEmprestimos().keySet();
		Iterator<Integer> i = s.iterator();
		int idLivro;
		Livro livro;
		
		while (i.hasNext())
		{
			idLivro = dados.getEmprestimos().get(i.next()).getIdLivro();
			livro = dados.getLivros().get(idLivro);
			idGenerosEmprestimos.add(livro.getCategoriaLivroID());
		}
		
		for (int ID : idGenerosEmprestimos)
		{
			numEmprestimosIndividuais.put(ID, Collections.frequency(idGenerosEmprestimos, ID));			
		}
		
		ArrayList<Map.Entry<Integer, Integer>> ranking = realizaSort(numEmprestimosIndividuais);
		
		realizaImpressaoRankingGeneros(ranking);		
	}
	
	/**
	 * Imprime na tela os gêneros mais populares
	 * @param ranking a lista ordenada
	 */
	
	public void realizaImpressaoRankingGeneros(ArrayList<Map.Entry<Integer, Integer>> ranking)
	{
		Iterator<Map.Entry<Integer,Integer>> j = ranking.iterator();
		String nome;
		int contador = 1;
		
		Entry<Integer,Integer> entrada;
		int idAtual;
		
		while (j.hasNext())
		{
			entrada = j.next();
			idAtual = entrada.getKey();
			nome = dados.getCategoriasLivros().get(idAtual).getNome();
			System.out.println(contador + "º lugar: " + nome + "(ID: " + idAtual + ")");
			contador++;
		}
	}

}
