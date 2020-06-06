import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;


import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * 
 * @author Thiago Castro
 *
 */

/**
 * Essa classe controla a criacao da janela principal, de suas ramificacoes e de
 * qualquer acao realizada nela.
 */


public class Janela{
	
	private Arquivo opcoesArquivo = new Arquivo(); // Usado para chamada de funções de arquivo
	private JTextArea usuario = new JTextArea(); // Campo principal de texto
	
	private ControleFechamentoJanela fechadorSeguro = new ControleFechamentoJanela(); // Classe com metodo windowClosing() para aplicar o botao fechar ao X da janela
	private VerificadorMudancasTexto aux = new VerificadorMudancasTexto(); // Instância para verificar mudanças de texto no documento
	protected UndoManager refazDesfaz = new UndoManager(); // Armazena qualquer mudança nos textos
	private ControleDesfaz desfaz = new ControleDesfaz();
	private ControleRefaz refaz = new ControleRefaz();
	
	
	static JFrame janela;
	
	
	JMenuBar menuBase = new JMenuBar();
	
	JMenu menuArquivo = new JMenu("Arquivo");
	JMenuItem opcaoAbrir = new JMenuItem("Abrir");
	JMenuItem opcaoSalvar = new JMenuItem("Salvar");
	JMenuItem opcaoSalvarComo = new JMenuItem("Salvar como...");
	JMenuItem opcaoFechar = new JMenuItem("Fechar");
	
	JMenu menuEditar = new JMenu("Editar");
	JMenuItem opcaoRefazer = new JMenuItem("Refazer");		
	JMenuItem opcaoDesfazer = new JMenuItem("Desfazer");
	
	/**
	 * Cria a janela do editor de texto
	 * <p>
	 * Cria a janela principal com a scrollbar e a barra de menu, utilizando as classes do pacote Swing.
	 * Nesse metodo, tambem e realizada a chamada de funcoes para os botoes.
	 * </p>
	 */
	
	public void setaJanelaPrincipal()
	{
		janela = new JFrame("Sem Título - TEdit");
		janela.setSize(800,800);
		janela.addWindowListener(fechadorSeguro);
		
		
		menuBase.add(menuArquivo);
		menuBase.add(menuEditar);
		
		
	
		menuArquivo.add(opcaoAbrir);
		menuArquivo.add(opcaoSalvar);
		menuArquivo.add(opcaoSalvarComo);
		menuArquivo.addSeparator();
		menuArquivo.add(opcaoFechar);
		
		
		menuEditar.add(opcaoRefazer);
		menuEditar.add(opcaoDesfazer);
		opcaoRefazer.setEnabled(false);
		opcaoDesfazer.setEnabled(false);
		@SuppressWarnings("deprecation")
		KeyStroke ctrlY = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
		@SuppressWarnings("deprecation")
		KeyStroke ctrlZ = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask());
		opcaoRefazer.setAccelerator(ctrlY);
		opcaoDesfazer.setAccelerator(ctrlZ);
		
		
		usuario.setWrapStyleWord(true);
		usuario.setLineWrap(true);
		usuario.getDocument().addUndoableEditListener(aux);
    
		janela.getContentPane().add(BorderLayout.NORTH, menuBase);
		janela.getContentPane().add(BorderLayout.CENTER, new JScrollPane(usuario));
		janela.setVisible(true);
		
		/**
		 * Abertura de arquivo
		 * <p>
		 * Chama o metodo abrir arquivo da classe Arquivo. Apos o processo ser realizado, retorna uma string com o nome do arquivo para renomear a janela principal,
		 * alem de zerar o buffer de edicoes.
		 * @param e Deteccao de clique na opcao
		 */
		
		opcaoAbrir.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				String novoNome = opcoesArquivo.opcaoAbrirArquivo(usuario);
				if (novoNome != null)
				{
					janela.setTitle(novoNome + " - TEdit");
				} 
				usuario.getDocument().addUndoableEditListener(aux);
				aux.zeraEditor();
				usuario.setWrapStyleWord(true);
				usuario.setLineWrap(true);
			}
		});
		
		/**
		 * Salvar arquivo ja existente
		 * <p>
		 * Se e um novo arquivo, e perguntado se o usuario deseja criar um novo arquivo. O metodo que controla isso e o controlaNovosArquivos().
		 * Se nao, apenas salva as mudancas no arquivo ja existente. Apos o processo ser realizado, retorna uma string com o nome do arquivo para renomear a janela principal,
	     * alem de zerar o buffer de edicoes.
		 * @param e Deteccao de clique na opcao
		 */
		
		opcaoSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (controlaNovosArquivos(janela) == false)
				{
					String novoNome = opcoesArquivo.opcaoSalvarArquivo(usuario);
					if (novoNome != null)
					{
						janela.setTitle(novoNome + " - TEdit");
					} 
					usuario.getDocument().addUndoableEditListener(aux);
					aux.zeraEditor();
					usuario.setWrapStyleWord(true);
					usuario.setLineWrap(true);
				}
				
			}
		});
		
		/**
		 * Salvar como novo arquivo ou ja existente
		 * <p>
		 * E chamado o metodo salvarComoArquivo() da classe Arquivo. Apos o processo ser realizado, retorna uma string com o nome do arquivo para renomear a janela principal,
	     * alem de zerar o buffer de edicoes.
		 */
		
		opcaoSalvarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String novoNome = opcoesArquivo.opcaoSalvarComoArquivo(usuario);
				if (novoNome != null)
				{
					janela.setTitle(novoNome + " - TEdit");
					usuario.getDocument().addUndoableEditListener(aux);
					aux.zeraEditor();
					usuario.setWrapStyleWord(true);
					usuario.setLineWrap(true);
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
		
		opcaoFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (opcaoDesfazer.isEnabled() == true  && opcoesArquivo.verificaArquivo() != null)
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
				else if (opcaoDesfazer.isEnabled() == true && opcoesArquivo.verificaArquivo() == null)
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
		
		/**
		 * Botao refazer
		 * <p>
		 * Apenas chama a funcao refazer da classe ControleRefazer
		 * </p>
		 */
		
		opcaoRefazer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				refaz.refazer();
				
			}
		});
		
		/**
		 * Botao desfazer
		 * <p>
		 * Apenas chama a funcao desfazer da classe ControleDesfazer
		 * </p>
		 */
		
		opcaoDesfazer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e)
			{
				desfaz.desfazer();				
			}
		});		
		
	}
	
	/**
	 * Verifica se algum arquivo e novo
	 * <p>
	 * Caso o arquivo seja novo, a opcao salvar do menu arquivo ira mostrar um dialogo perguntando o desejo do usuario entre salvar ou continuar editando.
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
				usuario.getDocument().addUndoableEditListener(aux);
				aux.zeraEditor();
			}
			return true;
		}
		return false;
		
	}
	
	/**
	 * Essa classe verifica qualquer edicao realizada no campo texto para utilizar na chamada dos
	 * metodos relacionados ao salvamento e para controle dos botoes refazer e desfazer.
	 */
	
	private class VerificadorMudancasTexto implements UndoableEditListener
	{
		
		/**
		 * Controle de edicoes
		 * <p>
		 * Buffer de edicoes realizada no editor. Qualquer mudanca e relatada para a variavel refazDesfaz da classe UndoManager.
		 * Com isso, o estado dos botoes desfazer e refazer e atualizado.
		 * </p>
		 */
		public void undoableEditHappened(UndoableEditEvent e)
		{
			 
			refazDesfaz.addEdit(e.getEdit());
			refaz.atualizaBotao();
			desfaz.atualizaBotao();
		}
		
		/**
		 * Zera editor e os botoes caso haja a abertura de um arquivo ou salvamento de um novo. Ou seja, esse e o novo ponto de partida.
		 */
		 
		 public void zeraEditor()
		 {
			 refazDesfaz.discardAllEdits();
			 opcaoDesfazer.setEnabled(false);
			 opcaoRefazer.setEnabled(false);
		 }	
	}
	
	/**
	 * Essa classe implementa a acao do botao desfazer e atualiza seu estado. 
	 */
	
	private class ControleDesfaz
	{
		/**
		 * Desfaz mudancas se possivel. Apos isso, atualiza o estado dos botoes desfazer e refazer.
		 */
		
		public void desfazer() 
		{
			try
			{
				refazDesfaz.undo();				
			}
			catch (CannotUndoException c)
			{
				
			}
			atualizaBotao();
			refaz.atualizaBotao();									 
		}
		
		/**
		 * Atualiza botao desfazer
		 */
		
		protected void atualizaBotao()
		{
			if (refazDesfaz.canUndo())
			{
				opcaoDesfazer.setEnabled(true);				
			}
			else
				opcaoDesfazer.setEnabled(false);
			
		}
	}
	
	/**
	 * Essa classe implementa a acao do botao refazer e atualiza seu estado. 
	 */
	
	private class ControleRefaz
	{
		/**
		 * Refaz mudancas se possivel. Apos isso, atualiza o estado dos botoes desfazer e refazer.
		 */
		
		public void refazer() 
		{			
			try
			{
				refazDesfaz.redo();
			}
			catch (CannotRedoException c)
			{					 
			}
			atualizaBotao();
			desfaz.atualizaBotao();					 
		}
		
		/**
		 * Atualiza botao refazer
		 */
		protected void atualizaBotao()
		{
			if (refazDesfaz.canRedo())
			{
				opcaoRefazer.setEnabled(true);				
			}
			else
				opcaoRefazer.setEnabled(false);			
		}		
	}
	
	/**
	 * Essa classe implementa a interface WindowListener, que controla mudancas na janela. 
	 * E usada no programa caso o usuario aperte o X.
	 */
	
	
	private class ControleFechamentoJanela implements WindowListener
	{
		/**
		 * Metodo nao implementado
		 */

		@Override
		public void windowOpened(WindowEvent e) {}
		
		/**
		 * Fechar janela pelo X
		 * <p>
		 * Utiliza a mesma logica do botao fechar do menu arquivo.
		 * </p>
		 * @param e A mudanca na janela detectada pela classe.
		 */

		@Override
		public void windowClosing(WindowEvent e) 
		{
			if (opcaoDesfazer.isEnabled() == true && opcoesArquivo.verificaArquivo() != null)
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
			else if (opcaoDesfazer.isEnabled() == true && opcoesArquivo.verificaArquivo() == null)
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
