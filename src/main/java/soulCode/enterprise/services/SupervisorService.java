package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Cargo;
import soulCode.enterprise.models.Supervisor;
import soulCode.enterprise.repositories.CargoRepository;
import soulCode.enterprise.repositories.SupervisorRepository;


/**
 * classe de serviço referente as operações com os supervisores
 * @author lucas
 * @author tatiana
 */
@Service
public class SupervisorService {
	
	@Autowired
	private SupervisorRepository supervisorRepository;
	
	@Autowired
	private CargoService cargoService;
	
	@Autowired 
	private CargoRepository cargoRepository;
	
	/**
	 * lista todos os supervisores presentes na tabela de supervisores
	 * @return list de supervisor
	 */
	public List<Supervisor> mostraTodosSupervisores(){
		return supervisorRepository.findAll();
	}
	
	/**
	 * busca um supervisor por meio de seu id
	 * @param id_supervisor id od supervisor a ser pesquisado e retornado
	 * @return obj supervisor
	 */
	public Supervisor buscarSupervisor(Integer id_supervisor) {
		Optional<Supervisor> supervisor =  supervisorRepository.findById(id_supervisor);
		return  supervisor.orElseThrow();
	}
	
	/**
	 * pesquisa um supervisor pro meio do id de um cargo
	 * @param id_cargo id do cargo que deve ter seu supervisor retornado
	 * @return obj supervisor
	 */
	public Supervisor buscarSupervisorCargo(Integer id_cargo) {
		Supervisor supervisor = supervisorRepository.buscarSupervisorCargo(id_cargo);
		return supervisor;
	}
	
	/**
	 * lista todos os supervisores que não estão supervisionando nenhum cargo
	 * @return list de supervisor
	 */
	public List<Supervisor> buscarSupervisoresLivres() {
		List<Supervisor> supervisoresLivres = supervisorRepository.buscarSupervisoresLivres();
		return supervisoresLivres;
	}
	
	/**
	 * lista todos os supervisores e seus dados mais os dados de seus respectivos cargos
	 * @return list supervisor + cargos
	 */
	public List<List> buscarSupervisorComSeuCargo(){
		List<List> supervisores = supervisorRepository.buscarSupervisorComSeuCargo();
		return supervisores;
	}
	
	/**
	 * busca o registro de um supervisor tendo seu nome como chave de busca
	 * @param su_nome nome a ser procurado
	 * @return obj supervisor
	 */
	public Supervisor buscarSupervisorByNome(String su_nome) {
		Supervisor supervisor = supervisorRepository.buscarSupervisorByNome(su_nome);
		return supervisor;
	}
	
	/**
	 * faz a inserção de um obj supervisor na tabela no banco. Essa inserção pode vir com um cargo ou não
	 * @param id_cargo id do cargo que será supervisionado pelo novo supervisor
	 * @param supervisor dados do supervisor que será inserido
	 * @return obj do supervisor que foi inserido
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
	
	/**
	 * faz a edição dos dados de um supervisor
	 * @param supervisor novos dados do supervisor que sobrescreverão os antigos
	 * @return obj do supervisor que foi alterado
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
	
	/**
	 * faz o salvamento da foto do supervisor
	 * @param id_supervisor id que receberá a foto
	 * @param caminhoFoto caminho da foto no repositorio
	 * @return obj do supervisor que teve a foto associada
	 */
	public Supervisor salvarFoto(Integer id_supervisor, String caminhoFoto) {
		Supervisor supervisor = buscarSupervisor(id_supervisor);
		supervisor.setSu_foto(caminhoFoto);
		return supervisorRepository.save(supervisor);
	}
	

	/**	
	 * deleta um supervisor, se o supervisor estiver sem cargo supervisionada simplesmente deleta ele. Caso ele tenha um cargo em supervisão deixa o cargo sem supervisão e deletar o supervisor 
	 * @param id_supervisor id do supervisor que será deletado
	 */
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
	
	/**
	 * retorna o id do cargo que o supervisor está supervisionando
	 * @param id_supervisor id do supervisor que terá seu cargo retornado
	 * @return obj cargo
	 */
	public Integer checaID(Integer id_supervisor) {
		return supervisorRepository.buscarCargoPeloSupervisor(id_supervisor);
	}
	
	/**
	 * retorna os dados do supervisor, incluindo seu cargo e sua foto
	 * @return list de supervisor+cargo
	 */
	public List<List> supervisorRegistro(){
		List<List> registros = supervisorRepository.supervisorRegistro();
		return registros;
	}
}
