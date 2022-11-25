package com.william.os.domain.enums;

public enum Prioridade {
	
	/* Colocar valores manualmente para melhor 
	 * controle caso outras prioridades sejam criadas
	*/
	
	
	BAIXA(0, "BAIXA"), 
	MEDIA(1, "MEDIA"),
	ALTA(2, "ALTA");
	
	
	//Criar atributos referentes aos dados entre parenteses
	
	private Integer cod;
	private String descricao;
	
	
	private Prioridade(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}


	public Integer getCod() {
		return cod;
	}


	public String getDescricao() {
		return descricao;
	}
	
	
	//Método estático não precisa ser instanciado
	public static Prioridade toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
	//Para cada objeto 'Prioridade' dentro da classe Prioridade e seu valores retorne valor
		
		for(Prioridade x : Prioridade.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Prioridade inválida!" + cod);
	}
	

}
