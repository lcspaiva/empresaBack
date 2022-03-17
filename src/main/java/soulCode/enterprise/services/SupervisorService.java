package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Cargo;
import soulCode.enterprise.models.Supervisor;
import soulCode.enterprise.repositories.CargoRepository;
import soulCode.enterprise.repositories.SupervisorRepository;


@Service
public class SupervisorService {
	
	@Autowired
	private SupervisorRepository supervisorRepository;
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired 
	private CargoRepository cargoRepository;
	
	public List<Supervisor> mostraTodosSupervisores(){
		return supervisorRepository.findAll();
	}
	
	public Supervisor buscarSupervisor(Integer id_supervisor) {
		Optional<Supervisor> supervisor =  supervisorRepository.findById(id_supervisor);
		return  supervisor.orElseThrow();
	}
	
	public Supervisor buscarSupervisorCargo(Integer id_cargo) {
		Supervisor supervisor = supervisorRepository.buscarSupervisorCargo(id_cargo);
		return supervisor;
	}
	
	public List<Supervisor> buscarSupervisoresLivres() {
		List<Supervisor> supervisoresLivres = supervisorRepository.buscarSupervisoresLivres();
		return supervisoresLivres;
	}
	
	public List<List> buscarSupervisorComSeuCargo(){
		List<List> supervisores = supervisorRepository.buscarSupervisorComSeuCargo();
		return supervisores;
	}
	
	public Supervisor buscarSupervisorByNome(String su_nome) {
		Supervisor supervisor = supervisorRepository.buscarSupervisorByNome(su_nome);
		return supervisor;
	}
	
	/*
	//essas funções estão comentadas uma vez que elas serão unidas em uma só função,
	public Supervisor insereSupervisorSemCargo(Supervisor supervisor){
		supervisor.setId_supervisor(null);
		return supervisorRepository.save(supervisor);
	}
	
	public Supervisor insereSupervisorComCargo(Integer id_cargo, Supervisor supervisor){
		supervisor.setId_supervisor(null);
		Cargo cargo = cargoService.buscaUmCargo(id_cargo);
		supervisor.setCargo(cargo);
		return supervisorRepository.save(supervisor);
	}
	*/
	
	public Supervisor insereSupervisor(Integer id_cargo, Supervisor supervisor) {
		supervisor.setId_supervisor(null);
		supervisor.setSu_foto(null);
		if(id_cargo != null) {
			Cargo cargo = cargoService.buscaUmCargo(id_cargo);
			supervisor.setCargo(cargo);
			cargo.setSupervisor(supervisor);
		}
		return supervisorRepository.save(supervisor);
	}
	
	/*
	//função by rafa
	public Supervisor editaSupervisor(Integer id_cargo, Supervisor supervisor) {
		//a edição envolve mudança de turma
		if(id_cargo != null) {
			Supervisor obj = buscarSupervisor(supervisor.getId_supervisor());
			Cargo cargoAnterior = obj.getCargo();
			
			if(cargoAnterior != null) {
				cargoAnterior.setSupervisor(null);
				cargoRepository.save(cargoAnterior);
			}
			
			Cargo cargoAtual = cargoService.buscaUmCargo(id_cargo);
			supervisor.setCargo(cargoAtual);
			cargoAtual.setSupervisor(supervisor);
		}
		return supervisorRepository.save(supervisor);
	}
	*/
	
	
	public Supervisor editarSupervisor(Supervisor supervisor) {
		Integer id_cargo =  supervisorRepository.buscarCargoPeloSupervisor(supervisor.getId_supervisor());
		if(id_cargo == null) {
			buscarSupervisor(supervisor.getId_supervisor());
			return supervisorRepository.save(supervisor);
		}else {
			supervisor.setCargo(cargoService.buscaUmCargo(id_cargo));
			return supervisorRepository.save(supervisor);
		}
	}
	
	public Supervisor salvarFoto(Integer id_supervisor, String caminhoFoto) {
		Supervisor supervisor = buscarSupervisor(id_supervisor);
		supervisor.setSu_foto(caminhoFoto);
		return supervisorRepository.save(supervisor);
	}
	
	//deleta um supervisor, se o supervisor estiver sem cargo supervisionada simplesmente deleta ele. Caso ele tenha um cargo em supervisão
	// deixa o cargo sem supervisão e deletar o supervisor
	public void deletarSupervisor(Integer id_supervisor) {
		buscarSupervisor(id_supervisor);
		Integer id_cargo = checaID(id_supervisor);
		if(id_cargo == null) {
			supervisorRepository.deleteById(id_supervisor);
		}else {
			cargoService.deixarCargoSemSupervisor(id_cargo, id_supervisor);
			supervisorRepository.deleteById(id_supervisor);
		}
	}
	
	public Integer checaID(Integer id_supervisor) {
		return supervisorRepository.buscarCargoPeloSupervisor(id_supervisor);
	}
	
	public List<List> supervisorRegistro(){
		List<List> registros = supervisorRepository.supervisorRegistro();
		return registros;
	}
}
