package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulCode.enterprise.models.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Integer>{
	
	@Query(value = "SELECT * FROM cargo WHERE id_supervisor IS NULL", nativeQuery = true)
	List<Cargo> cargoSemSupervisor();
	
	//retorna o cargo que aquele supervisor supervisiona
	@Query(value = "SELECT * FROM cargo WHERE id_supervisor = :id_supervisor", nativeQuery = true)
	Cargo cargoDoSupervisor(Integer id_supervisor);
	
	@Query(value = "SELECT cargo.id_cargo, cargo.car_nome, cargo.car_descricao, supervisor.id_supervisor, supervisor.su_nome, supervisor.su_setor FROM cargo LEFT JOIN supervisor ON supervisor.id_cargo = cargo.id_cargo ORDER BY cargo.id_cargo", nativeQuery = true)
	List<List> cargoComSeuSupervisor();
}
