package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulCode.enterprise.models.Supervisor;


public interface SupervisorRepository extends JpaRepository<Supervisor, Integer>{
	
	@Query(value = "SELECT * FROM supervisor WHERE id_cargo = :id_cargo", nativeQuery = true)
	Supervisor buscarSupervisorCargo(Integer id_cargo);

	@Query(value = "SELECT * FROM supervisor WHERE id_cargo IS NULL", nativeQuery = true)
	List<Supervisor> buscarSupervisoresLivres();
	
	@Query(value = "SELECT supervisor.id_supervisor, supervisor.su_nome, supervisor.su_setor, cargo.id_cargo, cargo.car_nome, cargo.car_descricao FROM supervisor LEFT JOIN cargo ON supervisor.id_supervisor = cargo.id_supervisor", nativeQuery = true)
	List<List> buscarSupervisorComSeuCargo();
	
	@Query(value = "SELECT * FROM supervisor WHERE su_nome = :su_nome", nativeQuery = true)
	Supervisor buscarSupervisorByNome(String su_nome);
	
	//retorna o cargo que está sendo supervisionado por aquele supervisor
	@Query(value = "SELECT id_cargo FROM supervisor WHERE id_supervisor = :id_supervisor", nativeQuery = true)
	Integer buscarCargoPeloSupervisor(Integer id_supervisor);
	
	//retorna todos os cargos do supervisor
	@Query(value = "SELECT supervisor.id_supervisor, supervisor.su_nome, supervisor.su_setor, supervisor.su_foto, cargo.id_cargo, cargo.car_nome, cargo.car_descricao FROM supervisor LEFT JOIN cargo ON supervisor.id_supervisor = cargo.id_supervisor", nativeQuery = true)
	List<List> supervisorRegistro();
}
