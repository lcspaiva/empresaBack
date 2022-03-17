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

@CrossOrigin
@RestController
@RequestMapping("empresa")
public class CargoController {
	
	@Autowired
	CargoService cargoService;
	
	@GetMapping("/cargo")
	public List<Cargo> mostraTodosCargos(){
		List<Cargo> cargo = cargoService.mostraTodosCargos();
		return cargo;
	}
	
	@GetMapping("/cargo/{id_cargo}")
	public ResponseEntity<?> buscaUmCargo(@PathVariable Integer id_cargo){
		Cargo cargo = cargoService.buscaUmCargo(id_cargo);
		return ResponseEntity.ok().body(cargo);
	}
	@GetMapping("/cargoSemSupervisor")
	public List<Cargo> supervisorSemCargo(){
		List<Cargo> cargos = cargoService.cargoSemSupervisor();
		return cargos;
	}
	
	@GetMapping("/cargo/cargo-supervisor/{id_supervisor}")
	public Cargo cargoDoSupervisor(@PathVariable Integer id_supervisor) {
		return cargoService.cargoDoSupervisor(id_supervisor);
	}
	
	@GetMapping("/cargo/cargo-supervisor")
	public List<List> cargosComSeuSupervisor(){
		List<List> cargos = cargoService.cargoComSeuSupervisor();
		return cargos;
	}
	
	@PostMapping("/cargo")
	public ResponseEntity<Cargo> cadastraCargo(@RequestParam(value="supervisor", required=false)Integer id_supervisor, @RequestBody Cargo cargo){
		cargo = cargoService.cadastrarCargo(id_supervisor, cargo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cargo.getId_cargo()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/cargo/{id_cargo}")
	public ResponseEntity<?> editaCargo(@PathVariable Integer id_cargo, @RequestBody Cargo cargo){
		cargo.setId_cargo(id_cargo);
		cargo = cargoService.editaCargo(cargo);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/cargo/{id_cargo}")
	public ResponseEntity<?> deletarCargo(@PathVariable Integer id_cargo){
		cargoService.deletarCargo(id_cargo);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * faz a vinculação de um supervisor a um cargo 
	 * @param id_cargo
	 * @param id_supervisor
	 * @return
	 */
	@PutMapping("/cargo/definirSupervisor/{id_cargo}/{id_supervisor}")
	public ResponseEntity<Supervisor> atribuirSupervisor(@PathVariable Integer id_cargo, @PathVariable Integer id_supervisor){
		Cargo cargo = cargoService.atribuirSupervisor(id_cargo, id_supervisor);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/cargo/tirarSupervisor/{id_cargo}/{id_supervisor}")
	public ResponseEntity<Supervisor> deixarCargoSemSupervisor(@PathVariable Integer id_cargo, @PathVariable Integer id_supervisor){
		cargoService.deixarCargoSemSupervisor(id_cargo, id_supervisor);
		return ResponseEntity.noContent().build();
	}
}
