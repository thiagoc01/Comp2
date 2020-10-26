package br.ufrj.dcc.comp2.thiago.lista4;

import java.util.Scanner;

/**
 * Essa classe implementa o Menu, visando o padrão de projeto Facade para a Biblioteca.
 * @author Thiago Castro
 *
 */

public class Menu 
{
	private Biblioteca biblioteca = new Biblioteca();
	private Serializadora salvadorTSV = new Serializadora();
	boolean OK;
	
	private Scanner in = new Scanner(System.in);
	
	String entrada;
	
	Dados d = Dados.retornaInstancia();
	
	int escolha;
	
	/**
	 * Imprime o menu principal com um loop enquanto o usuário não escolher a opção 12 para encerrar e salvar o estado do programa.
	 * @return true se o usuário escolheu 12 e desejou sair, false c.c.
	 */
	
	public boolean printaMenu()
	{
		OK = false;
		System.out.println("Digite uma opção: ");
		System.out.println("1 - Cadastrar um aluno \n"
				+ "2 - Cadastrar um livro \n"
				+ "3 - Registrar um empréstimo \n"
				+ "4 - Registrar uma devolução \n"
				+ "5 - Consultar os N últimos empréstimos de livros \n"
				+ "6 - Consultar os empréstimos (fechados ou em aberto) com mais de N dias \n"
				+ "7 - Consultar os N estudantes que pegaram mais livros emprestados \n"
				+ "8 - Consultar os N livros mais emprestados \n"
				+ "9 - Consultar os N autores mais populares \n"
				+ "10 - Consulta aos estilos literários mais populares \n"
				+ "11 - Salvar o estado atual \n"
				+ "12 - Encerrar o programa e salvar o estado atual");
		
		while (!OK)
		{
			try
			{
				entrada = in.nextLine();
				escolha = Integer.parseInt(entrada);
				
				if (escolha < 1 || escolha > 12)
				{
					System.out.println("Digite números de 1 a 10");
					OK = false;
					continue;
				}
				OK = true;
			}
			catch (NumberFormatException e)
			{
				System.out.println("Digite números de 1 a 10");
				System.out.println("1 - Cadastrar um aluno \n"
						+ "2 - Cadastrar um livro \n"
						+ "3 - Registrar um empréstimo \n"
						+ "4- Registrar uma devolução \n"
						+ "5 - Consultar os N últimos empréstimos de livros \n"
						+ "6 - Consultar os empréstimos (fechados ou em aberto) com mais de N dias \n"
						+ "7 - Consultar os N estudantes que pegaram mais livros emprestados \n"
						+ "8 - Consultar os N livros mais emprestados \n"
						+ "9 - Consultar os N autores mais populares \n"
						+ "10 - Consulta aos estilos literários mais populares \n"
						+ "11 - Salvar o estado atual \n"
						+ "12 - Encerrar o programa e salvar o estado atual");
			}
		}
		
		if (switchEscolha(escolha))
			return true;
		else
			return false;
	}
	
	public boolean switchEscolha(int escolha)
	{
		int n;
		switch(escolha)
		{
			case 1:
				biblioteca.cadastraAlunos();
				break;
			case 2:
				biblioteca.cadastraLivros();
				break;
			case 3:
				biblioteca.cadastraEmprestimos();
				break;
			case 4:
				biblioteca.registrarDevolucao();
				break;
			case 5:
				n = retornaNumEmprestimos();
				if (n != -1)
					biblioteca.consultaUltimosEmprestimos(n);
				break;
			case 6:
				n = retornaNumDias();
				if (n != -1)
					biblioteca.consultaEmprestimosAlemDeN(n);
				break;
			case 7:
				n = retornaNumAlunos();
				if (n != -1)
					biblioteca.consultaAlunosComMaisEmprestimos(n);
				break;
			case 8:
				n = retornaNumLivros();
				if (n != -1)
					biblioteca.consultaLivrosMaisEmprestados(n);
				break;
			case 9:
				n = retornaNumAutores();
				if (n != -1)
					biblioteca.consultaAutoresMaisPopulares(n);
				break;
			case 10:
				biblioteca.consultaGenerosMaisPopulares();
				break;
			case 11:
				salvadorTSV.criaPastaDados();
				salvadorTSV.geraAutoresTSV();
				salvadorTSV.geraCategoriasLivrosTSV();
				salvadorTSV.geraLivrosTSV();
				salvadorTSV.geraEstudantesTSV();
				salvadorTSV.geraFilaTSV();
				salvadorTSV.geraEmprestimosTSV();
				break;				
			case 12:
				in.close();
				salvadorTSV.criaPastaDados();
				salvadorTSV.geraAutoresTSV();
				salvadorTSV.geraCategoriasLivrosTSV();
				salvadorTSV.geraLivrosTSV();
				salvadorTSV.geraEstudantesTSV();
				salvadorTSV.geraFilaTSV();
				salvadorTSV.geraEmprestimosTSV();
				return true;
		}
		
		return false;
		
	}
	
	/**
	 * Recebe os locais com os arquivos para o carregamento do banco de dados.
	 * <p>
	 * 		Esse método é chamado pela classe Dados no início do programa.
	 * 		Não há controle quanto ao arquivo queue.tsv.
	 * </p>
	 * @return true se o usuário inseriu locais corretos e o local do arquivo queue.tsv correto ou ENTER para ele, false c.c
	 */
	
	
	public boolean recebeLocaisArquivos()
	{
		Arquivo arq = new Arquivo();
		String localBooks, localBorrows, localTypes, localStudents, localAuthors, localQueue;
		
		System.out.println("Digite o caminho dos arquivos .tsv na ordem(livros, empréstimos, tipos, estudantes, autores, fila). "
				+ "Se não houver o arquivo queue.tsv, aperte ENTER no momento de solicitação do mesmo. ");
		
		localBooks = in.nextLine();
		localBorrows = in.nextLine();		
		localTypes = in.nextLine();
		localStudents = in.nextLine();
		localAuthors = in.nextLine();
		localQueue = in.nextLine();
		
		if (!arq.verificaExistenciaArquivo(localAuthors))
			return false;		
		
		if (!arq.verificaExistenciaArquivo(localTypes))
			return false;
		
		if (!arq.verificaExistenciaArquivo(localBooks))
			return false;
		
		if (!arq.verificaExistenciaArquivo(localBorrows))
			return false;
		
		if (!arq.verificaExistenciaArquivo(localStudents))
			return false;
		
		if (!arq.verificaExistenciaArquivoQueue(localQueue))
			return false;
		
		arq.recebeAutoresTSV(localAuthors);
		arq.recebeTiposTSV(localTypes);		
		arq.recebeLivrosTSV(localBooks);
		arq.recebeEstudantesTSV(localStudents);
		arq.recebeEmprestimosTSV(localBorrows);
		
		if (localQueue.trim().intern() != "")
			arq.recebeFilaTSV(localQueue);
		
		return true;
	}
	
	/**
	 * Recebe um número maior que 0 para os métodos que recebem N como parâmetro.
	 * @return -1 se o usuário não digitou um int maior que 0 ou retorna o número inteiro fornecido.
	 */
	
	public int recebeMaiorQue0()
	{
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		int escolha = -1;
		
		if (entrada.hasNextInt())
		{
			escolha = Integer.parseInt(entrada.next());
			
			if (escolha <= 0)
			{
				System.out.println("Digite um número maior que 0.");
				escolha = -1;
			}
		}
		else
			System.out.println("Digite um número inteiro.");
		
		return escolha;
		
	}
	
	/**
	 Recebe um número maior que 0 ou igual a 0 para os métodos que recebem N como parâmetro.
	 * @return -1 se o usuário não digitou um int maior que 0 ou a igual a 0 ou retorna o número inteiro fornecido.
	 */
	
	public int recebeMaiorIgual0()
	{	
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		int escolha = -1;
			
		if (entrada.hasNextInt())
		{
			escolha = Integer.parseInt(entrada.next());
			
			if (escolha < 0)
			{
				System.out.println("Digite um número maior que 0 ou igual a 0.");
				escolha = -1;
			}
		}
		else
			System.out.println("Digite um número inteiro.");
		
		
		return escolha;
		
		
	}
	
	/**
	 * Retorna a quantidade de empréstimo para o método consultaUltimosEmprestimos da classe Biblioteca
	 * @return -1 se não for um inteiro ou se for um inteiro menor que 1. Caso contrário, retorna o inteiro fornecido.
	 */
	
	public int retornaNumEmprestimos()
	{
		System.out.println("Digite o número de empréstimos que você deseja: ");		
		
		return recebeMaiorQue0();		
	}
	
	/**
	 * Retorna a quantidade de dias para o método consultaEmprestimosAlemDeN da classe Biblioteca
	 * @return -1 se não for um inteiro ou se for um inteiro menor que 0. Caso contrário, retorna o inteiro fornecido.
	 */
	
	
	public int retornaNumDias()
	{
		System.out.println("Digite o número de dias que você deseja: ");		
		
		return recebeMaiorIgual0();				
	}
	
	/**
	 * Retorna a quantidade de alunos para o método consultaAlunosComMaisEmprestimos da classe Biblioteca
	 * @return -1 se não for um inteiro ou se for um inteiro menor que 1. Caso contrário, retorna o inteiro fornecido.
	 */
	
	public int retornaNumAlunos()
	{
		System.out.println("Digite o número de alunos que você deseja: ");
			
		return recebeMaiorQue0();			
	}
	
	/**
	 * Retorna a quantidade de livros para o método consultaLivrosMaisEmprestados da classe Biblioteca
	 * @return -1 se não for um inteiro ou se for um inteiro menor que 1. Caso contrário, retorna o inteiro fornecido.
	 */
	
	public int retornaNumLivros()
	{
		System.out.println("Digite o número de livros que você deseja: ");
			
		return recebeMaiorQue0();		
	}
	
	/**
	 * Retorna a quantidade de autores para o método consultaAutoresMaisPopulares da classe Biblioteca
	 * @return -1 se não for um inteiro ou se for um inteiro menor que 1. Caso contrário, retorna o inteiro fornecido.
	 */
	
	public int retornaNumAutores()
	{
		System.out.println("Digite o número de autores que você deseja: ");
			
		return recebeMaiorQue0();		
	}
	
	
	
	

}
