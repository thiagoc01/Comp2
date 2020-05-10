import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;


public class Grafico {
	
	private String resultado = 
			"<!DOCTYPE HTML>\r\n" + 
			"<html>\r\n" + 
			"\r\n" + 
			"<head>\r\n" + 
			"  <meta content=\"text/html;charset=utf-8\" http-equiv=\"Content-Type\">\r\n" + 
			"  <meta content=\"utf-8\" http-equiv=\"encoding\">\r\n" + 
			"  <title>Gráfico COVID-19</title>\r\n" + 
			"\r\n" + 
			"  <style type=\"text/css\">\r\n" + 
			"    body, html {\r\n" + 
			"      font-family: sans-serif;\r\n" + 
			"    }\r\n" + 
			"  </style>\r\n" + 
			"\r\n" + 
			"<script type=\"text/javascript\" src=\"https://momentjs.com/downloads/moment-with-locales.min.js\"></script>\r\n" + 
			"<script type=\"text/javascript\" src=\"https://unpkg.com/vis-timeline@latest/standalone/umd/vis-timeline-graph2d.min.js\"></script>\r\n" + 
			"<link href=\"https://unpkg.com/vis-timeline@latest/styles/vis-timeline-graph2d.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n" + 
			"</head>\r\n" + 
			"<body>\r\n" + 
			"<h2>Gráfico COVID-19</h2>\r\n" + 
			"<br />\r\n" + 
			"<div id=\"visualization\"></div>\r\n" + 
			"\r\n" + 
			"<script type=\"text/javascript\">\r\n" + 
			"\r\n" + 
			"    var container = document.getElementById('visualization');\r\n" + 
			"\r\n" + 
			"    var groups = new vis.DataSet([\r\n" + 
			"            {id: \"Casos\", content: \"Casos\"},\r\n" + 
			"            {id: \"Mortes\", content: \"Mortes\"}\r\n" + 
			"        ]\r\n" + 
			"    );\r\n" + 
			"\r\n" + 
			"    var items = [\r\n" + 
			"        :items:\r\n" + 
			"    ];\r\n" + 
			"\r\n" + 
			"    var dataset = new vis.DataSet(items);\r\n" + 
			"    var options = {\r\n" + 
			"        start: :data_inicio:,\r\n" + 
			"        end: :data_fim:,\r\n" + 
			"        legend: true,\r\n" + 
			"        locale: 'pt-br'\r\n" + 
			"    };\r\n" + 
			"  var graph2d = new vis.Graph2d(container, dataset, groups, options);\r\n" + 
			"</script>\r\n" + 
			"</body>\r\n" + 
			"</html>"; // HTML para ser salvo
	
	private FileWriter saida = null;
	private BufferedReader leitor = null;
	private String dataAnalisada = "";
	private String[] buffer;
	private int totalCasos = 0;
	private int totalMortes = 0;
	
	/**
	 * Buscar cidade no arquivo CSV
	 * @param entradaUsuario String com a cidade desejada pelo usuario
	 * @param local Local do arquivo
	 * @return true caso encontre a cidade no arquivo CSV, false c.c.
	 */
	public boolean buscaCidade(String entradaUsuario, String local)
	{
		String linha = "";
		
		try
		{
			leitor = new BufferedReader(new FileReader(local));
			while ( (linha = leitor.readLine()) != null)
			{
				linha = linha.toUpperCase().trim().replace(" ", "");
				
				if ((linha = Main.removerAcentos(linha)).contains(entradaUsuario) == true)
				{
					leitor.close();
					return true;
				}
					
			}
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			try 
			{		    
				leitor.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
			}
		}
		
		return false;
		
	}
	
	/**
	 * Gera gráfico com total de casos/mortes no pais
	 * <p>
	 * Le linha por linha do arquivo e captura data e salva, em um arquivo HTML, o modelo da variavel resultado com os casos e mortes da respectiva data. Apenas estados.
	 * </p>
	 * @param local Local do arquivo
	 */
	public void geraGraficoTotal(String local)
	{	
		
		String paraAdicionar = "";
		
		try
		{
			leitor = new BufferedReader(new FileReader(local));
			File arquivo = new File("Grafico.html");
			saida = new FileWriter(arquivo,true);
			String dataFim = leitor.readLine().split(",")[0];
			leitor.close();
			leitor = new BufferedReader(new FileReader(local));
				
			while ( leitor.ready()== true)
			{
				buffer = leitor.readLine().split(",");
				dataAnalisada = buffer[0];
					
				while(buffer[0].intern() == dataAnalisada.intern() && leitor.ready() == true)
				{
					if(buffer[3].intern() == "state") 
					{
	                     totalCasos += Integer.parseInt(buffer[4].intern());
						 totalMortes += Integer.parseInt(buffer[5].intern());
					     
					}
					buffer = leitor.readLine().split(",");
						
					}
					paraAdicionar += ("\t{x:'" + dataAnalisada + "', y:" + totalCasos + ", group: \"Casos\"},\n");
					paraAdicionar += ("\t{x:'" + dataAnalisada + "', y:" + totalMortes + ", group: \"Mortes\"},\n");
					totalCasos = 0;
					totalMortes = 0;
				}
				paraAdicionar = paraAdicionar.substring(0,paraAdicionar.length()-1);
				
				resultado = resultado.replace(":items:", paraAdicionar).replace(":data_inicio:", "'" + dataAnalisada + "'").replace(":data_fim:", "'"+dataFim+"'");
				
				saida.write(resultado);
				
				System.out.println("Página HTML gerada");
				
				
		}
		catch (FileNotFoundException e)
		{
				e.printStackTrace();
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
				
		}
		finally
		{
			try
			{
				leitor.close();
				saida.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
					
			}
		}		
		
		
	}
	
	/**
	 * Gera grafico com total de casos/mortes em um estado
	 * <p>
	 * Le linha por linha do arquivo e captura data e salva, em um arquivo HTML, o modelo da variavel resultado com casos e mortes da respectiva data. Apenas o estado solicitado pela usuario
	 * </p>
	 * @param entradaUsuario String com o estado desejado pelo usuario.
	 * @param local Local do arquivo
	 */
	public void geraGraficoEstado(String entradaUsuario, String local)
	{
		
		Estados d = Estados.valueOf(entradaUsuario);
		String estadoAnalisado = d.retornaEstado();
		System.out.println(local);
		
		String paraAdicionar = "";
		
		try
		{
			leitor = new BufferedReader(new FileReader(local));
			File arquivo = new File("Grafico.html");
			saida = new FileWriter(arquivo, true);
			String dataFim = leitor.readLine().split(",")[0];
			leitor.close();
			leitor = new BufferedReader(new FileReader(local));
			
			while ( leitor.ready() == true)
			{
				buffer = leitor.readLine().split(",");
				dataAnalisada = buffer[0];
				
				while(buffer[0].intern() == dataAnalisada.intern() && leitor.ready() == true)
				{
					if(buffer[1].intern() == estadoAnalisado && buffer[3].intern() == "state") 
					{
	                     totalCasos += Integer.parseInt(buffer[4].intern());
						 totalMortes += Integer.parseInt(buffer[5].intern());
					     
					}
					buffer = leitor.readLine().split(",");
						
				}
			    paraAdicionar += ("\t{x:'" + dataAnalisada + "', y:" + totalCasos + ", group: \"Casos\"},\n");
				paraAdicionar += ("\t{x:'" + dataAnalisada + "', y:" + totalMortes + ", group: \"Mortes\"},\n");
				totalCasos = 0;
				totalMortes = 0;
			}
			paraAdicionar = paraAdicionar.substring(0,paraAdicionar.length()-1);
			
			resultado = resultado.replace(":items:", paraAdicionar).replace(":data_inicio:", "'" + dataAnalisada + "'").replace(":data_fim:", "'"+dataFim+"'");
			
			saida.write(resultado);
			
			System.out.println("Página HTML gerada");
				
		}
		
		catch (FileNotFoundException e)
		{
				e.printStackTrace();
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
				
		}
		finally
		{
			try
			{
				leitor.close();
				saida.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
					
			}
		}
	}
	
	/**
	 * Gera gráfico com total de casos/mortes em uma cidade
	 * <p>
	 * Le linha por linha do arquivo e captura data e salva, em um arquivo HTML, o modelo da variavel resultado com casos e mortes da respectiva data.
	 * Apenas a cidade fornecida pelo usuario.
	 * </p>
	 * @param entradaUsuario String com a cidade desejada pelo usuario.
	 * @param local Local do arquivo
	 */
		
	public void geraGraficoCidade(String entradaUsuario, String local)
	{
        String paraAdicionar = "";
        
		
		try
		{
			leitor = new BufferedReader(new FileReader(local));
			File arquivo = new File("Grafico.html");
			saida = new FileWriter(arquivo,true);
			String dataFim = leitor.readLine().split(",")[0];
			leitor.close();
			leitor = new BufferedReader(new FileReader(local));
				
			while ( leitor.ready()== true)
			{
				buffer = Main.removerAcentos(leitor.readLine().toUpperCase().trim().replace(" ", "")).split(",");
				dataAnalisada = buffer[0];
					
				while(buffer[0].intern() == dataAnalisada.intern() && leitor.ready() == true)
				{
					if(buffer[2].intern() == entradaUsuario.intern()) 
					{
	                     totalCasos += Integer.parseInt(buffer[4].intern());
						 totalMortes += Integer.parseInt(buffer[5].intern());
					     
					}
					buffer = Main.removerAcentos(leitor.readLine().toUpperCase().trim().replace(" ", "")).split(",");
						
					}
					paraAdicionar += ("\t{x:'" + dataAnalisada + "', y:" + totalCasos + ", group: \"Casos\"},\n");
					paraAdicionar += ("\t{x:'" + dataAnalisada + "', y:" + totalMortes + ", group: \"Mortes\"},\n");
					totalCasos = 0;
					totalMortes = 0;
				}
				paraAdicionar = paraAdicionar.substring(0,paraAdicionar.length()-1);
				
				resultado = resultado.replace(":items:", paraAdicionar).replace(":data_inicio:", "'" + dataAnalisada + "'").replace(":data_fim:", "'"+dataFim+"'");
				
				saida.write(resultado);
				
				System.out.println("Página HTML gerada");
				
				
		}
		catch (FileNotFoundException e)
		{
				e.printStackTrace();
				
		}
		catch (IOException e)
		{
			e.printStackTrace();
				
		}
		finally
		{
			try
			{
				leitor.close();
				saida.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
					
			}
		}		
		
	}

}
