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
import soulCode.enterprise.services.CargoService;
/**
 * classe de controle das funções do cargo 
 * @author tatiana
 * @author lucas
 *
 */
@CrossOrigin
@RestController
@RequestMapping("empresa")
public class CargoController {
	
	@Autowired
	CargoService cargoService;
	
	/**
	 * rota para mostrar todos os cargos cadastrados no banco de dados
	 * @return
	 */
	@GetMapping("/cargo")
	public List<Cargo> mostraTodosCargos(){
		List<Cargo> cargo = cargoService.mostraTodosCargos();
		return cargo;
	}
	
	/** 
	 * rota para buscar um cargo pelo is e exbir os seus dados
	 * @param id_cargo id do cargo que deve ser procurado
	 * @return
	 */
	@GetMapping("/cargo/{id_cargo}")
	public ResponseEntity<?> buscaUmCargo(@PathVariable Integer id_cargo){
		Cargo cargo = cargoService.buscaUmCargo(id_cargo);
		return ResponseEntity.ok().body(cargo);
	}
	

	/**
	 * rota para exibir todos os cargos que não possuem um supervisor vinculado
	 * @return lista contendo todos os registros dos supervisores encontrados que atendem a retrição de não possuírem cargo
	 */
	@GetMapping("/cargoSemSupervisor")
	public List<Cargo> supervisorSemCargo(){
		List<Cargo> cargos = cargoService.cargoSemSupervisor();
		return cargos;
	}
	
	
	/**
	 * rota par exibir qual o cargo vinculado a um supervisor
	 * @param id_supervisor ql o id que deve ser procurado
	 * @return o objeto do cargo que está vinculado ao supervisor
	 */
	@GetMapping("/cargo/cargo-supervisor/{id_supervisor}")
	public Cargo cargoDoSupervisor(@PathVariable Integer id_supervisor) {
		return cargoService.cargoDoSupervisor(id_supervisor);
	}
	
	
	/**
	 * rota para exibir todos os cargos e seus respectivos supervisores vinculados
	 * @return uma lista contendo todos os cargos e seus respectivos supervisores 
	 */
	@GetMapping("/cargo/cargo-supervisor")
	public List<List> cargosComSeuSupervisor(){
		List<List> cargos = cargoService.cargoComSeuSupervisor();
		return cargos;
	}
	
	/**
	 * rota para cadastrar um novo cargo no banco de dados, sendo que pode ser um cadastro que já venha com um supervisor para fazer a vinculação
	 * @param id_supervisor (opcinal) indica se o cargo já possuirá um supervisor no momento da sua criação
	 * @param cargo obj contendo as informações do cargo que será adcionado ao banco
	 * @return
	 */
	@PostMapping("/cargo")
	public ResponseEntity<Cargo> cadastraCargo(@RequestParam(value="supervisor", required=false)Integer id_supervisor, @RequestBody Cargo cargo){
		cargo = cargoService.cadastrarCargo(id_supervisor, cargo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargo.getId_cargo()).toUri();
		return ResponseEntity.created(uri).build();
	}
	/**
	 * rota para editar os dados de um cargo que já está cadastrado no banco de dados 
	 * @param id_cargo id do cargo que deve ser editado
	 * @param cargo obj contendo as informações que devem ser salvas, sobrescrevendo os dados antigos
	 * @return
	 */
	@PutMapping("/cargo/{id_cargo}")
	public ResponseEntity<?> editaCargo(@PathVariable Integer id_cargo, @RequestBody Cargo cargo){
		cargo.setId_cargo(id_cargo);
		cargo = cargoService.editaCargo(cargo);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * função para deletar um cargo da base de dados
	 * @param id_cargo id do cargo que deve ser deletado
	 * @return nenhum
	 */
	@DeleteMapping("/cargo/{id_cargo}")
	public ResponseEntity<?> deletarCargo(@PathVariable Integer id_cargo){
		cargoService.deletarCargo(id_cargo);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * faz a vinculação de um supervisor a um cargo 
	 * @param id_cargo id do cargo que receberá o supervisor
	 * @param id_supervisor id do supervisor que supervisionará o cargo a partir de então
	 * @return nenhum
	 */
	@PutMapping("/cargo/definirSupervisor/{id_cargo}/{id_supervisor}")
	public ResponseEntity<Supervisor> atribuirSupervisor(@PathVariable Integer id_cargo, @PathVariable Integer id_supervisor){
		Cargo cargo = cargoService.atribuirSupervisor(id_cargo, id_supervisor);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * faz a desassociação entre supervisor e o cargo
	 * @param id_cargo id do cargo que terá o supervisor retirado
	 * @param id_supervisor id do supervisor que será liberado
	 * @return nenhum
	 */
	@PutMapping("/cargo/tirarSupervisor/{id_cargo}/{id_supervisor}")
	public ResponseEntity<Supervisor> deixarCargoSemSupervisor(@PathVariable Integer id_cargo, @PathVariable Integer id_supervisor){
		cargoService.deixarCargoSemSupervisor(id_cargo, id_supervisor);
		return ResponseEntity.noContent().build();
	}
}
