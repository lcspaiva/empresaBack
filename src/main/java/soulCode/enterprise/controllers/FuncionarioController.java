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
import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.repositories.FuncionarioRepository;
import soulCode.enterprise.services.FuncionarioService;

//define as rotas que teremos para cada serviço, ou seja, qual serviço responderá cada url
//anotações para:
	//indicar que é uma classe de controle
	//remover o erro de CORS
	//indicar que a rota principal será empresa, logo a url seria, localhost:8080/empresa/home etc;
@CrossOrigin
@RestController
@RequestMapping("empresa")
public class FuncionarioController {
	
	//injeção de dependencias para podermos acessar os serviços que responderão à essas rotas
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	
	//anotação para dizer qual o método que chama esse controllador e qual a rota que faz isso
	//ou seja, um get na rota /empresa/funcionarios será tratada por essa função
	@GetMapping("/funcionarios")
	public List<Funcionario> findAll(){
		List<Funcionario> funcionario = funcionarioService.findAll(); 
		return funcionario;
	}
	
	@GetMapping("/funcionario-cargo")
	public List<List> FuncsComCargos(){
		List<List> funcsCargos = funcionarioService.buscaFunComCargo();
		return funcsCargos;
	}
	
	//o response entity retorna os dados reais de um registro do banco de dados. Retorna uma resposta inteira, contendo cabeçalho, status, corpo
	// ?(caractere curinga genérico) -> traz o retorno, mesmo que seja um retorno de sucesso ou erro
	//@PathVariable especifica que o id procurado será passado através da URL
	@GetMapping("/funcionario/{Id_funcionario}")
	public ResponseEntity<?> findOne(@PathVariable Integer Id_funcionario){
		Funcionario funcionario = funcionarioService.findOne(Id_funcionario);
		// se a resposta for ok, manda o funcionario pelo body
		return ResponseEntity.ok().body(funcionario);
	}
	
	@GetMapping("/funcionario/busca-cargo/{id_cargo}")
	public List<Funcionario> buscaFuncsCargo(@PathVariable Integer id_cargo){
		List<Funcionario> funcs = funcionarioService.buscarFuncsCargo(id_cargo);
		return funcs;
	}
	
	//eu que fiz
	@GetMapping("/funcionario/buscar-id-cargo/{id_func}")
	public String buscarIdCargo(@PathVariable String id_func) {
		String id_cargo = funcionarioService.buscarIdCargo(id_func);
		return id_cargo;
	}
	
	@GetMapping("funcionario/registros")
	public List<List> funcionarioRegistro(){
		List<List> funcionarioRegistro = funcionarioService.funcionarioRegistro();
		return funcionarioRegistro;
	}
	
	@PutMapping("/funcionario/inserirCargo/{id_funcionario}")
	public ResponseEntity<Funcionario> alocaFuncCargo(@PathVariable Integer id_funcionario, @RequestBody Cargo cargo) {
		Funcionario funcionario = funcionarioService.alocaFuncCargo(id_funcionario, cargo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/funcionario/deixarSemCargo/{id_funcionario}")
	public ResponseEntity<Funcionario> deixarFuncionarioSemCargo(@PathVariable Integer id_funcionario){
		Funcionario funcionario = funcionarioService.deixarFuncionarioSemCargo(id_funcionario);
		return ResponseEntity.noContent().build();
	}
	

	
	//URI - identificador uniforme de recurso - serve para identificar ou denominar um recurso vindo do servidor
	//URI - une o protocolo HTTP + localização do recurso (url + nome do recurso(URN))
	//identifica quais recursos queremos utilizar
	//ServletUriComponentsBuilder é uma classe que possui metodos para criar ligações com servidor
	
	//estamos querendo fazer uma req para o serv corrente
	//buildAndExpand - cria uma nova instância e coloca os dados que foram passados atrvés do corpo da requisição no modelo que temos
	//como objeto do aluno
//	@PostMapping("/funcionario")
//	public ResponseEntity<Void> insertFuncionario(@RequestBody Funcionario funcionario){
//		funcionarioService.insertFuncionario(funcionario);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id").buildAndExpand(funcionario.getId_funcionario()).toUri();
//		return ResponseEntity.created(uri).build();
//	}
	@PostMapping("/funcionario")
	public ResponseEntity<Funcionario> cadastraFuncionario(@RequestBody Funcionario funcionario){
		funcionario = funcionarioService.insereFuncionario(funcionario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/funcionario/{id}").buildAndExpand(funcionario.getId_funcionario()).toUri();
		return ResponseEntity.created(uri).body(funcionario);
	}
	
	@PostMapping("/funcionario/cargo/{id_cargo}")
	public ResponseEntity<Funcionario> cadastraFuncNoCargo(@RequestBody Funcionario funcionario, @PathVariable Integer id_cargo){
		Funcionario funcionarioRetorno = funcionarioService.cadastraFuncNoCargo(funcionario, id_cargo);
		return ResponseEntity.ok().body(funcionarioRetorno);
	}
	
	@DeleteMapping("/funcionario/{Id_funcionario}")
	public ResponseEntity<Void> deleteFuncionario(@PathVariable Integer Id_funcionario){
		funcionarioService.deleteFuncionario(Id_funcionario);
		return ResponseEntity.noContent().build(); //indica que não terá retorno
	}
	
	/*
	 //esse put não permite que a alteração do cargo do funcionario
	@PutMapping("/funcionario/{Id_funcionario}")
	public ResponseEntity<Void> editFuncionario(@PathVariable Integer Id_funcionario, @RequestBody Funcionario funcionario){
		funcionario.setId_funcionario(Id_funcionario);
		funcionario = funcionarioService.editFuncionario(funcionario);
		return ResponseEntity.noContent().build();
	}
	*/
	
	//faz a alteração da turma caso precise
	@PutMapping("/funcionario/{id_funcionario}")
	public ResponseEntity<Void> editarFuncionario(@PathVariable Integer id_funcionario, @RequestBody Funcionario funcionario){
		funcionario.setId_funcionario(id_funcionario);	
		funcionario = funcionarioService.editFuncionario(funcionario);
		return ResponseEntity.noContent().build();
	}
	
//	@PostMapping("/teste")
//	public ResponseEntity<Funcionario> cadastraFuncionarioRetorno(@RequestBody Funcionario funcionario){
//		funcionario = funcionarioService.insereFuncionario(funcionario);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/funcionario/{id}").buildAndExpand(funcionario.getId_funcionario()).toUri();
//		return ResponseEntity.created(uri).body(funcionario);
//	}
	

}
