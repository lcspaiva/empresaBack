package soulCode.enterprise.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import soulCode.enterprise.models.Cargo;
import soulCode.enterprise.models.Supervisor;
import soulCode.enterprise.services.SupervisorService;

@CrossOrigin
@RestController
@RequestMapping("empresa")
/**
 * classe de controle das funções do supervisor
 * @author tatiana
 * @author lucas
 *
 */
public class SupervisorController {
	
	@Autowired
	private SupervisorService supervisorService;
	
	/**
	 * lista todos os supervisores que estão cadastrados na base de dados
	 * @return lista contendo todos os supervisores que estão na base de dados
	 */
	@GetMapping("/supervisor")
	public List<Supervisor> mostraTodosSupervisores() {
		List<Supervisor> supervisores = supervisorService.mostraTodosSupervisores();
		return supervisores;
	}
	
	/**
	 * retorna os dados de um supervisor especifico
	 * @param id_supervisor id do supervisor que deve ser pesquisado
	 * @return obj do supervisor contendo os dados
	 */
	@GetMapping("/supervisor/{id_supervisor}")
	public Supervisor buscarSupervisor(@PathVariable Integer id_supervisor) {
		Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
		return supervisor;
	}
	
	/**
	 * retorna os dados do supervisor que está atrelado àquele cargo
	 * @param id_cargo id do cargo que deve ser pesquisado
	 * @return obj do supervisor contendo seus dados
	 */
	@GetMapping("/supervisor-cargo/{id_cargo}")
	public ResponseEntity<Supervisor> buscarSupervisorCargo(@PathVariable Integer id_cargo){
		Supervisor supervisor = supervisorService.buscarSupervisorCargo(id_cargo);
		return ResponseEntity.ok().body(supervisor);
	}
	
	/**
	 * retorna todos os supervisores que não estão supervionando nenhum cargo
	 * @return lista contendo todos os supervisores sem cargo
	 */
	@GetMapping("/supervisor-livres")
	public List<Supervisor> supervisoresLivres(){
		List<Supervisor> supervisoresLivres = supervisorService.buscarSupervisoresLivres();
		return supervisoresLivres; 
	}
	
	
	//traz o join do supervisor com o cargo
	/**
	 * traz os dados de todos os supervisores e seus respectivos cargos
	 * @return lista contando os supervisores e seus cargos (caso possuam)
	 */
	@GetMapping("/supervisor/supervisor-cargo")
	public List<List> supervisorComCargo(){
		List<List> supervisores = supervisorService.buscarSupervisorComSeuCargo();
		return supervisores;
	}
	
	/**
	 * busca um supervisor pelo seu nome
	 * @param su_nome nome do supervisor a ser procurado
	 * @return dados do supervisor encontrado, null caso contrario
	 */
	@GetMapping("/supervisor/buscarSupervisorByNome/{su_nome}")
	public Supervisor buscarSupervisorByName(@PathVariable String su_nome) {
		Supervisor supervisor = supervisorService.buscarSupervisorByNome(su_nome);
		return supervisor;
	}
	
	/**
	 * insere um supervisor novo no banco de dados já tendo um cargo associado a ele
	 * @param id_cargo cargo que deve ser associado ao novo supervisor
	 * @param supervisor dados do supervisor que será inserido no banco
	 * @return obj do supervisor que foi recém adcionado
	 */
	@PostMapping("/supervisor")
	public ResponseEntity<Supervisor> insereSupervisorComCargo(@RequestParam(name="cargo", required = false) Integer id_cargo, @RequestBody Supervisor supervisor){
		supervisor = supervisorService.insereSupervisor(id_cargo, supervisor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(supervisor.getId_supervisor()).toUri();
		return ResponseEntity.created(uri).body(supervisor);
	}
	
	/**
	 * editar os dados de um supervisor 
	 * @param id_supervisor id do supervisor que deve ter seus dados alterados
	 * @param supervisor obj contendo os dados que sobrescreverão os antigos
	 * @return
	 */
	@PutMapping("/supervisor/{id_supervisor}")
	public ResponseEntity<Supervisor> editarSupervisor(@PathVariable Integer id_supervisor, @RequestBody Supervisor supervisor){
		supervisor.setId_supervisor(id_supervisor);	
		supervisor = supervisorService.editarSupervisor(supervisor);
		return ResponseEntity.noContent().build();
	}	
	
	/**
	 * deleta um supervisor do banco de dados
	 * @param id_supervisor id do supervisor que deve ser excluído
	 * @return
	 */
	@DeleteMapping("/supervisor/{id_supervisor}")
	public ResponseEntity<Void> deletarSupervisor(@PathVariable Integer id_supervisor){
		supervisorService.deletarSupervisor(id_supervisor);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * pesquisa e retorna o id do cargo atrelado a um supervisor
	 * @param id_supervisor id do supervisor a ser pesquisado
	 * @return id do cargo que o supervisor está atrelado
	 */
	@GetMapping("/supervisor/checaCargo/{id_supervisor}")
	public ResponseEntity<Integer> checaId(@PathVariable Integer id_supervisor){
		Integer id_cargo = supervisorService.checaID(id_supervisor);
		System.out.print("id_cargo:" + id_cargo);
		return ResponseEntity.ok().body(id_cargo);
	}
	
	/**
	 * retorna o resgitro de todos os supervisores. O registro se refere ao dados do supervisor + dados do cargo que ele superviona
	 * @return obj contendo todos os registros
	 */
	@GetMapping("/supervisor/registros")
	public List<List> supervisorRegistro(){
		List<List> registros = supervisorService.supervisorRegistro();
		return registros;
	}

}
