package soulCode.enterprise.models;

/**
 *  classe de enumeração dos estados possiveis para o pagamento
 * @author tatiana
 * @author lucas
 *
 */
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
