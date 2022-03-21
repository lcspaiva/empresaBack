package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Categoria;
import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.models.Pagamento;
import soulCode.enterprise.repositories.PagamentoRepository;

/**
 * classe de serviço referente as operações com os pagamentos
 * @author lucas
 * @author tatiana
 */
@Service
public class PagamentoService {
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	/**
	 * lista todos os pagamentos existentes na tabela
	 * @return list de pagamento
	 */
	public List<Pagamento> mostraTodosPagamentos(){
		List<Pagamento> pagamentos = this.pagamentoRepository.findAll();
		return pagamentos;
	}
	
	/**
	 * exibe as informações de um pagamento por meio da pesquisa de seu id
	 * @param id_pagamento id do pagamento a ser procurado
	 * @return obj pagamento
	 */
	public Pagamento mostraUmPagamento(Integer id_pagamento) {
		Optional<Pagamento> pagamento = pagamentoRepository.findById(id_pagamento);
		return pagamento.orElseThrow();
	}
	
	/**
	 * lista todos os pagamentos que foram efetuados para um id de funcionario
	 * @param id_funcionario id do funcionario que deve ter seus lançamentos listados
	 * @return list de pagamentos
	 */
	public List<Pagamento> mostraPagamentosFuncionario(Integer id_funcionario){
		List<Pagamento> pagamentos = pagamentoRepository.mostraPagamentoFunc(id_funcionario);
		return pagamentos;
	}
	
	/**
	 * faz a inserção de um pagamento sob um id de funcionaro
	 * @param pagamento dados do pagamento que serão inseridos no banco de dados
	 * @param funcionario obj do funcionario que receberá o pagamento
	 * @return obj do pagamento que foi inserido no banco
	 */
	public Pagamento cadastrarPagamento(Pagamento pagamento, Funcionario funcionario) {
		pagamento.setCodigo(null);
		pagamento.setFuncionario(funcionario);
		return pagamentoRepository.save(pagamento);
	}
	
	/**
	 * faz a edição dos dados de um pagamento
	 * @param pagamento obj pagamento contendo os dados que devem sobrescrever os antigos
	 * @param codigo identificado do pagamento que deverá ser editado
	 * @param id_funcionario id do funcionario que recebeu esse pagamento
	 * @return dados do pagamento que foi editado
	 */
	public Pagamento editarPagamento(Pagamento pagamento, Integer codigo, Integer id_funcionario) {
		mostraUmPagamento(codigo);
		Funcionario funcionario = funcionarioService.findOne(id_funcionario);
		pagamento.setFuncionario(funcionario);
		return pagamentoRepository.save(pagamento);
	}
	
	/**
	 * faz a alteração do status do pagamento
	 * @param codigo id do pagamento
	 * @return obj pagamento que foi atualizado
	 */
	public Pagamento efetuarPagamento(Integer codigo) {
		Pagamento pagamento = mostraUmPagamento(codigo);
		Categoria cat = Categoria.DEPOSITADO;
		pagamento.setPa_categoria(cat);
		return pagamentoRepository.save(pagamento);
	}
	
	/**
	 * faz a alteração do status do pagamento
	 * @param codigo id do pagamento
	 * @return obj pagamento que foi atualizado
	 */
	public Pagamento cancelarPagamento(Integer codigo) {
		Pagamento pagamento = mostraUmPagamento(codigo);
		Categoria cat = Categoria.CANCELADO;
		pagamento.setPa_categoria(cat);
		return pagamentoRepository.save(pagamento);
	}
	
	/**
	 * faz a deleção de um pagamento do banco de dados
	 * @param codigo identificador do pagamento que deverá ser deletado
	 */
	public void deletarPagamento(Integer codigo) {
		pagamentoRepository.deleteById(codigo);
	}
	
	/**
	 * retorna o funcionario dono daquele pagamento mediante o fornecimento do codigo do pagamento
	 * @param codigo codigo do pagamento
	 * @return obj funcionario
	 */
	public Integer fetchFuncByCodigo(Integer codigo){
		return pagamentoRepository.fetchFuncByCodigo(codigo);
	}
}
