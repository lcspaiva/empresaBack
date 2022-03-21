package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulCode.enterprise.models.Cargo;

/**
 * interface que define as funções para efetuar o acesso aos dados presentes no banco de dados
 * @author lucas
 */
public interface CargoRepository extends JpaRepository<Cargo, Integer>{
	
	/**
	 * retorna todas as colunas presentes na tabela de cargo para aqueles cargos que não estao sendo supervionados
	 * @return lista contendo os cargos sem supervisao
	 */
	@Query(value = "SELECT * FROM cargo WHERE id_supervisor IS NULL", nativeQuery = true)
	List<Cargo> cargoSemSupervisor();
	
	/**
	 * retorna todas as colunas de um determinado cargo supervisionado por um id de supervisor passado por parâmetro
	 * @param id_supervisor id do supervisor que deve ter seu cargo supervisionado passado por parâmetro
	 * @return objeto cargo
	 */
	@Query(value = "SELECT * FROM cargo WHERE id_supervisor = :id_supervisor", nativeQuery = true)
	Cargo cargoDoSupervisor(Integer id_supervisor);
	
	/**
	 * retorna todas as colunas do supervisor mais todas as colunas do cargo supervisionado por ele
	 * @return lista contendo o join entre cargo e supervisor
	 */
	@Query(value = "SELECT cargo.id_cargo, cargo.car_nome, cargo.car_descricao, supervisor.id_supervisor, supervisor.su_nome, supervisor.su_setor FROM cargo LEFT JOIN supervisor ON supervisor.id_cargo = cargo.id_cargo ORDER BY cargo.id_cargo", nativeQuery = true)
	List<List> cargoComSeuSupervisor();
}
