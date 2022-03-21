package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulCode.enterprise.models.Pagamento;
/**
 * interface que define as funções para efetuar o acesso aos dados presentes no banco de dados
 * @author lucas
 * @author tatiana
 */
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	
	/**
	 * mostra todos os lançamentos efetuados para um funcionario em particular
	 * @param id_funcionario id do funcionario que deve ter seus pagamentos retornados
	 * @return lista contendo todos os pagamentos que foram efetuado no id de um funcionario
	 */
	@Query(value="SELECT * FROM pagamento WHERE id_funcionario = :id_funcionario", nativeQuery=true)
	public List<Pagamento> mostraPagamentoFunc(Integer id_funcionario);
		
	/**
	 * retorna o funcionario dono daquele pagamento mediante o fornecimento do codigo do pagamento
	 * @param codigo id que identifica unicamente um pagamento lançado para um funcionario
	 * @return id do funcionario dono do pagamento
	 */
	@Query(value="SELECT id_funcionario FROM pagamento WHERE codigo = :codigo", nativeQuery=true)
	public Integer fetchFuncByCodigo(Integer codigo);	
}
