package com.william.os.domain.enums;

public enum Status {

	
	
	/* Colocar valores manualmente para melhor 
	 * controle caso outras prioridades sejam criadas
	*/
	
	ABERTO(0, "ABERTO"), 
	ANDAMENTO(1, "ANDAMENTO"),
	ENCERRADO(2, "ENCERRADO");
	
	
	//Criar atributos referentes aos dados entre parenteses
	
	private Integer cod;
	private String descricao;
	
	
	private Status(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}


	public Integer getCod() {
		return cod;
	}


	public String getDescricao() {
		return descricao;
	}
	
	public static Status toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
	//Para cada objeto 'Status' dentro da classe Status e seu valores retorne valor
		
		for(Status x : Status.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Status inválido!" + cod);
	}
	

}


