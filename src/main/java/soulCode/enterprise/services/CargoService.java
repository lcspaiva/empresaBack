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

/**
 * classe de serviço referente as operações com os cargos
 * @author lucas
 * @author tatiana
 *
 */
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
	
	/**
	 * lista todos os cargos presentes no banco
	 * @return list de cargos
	 */
	public List<Cargo> mostraTodosCargos(){
		List<Cargo> cargos = cargoRepository.findAll();
		return cargos;
	}
	
	/**
	 * busca um cargo mediante um id
	 * @param id_cargo id do cargo a ser pesquisado
	 * @return obj de um cargo
	 */
	public Cargo buscaUmCargo(Integer id_cargo) {
		Optional<Cargo> cargo = cargoRepository.findById(id_cargo);
		return cargo.orElseThrow(() -> new ObjectNotFoundException("Cargo não cadastrado, id buscado: " + id_cargo));
	}
	
	public Cargo cargoDoSupervisor(Integer id_supervisor) {
		Cargo cargo = cargoRepository.cargoDoSupervisor(id_supervisor);
		return cargo;
	}
	
	/**
	 * lista todos os supervisores mais os dados de seus respectivos cargos
	 * @return lista de supervisores e cargos
	 */
	public List<List> cargoComSeuSupervisor(){
		List<List> cargos = cargoRepository.cargoComSeuSupervisor();
		return cargos;
	}
	
	/**
	 * lista os cargos que estão sem supervisor associado
	 * @return lista de cargos
	 */
	public List<Cargo> cargoSemSupervisor(){
		List<Cargo> cargos = cargoRepository.cargoSemSupervisor();
		return cargos;
	}
	
	//vai cadastrar o cargo ja contendo um supervisor
	/**
	 * faz o cadastro de um cargo na base. Caso seja fornecido um id de supervisor, o cargo será cadastrado já tendo um supervisor associado, caso contrário o cargo será cadastrado sem supervisão.
	 * @param id_supervisor id do supervisor
	 * @param cargo dados do cargo
	 * @return registro cadastrado no banco
	 */
	public Cargo cadastrarCargo(Integer id_supervisor, Cargo cargo) {
		cargo.setId_cargo(null);
		if(id_supervisor != null) {
			Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
			cargo.setSupervisor(supervisor);
		}
		return cargoRepository.save(cargo);
	}
	
	/**
	 * faz a edição das informações de um cargo
	 * @param cargo dados a serem alterados
	 * @return registro cadastrado no banco
	 */
	public Cargo editaCargo(Cargo cargo) {
		buscaUmCargo(cargo.getId_cargo());
		return cargoRepository.save(cargo);
	}
	
	
	/**
	 * faz a deleção de um cargo, se o mesmo possuir supervisor ou funcionarios alocados, os mesmos serão desvinculados.
	 * @param id_cargo id do cargo a ser deletado
	 */
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
	
	/**
	 * remove o supervisor associado aquela cargo
	 * @param id_cargo id do cargo que deve ter seu supervisor removido
	 * @param id_supervisor id do supervisor que será desvinculado do cargo
	 * @return obj do cargo que foi deixado sem supervisor
	 */
	public Cargo deixarCargoSemSupervisor(Integer id_cargo, Integer id_supervisor) {
		Cargo cargo = buscaUmCargo(id_cargo);
		cargo.setSupervisor(null);
		
		Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
		supervisor.setCargo(null);
		
		return cargoRepository.save(cargo);
	}
}
