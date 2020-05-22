import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * 
 * @author Thiago Castro
 *
 */



public class Janela{
	
	private Arquivo opcoesArquivo = new Arquivo(); 
	JTextArea usuario = new JTextArea(); // Campo principal de texto
	
	ControleFechamentoJanela fechadorSeguro = new ControleFechamentoJanela(); // Classe com metodo windowClosing() para aplicar o botao fechar ao X da janela
	VerificadorMudancasTexto aux = new VerificadorMudancasTexto(); // Instância para verificar mudanças de texto no documento
	static JFrame janela;
	
	/**
	 * Cria a janela do editor de texto
	 * <p>
	 * Cria a janela principal com a scrollbar e a barra de menu, utilizando as classes do pacote Swing.
	 * Nesse metodo, tambem e realizado a chamada de funcoes para os botoes.	 * 
	 * </p>
	 */
	
	public void setaJanelaPrincipal()
	{
		janela = new JFrame("Sem Título - TEdit");
		janela.setSize(800,800);
		janela.addWindowListener(fechadorSeguro);
		
		JMenuBar menuBase = new JMenuBar();
		JMenu opcao1 = new JMenu("Arquivo");
		JMenuItem opcao2 = new JMenuItem("Fechar");		
		menuBase.add(opcao1);
		menuBase.add(opcao2);
		
		JMenuItem opcao1_1 = new JMenuItem("Abrir");
		JMenuItem opcao1_2 = new JMenuItem("Salvar");
		JMenuItem opcao1_3 = new JMenuItem("Salvar como...");
		opcao1.add(opcao1_1);
		opcao1.add(opcao1_2);
		opcao1.add(opcao1_3);		
		
		usuario.getDocument().addDocumentListener(new VerificadorMudancasTexto());
		usuario.setWrapStyleWord(true);
		usuario.setLineWrap(true);
        
    
		janela.getContentPane().add(BorderLayout.NORTH, menuBase);
		janela.getContentPane().add(BorderLayout.CENTER, new JScrollPane(usuario));
		janela.setVisible(true);
		
		
	
		
		opcao1_1.addActionListener(new ActionListener() {
			/**
			 * Abertura de arquivo
			 * <p>
			 * Chama o metodo abrir arquivo da classe Arquivo. Apos o processo ser realizado, retorna uma string com o nome do arquivo para renomear a janela principal,
			 * alem de adicionar as mudancas no buffer de referencia de eventos.
			 * @param e Deteccao de clique na opcao
			 */
			public void actionPerformed(ActionEvent e)
			{
				String novoNome = opcoesArquivo.opcaoAbrirArquivo(usuario);
				if (novoNome != null)
				{
					janela.setTitle(novoNome + " - TEdit");
				} 
				usuario.getDocument().addDocumentListener(new VerificadorMudancasTexto());
				aux.setaMudancaAdiciona(usuario.getText());
				aux.setaMudancaRemove(usuario.getText());
			}
		});
		
		/**
		 * Salvar arquivo ja existente
		 * <p>
		 * Se e um novo arquivo, e perguntado se o usuario deseja criar um novo arquivo. O metodo que controla isso e o controlaNovosArquivos().
		 * Se nao, apenas salva as mudancas no arquivo ja existente. Apos o processo ser realizado, retorna uma string com o nome do arquivo para renomear a janela principal,
	     * alem de adicionar as mudancas no buffer de referencia de eventos.
		 * @param e Deteccao de clique na opcao
		 */
		
		opcao1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (controlaNovosArquivos(janela) == false)
				{
					String novoNome = opcoesArquivo.opcaoSalvarArquivo(usuario);
					if (novoNome != null)
					{
						janela.setTitle(novoNome + " - TEdit");
					} 
					usuario.getDocument().addDocumentListener(new VerificadorMudancasTexto());
					aux.setaMudancaAdiciona(usuario.getText());
					aux.setaMudancaRemove(usuario.getText());					
				}
				
			}
		});
		
		/**
		 * Salvar como novo arquivo ou ja existente
		 * <p>
		 * E chamado o metodo salvarComoArquivo() da classe Arquivo. Apos o processo ser realizado, retorna uma string com o nome do arquivo para renomear a janela principal,
	     * alem de adicionar as mudancas no buffer de referencia de eventos.
		 */
		
		opcao1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String novoNome = opcoesArquivo.opcaoSalvarComoArquivo(usuario);
				if (novoNome != null)
				{
					janela.setTitle(novoNome + " - TEdit");
					usuario.getDocument().addDocumentListener(new VerificadorMudancasTexto());
					aux.setaMudancaAdiciona(usuario.getText());
					aux.setaMudancaRemove(usuario.getText());
				}
				
			}
		});
		
		/**
		 * Fechar a janela salvando um arquivo, se criado.
		 * <p>
		 * Caso um arquivo aberto esteja modificado pelo usuario, e chamado o metodo salvarArquivo para apenas salvar essas modificacoes. Apos isso, o programa e encerrado.
		 * Caso seja um novo arquivo e tenha sofrido modificacoes do usuario, e chamado o metodo salvarComoArquivo() da classe Arquivo para indicar o diretorio.
		 * </p>
		 * @param e A deteccao do clique no botao fechar
		 */
		
		opcao2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if ((aux.retornaMudancaAdiciona() != usuario.getText().intern() || aux.retornaMudancaRemove() != usuario.getText().intern()) && opcoesArquivo.verificaArquivo() != null)
				{
					JLabel mensagem = new JLabel("Deseja salvar as alterações em " + opcoesArquivo.verificaArquivo().toString() + "?");				
					int confirmacao = JOptionPane.showConfirmDialog(janela, mensagem, "TEdit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane. WARNING_MESSAGE);
					if (confirmacao == JOptionPane.YES_OPTION)
					{
						opcoesArquivo.opcaoSalvarArquivo(usuario);
						janela.dispose();
						System.exit(0);	
					}
					else if (confirmacao == JOptionPane.NO_OPTION)
					{
						janela.dispose();
						System.exit(0);
						
					}
					
				}
				else if ((aux.retornaMudancaAdiciona() != usuario.getText().intern() || aux.retornaMudancaRemove() != usuario.getText().intern()) && opcoesArquivo.verificaArquivo() == null)
				{
					JLabel mensagem = new JLabel("Deseja salvar as alterações?" );				
					int confirmacao = JOptionPane.showConfirmDialog(janela, mensagem, "TEdit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane. WARNING_MESSAGE);
					if (confirmacao == JOptionPane.YES_OPTION)
					{
						opcoesArquivo.opcaoSalvarComoArquivo(usuario);
						janela.dispose();
						System.exit(0);	
					}
					else if (confirmacao == JOptionPane.NO_OPTION)
					{
						janela.dispose();
						System.exit(0);
					}
					
				}
				else
				{
					janela.dispose();
					System.exit(0);					
				}
				
			}
		});
	}
	
	private class VerificadorMudancasTexto implements DocumentListener
	{
		
		private String mudancaAdiciona = ""; // String para o metodo insertUpdate()
		private String mudancaRemove = ""; // String para o metodo removeUpdate()
		
		/**
		 * Altera o histórico de mudancas na string mudancaAdiciona no arquivo.
		 * <p>
		 * Apos a chamada de funcoes de abrir e salvar arquivos, e necessario a criacao de pontos de partida para a chamada de novas.
		 * Exemplo: Ao abrir o editor, o padrao, obviamente, e um historico vazio. Ao abrir um arquivo, o novo padrao sera esse arquivo. Qualquer mudanca e relativa a essa abertura.
		 * </p>
		 * @param texto O texto atual no editor
		 */
		
		public void setaMudancaAdiciona(String texto)
		{
			mudancaAdiciona = texto;

		}
		
		/**
		 * Altera o histórico de mudancas na string mudancaAdiciona no arquivo.
		 * <p>
		 * Apos a chamada de funcoes de abrir e salvar arquivos, e necessario a criacao de pontos de partida para a chamada de novas.
		 * Exemplo: Ao abrir o editor, o padrao, obviamente, e um historico vazio. Ao abrir um arquivo, o novo padrao sera esse arquivo. Qualquer mudanca e relativa a essa abertura.
		 * </p>
		 * @param texto O texto atual no editor
		 */
		
		public void setaMudancaRemove(String texto)
		{
			mudancaRemove = texto;
		
		}
		
		/**
		 * Retorna o historico atual de mudancas da string mudancaAdiciona no arquivo.
		 * <p>
		 * Apos a chamada de funcoes de abrir e salvar arquivos, e necessario a criacao de pontos de partida para a chamada de novas.
		 * Exemplo: Ao abrir o editor, o padrao, obviamente, e um historico vazio. Ao abrir um arquivo, o novo padrao sera esse arquivo. Qualquer mudanca e relativa a essa abertura.
		 * </p>
		 * @return String O texto armazenado no editor antes de abrir/salvar um arquivo.
		 */
		
		public String retornaMudancaAdiciona()
		{
			return mudancaAdiciona.intern();
		}
		
		/**
		 * Retorna o historico atual de mudancas da string mudancaAdiciona no arquivo.
		 * <p>
		 * Apos a chamada de funcoes de abrir e salvar arquivos, e necessario a criacao de pontos de partida para a chamada de novas.
		 * Exemplo: Ao abrir o editor, o padrao, obviamente, e um historico vazio. Ao abrir um arquivo, o novo padrao sera esse arquivo. Qualquer mudanca e relativa a essa abertura.
		 * </p>
		 * @return String O texto armazenado no editor antes de abrir/salvar um arquivo.
		 */
		
		public String retornaMudancaRemove()
		{
			return mudancaRemove.intern();
		}


		/**
		 * Insere uma mudanca no historico
		 * <p>
		 * Transforma para string uma mudanca adicionada, utilizando a classe Document.
		 * </p>
		 * @param e A mudanca detectada no arquivo 
		 */
		
		public void insertUpdate(DocumentEvent e) {
			
			try
			{
				mudancaAdiciona = e.getDocument().getText(0, usuario.getText().length());
			}
			catch (BadLocationException d)
			{
				d.printStackTrace();
			}
			
			
		}

		/**
		 * Remove uma mudanca no historico
		 * <p>
		 * Transforma para string uma mudanca removida, utilizando a classe Document.
		 * </p>
		 * @param e A mudanca detectada no arquivo 
		 */
		public void removeUpdate(DocumentEvent e) {
			try
			{
				mudancaRemove = e.getDocument().getText(0, usuario.getText().length());
			}
			catch (BadLocationException d)
			{
				JLabel mensagem = new JLabel("Erro na leitura.");
				JOptionPane.showMessageDialog(janela , mensagem, "Erro", JOptionPane.ERROR_MESSAGE, null);
			}
			
			
		}

		/**
		 * Metodo nao implementado
		 */
		public void changedUpdate(DocumentEvent e) {}
		
		
	}
	
	/**
	 * Verifica se algum arquivo e novo
	 * <p>
	 * Caso o arquivo seja novo, a salvar no menu arquivo ira mostrar um dialogo perguntando o desejo do usuario entre salvar ou continuar editando.
	 * Caso contrario, o metodo encerra.
	 * </p>
	 * @param programa Janela principal para modificacoes
	 * @return true se e um arquivo do zero ou false se e um arquivo ja editado/criado
	 */
	
	private boolean controlaNovosArquivos(JFrame programa)
	{
		if (programa.getTitle().intern() == "Sem Título - TEdit" || opcoesArquivo.verificaArquivo() == null)
		{
			JLabel mensagem = new JLabel("Deseja criar um novo arquivo?");				
			int confirmacao = JOptionPane.showConfirmDialog(programa, mensagem, "Novo arquivo", JOptionPane. WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
			
			if (confirmacao == JOptionPane.YES_OPTION)
			{
				String novoNome = opcoesArquivo.opcaoSalvarComoArquivo(usuario);
				if (novoNome != null)
				{
					programa.setTitle(novoNome + " - TEdit");
				}
				usuario.getDocument().addDocumentListener(new VerificadorMudancasTexto());
				aux.setaMudancaAdiciona(usuario.getText());
				aux.setaMudancaRemove(usuario.getText());
			}
			return true;
		}
		return false;
		
	}
	
	private class ControleFechamentoJanela implements WindowListener // Essa classe implementa a interface WindowListener, que controla mudancas na janela. E usada no programa caso o usuario aperte o X.
	{
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowOpened(WindowEvent e) {}
		
		/**
		 * Fechar janela pelo X
		 * <p>
		 * Utiliza a mesma logica do botao fechar(opcao2 do metodo setaJanelaPrincipal()).
		 * </p>
		 * @param e A mudanca na janela detectada pela classe.
		 */

		@Override
		public void windowClosing(WindowEvent e) 
		{
			if ((aux.retornaMudancaAdiciona() != usuario.getText().intern() || aux.retornaMudancaRemove() != usuario.getText().intern()) && opcoesArquivo.verificaArquivo() != null)
			{
				JLabel mensagem = new JLabel("Deseja salvar as alterações em " + opcoesArquivo.verificaArquivo().toString() + "?");				
				int confirmacao = JOptionPane.showConfirmDialog(janela, mensagem, "TEdit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane. WARNING_MESSAGE);
				if (confirmacao == JOptionPane.YES_OPTION)
				{
					opcoesArquivo.opcaoSalvarArquivo(usuario);
					janela.dispose();
					System.exit(0);	
				}
				else if (confirmacao == JOptionPane.NO_OPTION)
				{
					janela.dispose();
					System.exit(0);
					
				}
				else
				{
					janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
				
			}
			else if ((aux.retornaMudancaAdiciona() != usuario.getText().intern() || aux.retornaMudancaRemove() != usuario.getText().intern()) && opcoesArquivo.verificaArquivo() == null)
			{
				JLabel mensagem = new JLabel("Deseja salvar as alterações?" );				
				int confirmacao = JOptionPane.showConfirmDialog(janela, mensagem, "TEdit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane. WARNING_MESSAGE);
				if (confirmacao == JOptionPane.YES_OPTION)
				{
					opcoesArquivo.opcaoSalvarComoArquivo(usuario);
					janela.dispose();
					System.exit(0);	
				}
				else if (confirmacao == JOptionPane.NO_OPTION)
				{
					janela.dispose();
					System.exit(0);
				}
				else
				{
					janela.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
			else
			{
				janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
		
		}
		
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowClosed(WindowEvent e) {}
		
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowIconified(WindowEvent e) {}
		
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowDeiconified(WindowEvent e) {}
		
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowActivated(WindowEvent e) {}
		
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowDeactivated(WindowEvent e) {}		
		
		
	}


	
	
	

}
