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

import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.models.Pagamento;
import soulCode.enterprise.services.FuncionarioService;
import soulCode.enterprise.services.PagamentoService;

@RestController
@CrossOrigin
@RequestMapping("empresa")
public class PagamentoController {
	
	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@GetMapping("/pagamento")
	public List<Pagamento> mostraTodosPagamentos(){
		List<Pagamento> pagamentos = pagamentoService.mostraTodosPagamentos();
		return pagamentos;
	}
	
	@GetMapping("/pagamento/{id_pagamento}")
	public ResponseEntity<Pagamento> mostraUmPagamento(@PathVariable Integer id_pagamento){
		Pagamento pagamento = pagamentoService.mostraUmPagamento(id_pagamento);
		return ResponseEntity.ok().body(pagamento);
	}
	
	@GetMapping("/pagamento/funcionario/{id_funcionario}")
	public List<Pagamento> mostraPagamentosFuncionario(@PathVariable Integer id_funcionario){
		List<Pagamento> pagamentos = pagamentoService.mostraPagamentosFuncionario(id_funcionario);
		return pagamentos;
	}
	
	//retorna o id do funcionario dono daquele pagamento
	@GetMapping("/pagamento/fetchFuncionario/{codigo}")
	public Integer fetchFuncByCodigo(@PathVariable Integer codigo) {
		return pagamentoService.fetchFuncByCodigo(codigo);
	}
	
	@PostMapping("/pagamento/cadastrarPagamento/{id_funcionario}")
	public ResponseEntity<Void> cadastrarPagamento(@RequestBody Pagamento pagamento, @PathVariable Integer id_funcionario){
		Funcionario funcionario = funcionarioService.findOne(id_funcionario);
		Pagamento pagamentoLancado = pagamentoService.cadastrarPagamento(pagamento, funcionario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(pagamentoLancado.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/pagamento/editarPagamento/{codigo}/{id_funcionario}")
	public ResponseEntity<Pagamento> editarPagamento(@RequestBody Pagamento pagamento, @PathVariable Integer codigo, @PathVariable Integer id_funcionario){
		pagamentoService.editarPagamento(pagamento, codigo, id_funcionario);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/pagamento/efetuarPagamento/{codigo}")
	public ResponseEntity<Pagamento> efetuarPagamento(@PathVariable Integer codigo){
		pagamentoService.efetuarPagamento(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/pagamento/cancelarPagamento/{codigo}")
	public ResponseEntity<Pagamento> cancelarPagamento(@PathVariable Integer codigo){
		pagamentoService.cancelarPagamento(codigo);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/pagamento/deletarPagamento/{id_pagamento}")
	public ResponseEntity<Void> deletarPagamento(@PathVariable Integer id_pagamento){
		pagamentoService.deletarPagamento(id_pagamento);
		return ResponseEntity.noContent().build();
	}
	
	
}
