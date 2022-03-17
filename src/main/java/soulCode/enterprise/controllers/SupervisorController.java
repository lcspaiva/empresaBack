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
public class SupervisorController {
	
	@Autowired
	private SupervisorService supervisorService;
	
	@GetMapping("/supervisor")
	public List<Supervisor> mostraTodosSupervisores() {
		List<Supervisor> supervisores = supervisorService.mostraTodosSupervisores();
		return supervisores;
	}
	
	@GetMapping("/supervisor/{id_supervisor}")
	public Supervisor buscarSupervisor(@PathVariable Integer id_supervisor) {
		Supervisor supervisor = supervisorService.buscarSupervisor(id_supervisor);
		return supervisor;
	}
	
	@GetMapping("/supervisor-cargo/{id_cargo}")
	public ResponseEntity<Supervisor> buscarSupervisorCargo(@PathVariable Integer id_cargo){
		Supervisor supervisor = supervisorService.buscarSupervisorCargo(id_cargo);
		return ResponseEntity.ok().body(supervisor);
	}
	
	@GetMapping("/supervisor-livres")
	public List<Supervisor> supervisoresLivres(){
		List<Supervisor> supervisoresLivres = supervisorService.buscarSupervisoresLivres();
		return supervisoresLivres; 
	}
	
	
	//traz o join do supervisor com o cargo
	@GetMapping("/supervisor/supervisor-cargo")
	public List<List> supervisorComCargo(){
		List<List> supervisores = supervisorService.buscarSupervisorComSeuCargo();
		return supervisores;
	}
	
	@GetMapping("/supervisor/buscarSupervisorByNome/{su_nome}")
	public Supervisor buscarSupervisorByName(@PathVariable String su_nome) {
		Supervisor supervisor = supervisorService.buscarSupervisorByNome(su_nome);
		return supervisor;
	}
	
	/*
	 *
	 // essas funções estão comentadas uma vez que elas serão unidas em uma só função, que detectará (no service) se deve ser feita uma inserção com turma ou se deve ser feita uma sem turma
	@PostMapping("/supervisorSemCargo")
	public ResponseEntity<Supervisor> inserirSupervisorSemCargo(@RequestBody Supervisor supervisor){
		supervisor = supervisorService.insereSupervisorSemCargo(supervisor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(supervisor.getId_supervisor()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/supervisorComCargo")
	public ResponseEntity<Supervisor> inserirSupervisorComCargo(@RequestParam(value="cargo")Integer id_cargo, @RequestBody Supervisor supervisor){
		supervisor = supervisorService.insereSupervisorComCargo(id_cargo, supervisor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(supervisor.getId_supervisor()).toUri();
		return ResponseEntity.created(uri).build();
	}
	*/
	
	@PostMapping("/supervisor")
	public ResponseEntity<Supervisor> insereSupervisorComCargo(@RequestParam(name="cargo", required = false) Integer id_cargo, @RequestBody Supervisor supervisor){
		supervisor = supervisorService.insereSupervisor(id_cargo, supervisor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(supervisor.getId_supervisor()).toUri();
		return ResponseEntity.created(uri).body(supervisor);
	}
	//@RequestParam(value="cargo")Cargo cargo, 
	@PutMapping("/supervisor/{id_supervisor}")
	public ResponseEntity<Supervisor> editarSupervisor(@PathVariable Integer id_supervisor, @RequestBody Supervisor supervisor){
		supervisor.setId_supervisor(id_supervisor);
		//supervisor.setCargo(cargo);
		//cargo.setSupervisor(supervisor);		
		supervisor = supervisorService.editarSupervisor(supervisor);
		return ResponseEntity.noContent().build();
	}	
	
	@DeleteMapping("/supervisor/{id_supervisor}")
	public ResponseEntity<Void> deletarSupervisor(@PathVariable Integer id_supervisor){
		supervisorService.deletarSupervisor(id_supervisor);
		return ResponseEntity.noContent().build();
	}
	
	//retorna o id do cargo atrelado a um supervisor
	@GetMapping("/supervisor/checaCargo/{id_supervisor}")
	public ResponseEntity<Integer> checaId(@PathVariable Integer id_supervisor){
		Integer id_cargo = supervisorService.checaID(id_supervisor);
		System.out.print("id_cargo:" + id_cargo);
		return ResponseEntity.ok().body(id_cargo);
	}
	
	@GetMapping("/supervisor/registros")
	public List<List> supervisorRegistro(){
		List<List> registros = supervisorService.supervisorRegistro();
		return registros;
	}

}
