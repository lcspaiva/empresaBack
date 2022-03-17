package soulCode.enterprise.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Supervisor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_supervisor;
	
	@Column(nullable = false, length = 60)
	private String su_nome;
	
	@Column(nullable = true)
	private String su_foto;
	
	@Column(nullable = true, length = 40)
	private String su_setor;
	
	//anotação de relacionamento (chave estrangeira)
	//o unique é pra travar o relacionamento 1p/1
	//o Cargo cargo automaticamente vai puxar a chave primária da tabela Cargo e colocar ela dentro da tabela Supervisor com o nome de id_cargo
	@JsonIgnore //o jsonignore pode ser suprimido pq ele já esta no funcionario, como é uma cadeia vc só precisa ter ele em um dos links para evitar o loop de importações
	@OneToOne
	@JoinColumn(name = "id_cargo", unique = true)
	private Cargo cargo;

	public Integer getId_supervisor() {
		return id_supervisor;
	}

	public void setId_supervisor(Integer id_supervisor) {
		this.id_supervisor = id_supervisor;
	}

	public String getSu_nome() {
		return su_nome;
	}

	public void setSu_nome(String su_nome) {
		this.su_nome = su_nome;
	}

	public String getSu_foto() {
		return su_foto;
	}

	public void setSu_foto(String su_foto) {
		this.su_foto = su_foto;
	}

	public String getSu_setor() {
		return su_setor;
	}

	public void setSu_setor(String su_setor) {
		this.su_setor = su_setor;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	
}

