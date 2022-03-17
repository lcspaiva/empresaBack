package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Cargo;
import soulCode.enterprise.models.Supervisor;
import soulCode.enterprise.repositories.CargoRepository;
import soulCode.enterprise.services.exceptions.ObjectNotFoundException;
import soulCode.enterprise.services.exceptions.DataEntegrityViolationException;

@Service
public class CargoService {
	
	@Autowired
	CargoRepository cargoRepository;
	
	@Lazy
	@Autowired
	FuncionarioService funcionarioService;
	
	@Lazy
	@Autowired
	SupervisorService supervisorService;
	
	public List<Cargo> mostraTodosCargos(){
		List<Cargo> cargos = cargoRepository.findAll();
		return cargos;
	}
	
	public Cargo buscaUmCargo(Integer id_cargo) {
		Optional<Cargo> cargo = cargoRepository.findById(id_cargo);
		return cargo.orElseThrow(() -> new ObjectNotFoundException("Cargo não cadastrado, id buscado: " + id_cargo));
	}
	
	public Cargo cargoDoSupervisor(Integer id_supervisor) {
		Cargo cargo = cargoRepository.cargoDoSupervisor(id_supervisor);
		return cargo;
	}
	
	public List<List> cargoComSeuSupervisor(){
		List<List> cargos = cargoRepository.cargoComSeuSupervisor();
		return cargos;
	}
	
	public List<Cargo> cargoSemSupervisor(){
		List<Cargo> cargos = cargoRepository.cargoSemSupervisor();
		return cargos;
	}
	
	//vai cadastrar o cargo ja contendo um supervisor
	public Cargo cadastrarCargo(Integer id_supervisor, Cargo cargo) {
		cargo.setId_cargo(null);
		if(id_supervisor != null) {
			Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
			cargo.setSupervisor(supervisor);
		}
		return cargoRepository.save(cargo);
	}
	
	public Cargo editaCargo(Cargo cargo) {
		buscaUmCargo(cargo.getId_cargo());
		return cargoRepository.save(cargo);
	}
	
	
	public void deletarCargo(Integer id_cargo) {
		Cargo cargo = buscaUmCargo(id_cargo);
		try{
			Supervisor supervisor = cargo.getSupervisor();
			//tem supervisor
			if(supervisor != null) {
				Integer id_supervisor = supervisor.getId_supervisor();
				deixarCargoSemSupervisor(id_cargo, id_supervisor);
			}
			funcionarioService.desvinculaFuncionarios(id_cargo);
			cargoRepository.deleteById(id_cargo);
			
		
		}catch(org.springframework.dao.DataIntegrityViolationException e){
			throw new soulCode.enterprise.services.exceptions.DataEntegrityViolationException("A turma não pode ser deletada pq tem alunos associados");
		}
	}
		
	/** faz a vinculação de um supervisor a um cargo 
	 * @param id_cargo id do cargo que receberá o supervisor
	 * @param id_supervisor id do supervisor que será vinculado àquele cargo
	 */
	public Cargo atribuirSupervisor(Integer id_cargo, Integer id_supervisor) {
		Cargo cargo = buscaUmCargo(id_cargo);
		Supervisor supervisorAnterior = supervisorService.buscarSupervisorCargo(id_cargo);
		Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
		
		if(cargo.getSupervisor() != null) {
			cargo.setSupervisor(null);
			supervisorAnterior.setCargo(null);
		}
		
		cargo.setSupervisor(supervisor);
		supervisor.setCargo(cargo);
		return cargoRepository.save(cargo);
	}
	
	public Cargo deixarCargoSemSupervisor(Integer id_cargo, Integer id_supervisor) {
		Cargo cargo = buscaUmCargo(id_cargo);
		cargo.setSupervisor(null);
		
		Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
		supervisor.setCargo(null);
		
		return cargoRepository.save(cargo);
	}
}
