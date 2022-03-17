package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import soulCode.enterprise.models.Funcionario;

//aqui declaramos as interfaces que o aluno possuirá, ou seja, métodos que ele pode utilizar. Como usamos a interface do Jpa, temos alguns 
//métodos já prontos

//os parametros do repositorio são o nome da tabela e o tipo do atributo que está servindo como identificador
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{
	
	//query para retornar todos os funcionarios que pertencem a um determinado cargo passado por parâmetro
	@Query(value = "SELECT * FROM funcionario WHERE id_cargo = :id_cargo", nativeQuery = true)
	List<Funcionario> fetchByCargo(Integer id_cargo);
	
	//query para retornar o id do cargo mediante o id do funcionario, ou seja, qual é o id do cargo daquele funcionario		
	@Query(value = "SELECT id_cargo FROM funcionario WHERE id_funcionario = :id_funcionario", nativeQuery = true)
	String fetchByIdFunc(String id_funcionario);
	
	//query para trazer as informações do funcionario em um join com o cargo
	@Query(value = "SELECT funcionario.id_funcionario, funcionario.func_nome, funcionario.func_cidade, cargo.car_nome, cargo.car_descricao, cargo.id_cargo FROM funcionario LEFT JOIN cargo ON funcionario.id_cargo = cargo.id_cargo", nativeQuery = true)
	List<List> funcionarioComCargo();
	
	//query para remover os cargos dos funcionarios
	@Modifying
	@Transactional 
	@Query(value = "UPDATE funcionario SET id_cargo = null WHERE id_cargo = :id_cargo", nativeQuery = true)
	void desvinculaFuncionarios(Integer id_cargo);
	
	//query para trazer as informações do funcionario em um join com o cargo junto da FOTO do funcionario
	@Query(value = "SELECT funcionario.id_funcionario, funcionario.func_nome, funcionario.func_cidade, funcionario.func_foto, cargo.car_nome, cargo.car_descricao, cargo.id_cargo FROM funcionario LEFT JOIN cargo ON funcionario.id_cargo = cargo.id_cargo", nativeQuery = true)
	List<List> funcionarioRegistro();
	
	@Query(value = "SELECT id_cargo FROM funcionario WHERE id_funcionario = :id_funcionario", nativeQuery = true)
	Integer cargoFuncionario(Integer id_funcionario);
}
