package soulCode.enterprise.models;

public enum Categoria {
	DEPOSITADO("Depositado"),
	CANCELADO("Cancelado"),
	PENDENTE("Pendente");
	
	private String descricao;
	
	Categoria(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
