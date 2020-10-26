package br.ufrj.dcc.comp2.thiago.lista4;

public class Main 
{
	public static void main(String[] args) 
	{
		System.out.println("Bem vindo ao sistema da Biblioteca!");
		
		Dados d = Dados.retornaInstancia();
		Menu m = new Menu();
		
		boolean verifica = false;
		
		if (d.carregaDataBase(m))
			return;

		while (!verifica)
		{
			verifica = m.printaMenu();
		}
	}

}
