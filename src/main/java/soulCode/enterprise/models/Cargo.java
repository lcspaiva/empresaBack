package soulCode.enterprise.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cargo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_cargo;
	
	@Column(nullable = false, length=40)
	private String car_nome;
	
	@Column(nullable = false, length=40)
	private String car_descricao;
	
	//armazenará a lista de aluno que teremos para cada turma
	//anotação para indicar a relação entre turma e aluno. Ela será mapeada pelo atributo turma dentro do aluno
	//tem que alterar no modelo Aluno
	@OneToMany(mappedBy = "cargo")
	private List<Funcionario> funcionarios = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "id_supervisor", unique = true)
	private Supervisor supervisor;

	public Integer getId_cargo() {
		return id_cargo;
	}

	public void setId_cargo(Integer id_cargo) {
		this.id_cargo = id_cargo;
	}

	public String getCar_nome() {
		return car_nome;
	}

	public void setCar_nome(String car_nome) {
		this.car_nome = car_nome;
	}

	public String getCar_descricao() {
		return car_descricao;
	}

	public void setCar_descricao(String car_descricao) {
		this.car_descricao = car_descricao;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
}
