package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Categoria;
import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.models.Pagamento;
import soulCode.enterprise.repositories.PagamentoRepository;

@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public List<Pagamento> mostraTodosPagamentos(){
		List<Pagamento> pagamentos = this.pagamentoRepository.findAll();
		return pagamentos;
	}
	
	public Pagamento mostraUmPagamento(Integer id_pagamento) {
		Optional<Pagamento> pagamento = pagamentoRepository.findById(id_pagamento);
		return pagamento.orElseThrow();
	}
	
	public List<Pagamento> mostraPagamentosFuncionario(Integer id_funcionario){
		List<Pagamento> pagamentos = pagamentoRepository.mostraPagamentoFunc(id_funcionario);
		return pagamentos;
	}
	
	public Pagamento cadastrarPagamento(Pagamento pagamento, Funcionario funcionario) {
		pagamento.setCodigo(null);
		pagamento.setFuncionario(funcionario);
		return pagamentoRepository.save(pagamento);
	}
	
	public Pagamento editarPagamento(Pagamento pagamento, Integer codigo, Integer id_funcionario) {
		mostraUmPagamento(codigo);
		Funcionario funcionario = funcionarioService.findOne(id_funcionario);
		pagamento.setFuncionario(funcionario);
		return pagamentoRepository.save(pagamento);
	}
	
	public Pagamento efetuarPagamento(Integer codigo) {
		Pagamento pagamento = mostraUmPagamento(codigo);
		Categoria cat = Categoria.DEPOSITADO;
		pagamento.setPa_categoria(cat);
		return pagamentoRepository.save(pagamento);
	}
	
	public Pagamento cancelarPagamento(Integer codigo) {
		Pagamento pagamento = mostraUmPagamento(codigo);
		Categoria cat = Categoria.CANCELADO;
		pagamento.setPa_categoria(cat);
		return pagamentoRepository.save(pagamento);
	}
	
	public void deletarPagamento(Integer codigo) {
		pagamentoRepository.deleteById(codigo);
	}
	
	//retorna o funcionario dono daquele pagamento mediante o fornecimento do codigo do pagamento
	public Integer fetchFuncByCodigo(Integer codigo){
		return pagamentoRepository.fetchFuncByCodigo(codigo);
	}
}
