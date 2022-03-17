package soulCode.enterprise.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

//anotação dizendo que a classe será um modelo para o banco de dados
@Entity
public class Funcionario {
	
	//anotação para indicar que o Id_funcionario é o id da tabela
	//anotação para indicar que é um numero gerado e único
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id_funcionario;
	
	//anotação para indicar que o atributo fará parte de uma coluna juntamente com regras da coluna
	@Column(nullable = false, length = 60)
	private String func_nome;
	
	@Column(nullable = false, length = 60)
	private String func_cidade;
	
//	@Column(nullable = false, length = 60)
//	private String func_cargo;
	
	@Column(nullable = true)
	private String func_foto;
	
	//dizendo que a tabela cargo virá aqui dentro, com o nome de id_cargo, ou seja, só o id vem ai dentro, dps ele dá o join
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_cargo")
	private Cargo cargo;
	
	//************************ Getters e Setters ********************************
	//serão utilizados para extrair ou setar os dados quando chamarmos os objetos na aplicação
	public Integer getId_funcionario() {
		return Id_funcionario;
	}

	public void setId_funcionario(Integer id_funcionario) {
		Id_funcionario = id_funcionario;
	}

	public String getFunc_nome() {
		return func_nome;
	}

	public void setFunc_nome(String func_nome) {
		this.func_nome = func_nome;
	}

	public String getFunc_cidade() {
		return func_cidade;
	}

	public void setFunc_cidade(String func_cidade) {
		this.func_cidade = func_cidade;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getFunc_foto() {
		return func_foto;
	}

	public void setFunc_foto(String func_foto) {
		this.func_foto = func_foto;
	}

}
