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
/**
 * interface que define as funções para efetuar o acesso aos dados presentes no banco de dados
 * @author lucas
 * @author tatiana
 */
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{
	
	/**
	 *  retornar uma lista contendo todas as colunas dos funcionarios que pertencem a um determinado cargo passado por parâmetro
	 * @param id_cargo cargo que deve ter seus funcionarios associados listados
	 * @return lista de obj do tipo cargo
	 */
	@Query(value = "SELECT * FROM funcionario WHERE id_cargo = :id_cargo", nativeQuery = true)
	List<Funcionario> fetchByCargo(Integer id_cargo);
	
	/**
	 * retorna o id do cargo mediante o id do funcionario, ou seja, qual é o id do cargo no qual o funcionario está alocado
	 * @param id_funcionario id do funcionario que deve ter o id do seu cargo retornado
	 * @return id do cargo do funcionario
	 */
	@Query(value = "SELECT id_cargo FROM funcionario WHERE id_funcionario = :id_funcionario", nativeQuery = true)
	String fetchByIdFunc(String id_funcionario);

	/**
	 *  traz as uma lista contendo todos os funcionarios mais todas as suas informações mais as informaçoes de seus respectivos cargos
	 * @return lista contendo o join entre funcionario e cargo
	 */
	@Query(value = "SELECT funcionario.id_funcionario, funcionario.func_nome, funcionario.func_cidade, cargo.car_nome, cargo.car_descricao, cargo.id_cargo FROM funcionario LEFT JOIN cargo ON funcionario.id_cargo = cargo.id_cargo", nativeQuery = true)
	List<List> funcionarioComCargo();
	
	/**
	 * seta os cargos dos funcionarios cujo id do cargo sejam iguais ao valor passado por parâmetro 
	 * @param id_cargo id do cargo que deve ser removido dos funcionarios associados
	 */
	@Modifying
	@Transactional 
	@Query(value = "UPDATE funcionario SET id_cargo = null WHERE id_cargo = :id_cargo", nativeQuery = true)
	void desvinculaFuncionarios(Integer id_cargo);
	
	/**
	 * traz as informações do funcionario em um join com o cargo junto da FOTO do funcionario
	 * @return lista contendo o join entre funcionario e cargo
	 */
	@Query(value = "SELECT funcionario.id_funcionario, funcionario.func_nome, funcionario.func_cidade, funcionario.func_foto, cargo.car_nome, cargo.car_descricao, cargo.id_cargo FROM funcionario LEFT JOIN cargo ON funcionario.id_cargo = cargo.id_cargo", nativeQuery = true)
	List<List> funcionarioRegistro();
	
	/**
	 * retorna o id do cargo de um funcionario cujo id é passado por parâmetro
	 * @param id_funcionario id do funcionario que deve ter o id do seu cargo retornado
	 * @return id do cargo do funcionario
	 */
	@Query(value = "SELECT id_cargo FROM funcionario WHERE id_funcionario = :id_funcionario", nativeQuery = true)
	Integer cargoFuncionario(Integer id_funcionario);
}
