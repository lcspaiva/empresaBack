package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulCode.enterprise.models.Supervisor;

/**
 * interface que define as funções para efetuar o acesso aos dados presentes no banco de dados
 * @author lucas
 * @author tatiana
 */
public interface SupervisorRepository extends JpaRepository<Supervisor, Integer>{
	
	/**
	 * retorna o registro de um supervisor cujo id do cargo casa com o id passado por parametro
	 * @param id_cargo id do cargo que deve ter seus supervisor associado retornado
	 * @return obj do supervisor
	 */
	@Query(value = "SELECT * FROM supervisor WHERE id_cargo = :id_cargo", nativeQuery = true)
	Supervisor buscarSupervisorCargo(Integer id_cargo);
	
	/**
	 * retorna todos os supervisores que não estão supervisionando nenhum cargo
	 * @return lista contendo todos os supervisores que estão livres de supervisão
	 */
	@Query(value = "SELECT * FROM supervisor WHERE id_cargo IS NULL", nativeQuery = true)
	List<Supervisor> buscarSupervisoresLivres();
	
	/**
	 * lista todas as infomações de todos os servidores mais todas as informações de seu cargo associado
	 * @return lista contendo todos os supervisores mais o join com o cargo
	 */
	@Query(value = "SELECT supervisor.id_supervisor, supervisor.su_nome, supervisor.su_setor, cargo.id_cargo, cargo.car_nome, cargo.car_descricao FROM supervisor LEFT JOIN cargo ON supervisor.id_supervisor = cargo.id_supervisor", nativeQuery = true)
	List<List> buscarSupervisorComSeuCargo();
	
	/**
	 * retorna um objeto servidor em função da busca pelo seu nome 
	 * @param su_nome nome a ser pesquisado
	 * @return objeto do tipo supevisor
	 */
	@Query(value = "SELECT * FROM supervisor WHERE su_nome = :su_nome", nativeQuery = true)
	Supervisor buscarSupervisorByNome(String su_nome);
	
	/**
	 * retorna o cargo que está sendo supervisionado por aquele supervisor
	 * @param id_supervisor id do supervisor a ser pesquisado
	 * @return id do cargo
	 */
	@Query(value = "SELECT id_cargo FROM supervisor WHERE id_supervisor = :id_supervisor", nativeQuery = true)
	Integer buscarCargoPeloSupervisor(Integer id_supervisor);
	
	/**
	 * retorna todos supervisores e sues respectivos cargos
	 * @return lista contendo todos os supervisores
	 */
	@Query(value = "SELECT supervisor.id_supervisor, supervisor.su_nome, supervisor.su_setor, supervisor.su_foto, cargo.id_cargo, cargo.car_nome, cargo.car_descricao FROM supervisor LEFT JOIN cargo ON supervisor.id_supervisor = cargo.id_supervisor", nativeQuery = true)
	List<List> supervisorRegistro();
}
