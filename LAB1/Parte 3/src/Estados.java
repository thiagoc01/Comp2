
public enum Estados {
	
	ACRE("AC"), 
	ALAGOAS("AL"), 
	AMAZONAS("AM"), 
	AMAPA("AP"), 
	BAHIA("BA"), 
	CEARA("CE"),
	DISTRITOFEDERAL("DF"),
	ESPIRITOSANTO("ES"),
	GOIAS("GO"),
	MARANHAO("MA"),
	MATOGROSSO("MT"),
	MATOGROSSODOSUL("MS"),
	MINASGERAIS("MG"),
	PARA("PA"),
	PARAIBA("PB"),
	PARANA("PR"),
	PERNAMBUCO("PE"),
	PIAUI("PI"),
	RIODEJANEIROESTADO("RJ"),
	RIOGRANDEDONORTE("RN"),
	RIOGRANDEDOSUL("RS"),
	RONDONIA("RO"),
	RORAIMA("RR"),
	SANTACATARINA("SC"),
	SAOPAULOESTADO("SP"),
	SERGIPE("SE"),
	TOCANTINS("TO");

	
	private String estados;
	
	Estados(String estados)
	{
		this.estados = estados;
	}
	
	public String retornaEstado()
	{
		return estados;
	}
}
