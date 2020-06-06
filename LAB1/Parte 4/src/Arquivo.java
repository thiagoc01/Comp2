import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * 
 * @author Thiago Castro
 *
 */


public final class Arquivo extends javax.swing.filechooser.FileFilter {
	
	private File arquivo = null;
	
	/**
	 * Verifica se o descritor de arquivo esta ocupado.
	 * @return O descritor de arquivo da classe.
	 */
	public File verificaArquivo()
	{
		return arquivo;
	}
	
	/**
	 * Metodo de salvamento
	 * <p>
	 * Apenas declarada para nao haver duplicata de codigo nos metodos opcaoSalvarComoArquivo e opcaoSalvarArquivo.
	 * @param entrada Campo texto do editor
	 * @return Nome do Arquivo para titulo da janela ou null caso nao aconteca mudanca.
	 */
	public String salvar(JTextArea entrada)
	{
		try
		{
			FileWriter arq = new FileWriter(arquivo, false);
			BufferedWriter buffer = new BufferedWriter(arq);
			entrada.write(buffer);
			buffer.close();
			arq.close();
			String nomeArquivo = arquivo.getName().replaceAll(".txt", "");
			return nomeArquivo;
		}
		catch (IOException e)
		{
			
		}
		return null;
		
	}
	
	/**
	 * Abre arquivo pelo dialogo do sistema.
	 * <p>
	 * Utiliza a classe JFileChooser para gerar um GUI que interaja com o usuario. Exibe apenas arquivos de texto.
	 * Caso o usuario selecione um arquivo, e aberto um descritor com o local indicado e o arquivo e lido.
	 * Caso nao, o metodo e encerrado.
	 * </p>
	 * @param entrada O campo de texto do editor de texto
	 * @return nome do arquivo ou null caso o metodo seja encerrado
	 */
	
	public String opcaoAbrirArquivo(JTextArea entrada)
	{
		JFileChooser verArqSistema = new JFileChooser();
		FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Documentos de Texto (*.txt)", "txt");
		verArqSistema.setFileFilter(filtroTxt);
		int confirmar = verArqSistema.showOpenDialog(new JFrame());
		String nomeArquivo = "";
	
		
		if ( confirmar == JFileChooser.APPROVE_OPTION )
		{
			arquivo = verArqSistema.getSelectedFile();
			try
			{
				FileReader arq = new FileReader(arquivo);
				BufferedReader leitor = new BufferedReader(arq);
				
				entrada.read(leitor, null);
				leitor.close();
				arq.close();
				nomeArquivo = arquivo.getName().replaceAll(".txt","");
				return nomeArquivo;
			}
			catch(FileNotFoundException e)
			{				
			}
			catch (IOException e)
			{
				JLabel mensagem = new JLabel("Erro de leitura");				
				JDialog erro = new JDialog();
				erro.pack();
				erro.setTitle("Erro de leitura");
				erro.getContentPane().add(mensagem);				
			}
			
		}
		return null;
	}
	
	/**
	 * Salva arquivo ja aberto
	 * <p>
	 * Apenas sobrescreve um arquivo ja aberto anteriormente. A verificacao ocorre na classe Janela.
	 * </p>
	 * @param entrada O campo de texto do editor de texto
	 * @return nome do arquivo ou null caso o metodo seja encerrado
	 */
	
	public String opcaoSalvarArquivo(JTextArea entrada)
	{
		String nomeArquivo;
		
		try
		{
			nomeArquivo = salvar(entrada);
			return nomeArquivo;
		}
		catch (Exception e)
		{
			
		}
		return null;	
	}
	
	/**
	 * Salva arquivo pelo dialogo do sistema.
	 * <p>
	 * Utiliza a classe JFileChooser para gerar uma GUI que interaja com o usuario.
	 * Caso o usuario selecione um arquivo ja existente ou digite um nome de arquivo ja existente, o metodo pergunta se ele deseja sobrescrever o arquivo. Se sim, prossegue. Se não, encerra o metodo.
	 * Caso o usuario digite um nome novo, um novo arquivo e gerado no diretorio desejado, com o nome desejado.
	 * Caso nao deseje prosseguir, o metodo e encerrado.
	 * </p>
	 * @param entrada O campo de texto do editor de texto
	 * @return nome do arquivo ou null caso o metodo seja encerrado
	 */
	
	public String opcaoSalvarComoArquivo(JTextArea entrada)
	{
		JFileChooser verArqSistema = new JFileChooser();
		FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Documentos de Texto (*.txt)", "txt");
		JDialog salvarArquivo = new JDialog();
		verArqSistema.setFileFilter(filtroTxt);
		int confirmar = verArqSistema.showSaveDialog(salvarArquivo);
		String nomeArquivo;
		
		if ( confirmar == JFileChooser.APPROVE_OPTION)
		{
			arquivo = verArqSistema.getSelectedFile();
			if (accept(arquivo) == false)
			{
				arquivo = new File(arquivo.getAbsoluteFile() + ".txt");
			}
			if (arquivo.exists()) // Verifica a existencia do arquivo
			{
				JLabel mensagem = new JLabel("Deseja sobrescrever o arquivo já existente?");
				int confirmacao = JOptionPane.showConfirmDialog(salvarArquivo , mensagem, "Arquivo já existente", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (confirmacao == JOptionPane.YES_OPTION)
				{
					nomeArquivo = salvar(entrada); // Chama o metodo de salvar.
					return nomeArquivo;					
				}
				else
					return null;
			}
			try
			{
				nomeArquivo = salvar(entrada);
				return nomeArquivo;
			}
			catch(Exception e){}
			
		}
		return null;
		
	}

	@Override
	public boolean accept(File f) {
		 return f.getName().toLowerCase().endsWith(".txt");
	}

	@Override
	public String getDescription() {
		return "*.txt";
	}

}
