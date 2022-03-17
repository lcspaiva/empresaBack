package soulCode.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import soulCode.enterprise.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{
	
	@Query(value="SELECT * FROM pagamento WHERE id_funcionario = :id_funcionario", nativeQuery=true)
	public List<Pagamento> mostraPagamentoFunc(Integer id_funcionario);
		
	//retorna o funcionario dono daquele pagamento mediante o fornecimento do codigo do pagamento
	@Query(value="SELECT id_funcionario FROM pagamento WHERE codigo = :codigo", nativeQuery=true)
	public Integer fetchFuncByCodigo(Integer codigo);	
}
