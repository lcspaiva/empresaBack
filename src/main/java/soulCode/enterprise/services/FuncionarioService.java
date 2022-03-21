package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Cargo;
import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.repositories.FuncionarioRepository;
import soulCode.enterprise.services.exceptions.ObjectNotFoundException;


/**
 * é a classe de serviços do funcionario, ela possui as atividades que podem ser realizadas
 * @author lucas
 * @author tatiana
 */
@Service
public class FuncionarioService {
	
	//injeção de dependencia, ou seja, utilizaremos os métodos da interface FuncionarioRepository (igual ao angular)
	// agora podemos usar os 11 metodos ja prontos do JpaRepository, pq o FuncionarioRepository extende ela 
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CargoService cargoService;
	
	
	/**
	 * lista todos os registros da tabela funcionarios
	 * @return list funcionario
	 */
	public List<Funcionario> findAll(){
		return funcionarioRepository.findAll();
	}
	
	/**
	 * lista todos os registros da tabela funcionarios mais as informações dos respectivos cargos
	 * @return lista contendo os funcionarios e seus cargos
	 */
	public List<List> buscaFunComCargo(){
		List<List> funcsCargos = funcionarioRepository.funcionarioComCargo();
		return funcsCargos;
	}
	
	/**
	 * procura os dados de um funcionario pesquisando seu id
	 * @param Id_funcionario id do funcionario pesquisado
	 * @return obj funcionario
	 */
	//Optional ajuda a evitar erra de NullPointerException (caso o id não seja encontrado), tira a necessidade de testar com if para 
	//verificar se o id existe
	//orElseThrow - se o aluno estiver presente no banco retorna o aluno senão lança uma exceção
	public Funcionario findOne(Integer Id_funcionario) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(Id_funcionario);
		return funcionario.orElseThrow(() -> new ObjectNotFoundException("Funcionario não cadastrado, id buscado: " + Id_funcionario));
	}
	
	/**
	 * lista todos os funcionarios associados a um determinado cargo
	 * @param id_cargo id do cargo que está sendo listado
	 * @return lista dos funcionarios daquele cargo
	 */
	public List<Funcionario> buscarFuncsCargo(Integer id_cargo){
		List<Funcionario> funcs = funcionarioRepository.fetchByCargo(id_cargo);
		return funcs;
	}
	
	
	/**
	 * aloca um funcionario existente em um cargo
	 * @param id_funcionario
	 * @param cargo
	 * @return
	 */
	public Funcionario alocaFuncCargo(Integer id_funcionario, Cargo cargo) {
		Funcionario funcionario = findOne(id_funcionario);
		funcionario.setCargo(cargo);
		return funcionarioRepository.save(funcionario);
	}
	
	
	/**
	 * cadastra um novo funcionario e já o aloca em um cargo
	 * @param funcionario dados do funcionario que será cadastrado
	 * @param id_cargo id do seu cargo
	 * @return registro do funcionario recém inserido no banco
	 */
	public Funcionario cadastraFuncNoCargo(Funcionario funcionario, Integer id_cargo) {
		funcionario.setId_funcionario(null);
		Cargo cargo = cargoService.buscaUmCargo(id_cargo);
		funcionario.setCargo(cargo);
		return funcionarioRepository.save(funcionario);
	}
	
	/**
	 * desassocia um funcionario de seu respectivo cargo
	 * @param id_funcionario id do funcionario que deve ser desassociado
	 * @return registro do funcionario desassociado
	 */
	public Funcionario deixarFuncionarioSemCargo(Integer id_funcionario) {
		Funcionario funcionario = findOne(id_funcionario);
		funcionario.setCargo(null);
		return funcionarioRepository.save(funcionario);
	}
	
	/**
	 * insere um funcionario sem nenhum cargo
	 * @param funcionario dados do funcionario inserido no banco
	 * @return registro do funcionario recém inserido no banco
	 */
	public Funcionario insereFuncionario(Funcionario funcionario) {
		funcionario.setId_funcionario(null);
		return funcionarioRepository.save(funcionario);
	}
	
	/**
	 * deleta um funcionario do banco
	 * @param Id_funcionario id do funcionario que deve ser deletado
	 */
	public void deleteFuncionario(Integer Id_funcionario) {
		funcionarioRepository.deleteById(Id_funcionario);
	}
	
	/**
	 * faz a edição dos dados de um funcionario
	 * @param funcionario obj do funcionario contendo dados que deve sobrescrever os antigos
	 * @return
	 */
	public Funcionario editFuncionario(Funcionario funcionario) {
		findOne(funcionario.getId_funcionario());
		Integer id_cargo = funcionarioRepository.cargoFuncionario(funcionario.getId_funcionario());
		if(id_cargo != null) {
			Cargo cargo = cargoService.buscaUmCargo(id_cargo);
			funcionario.setCargo(cargo);
		}
		return funcionarioRepository.save(funcionario);
	}
	

	/**
	 * traz o cargo baseado no id de um funcionario
	 * @param id_func id do funcionario que deve ter seu cargo retornado
	 * @return id do cargo do funcionario
	 */
	public String buscarIdCargo(String id_func) {
		String id_cargo = funcionarioRepository.fetchByIdFunc(id_func);
		return id_cargo;
	}
	
	/**
	 * desvincula todos os funcionarios de um determinado cargo
	 * @param id_cargo id do cargo que deve ser removido dos funcionarios
	 */
	public void desvinculaFuncionarios(Integer id_cargo) {
		funcionarioRepository.desvinculaFuncionarios(id_cargo);
	}
	
	/**
	 * faz o upload da foto
	 * @param id_funcionario id do funcionario que receberá a foto em seu registro
	 * @param caminhoFoto caminho da foto no repositorio
	 * @return registro do funcionario que recebeu a foto
	 */
	public Funcionario salvarFoto(Integer id_funcionario, String caminhoFoto) {
		Funcionario funcionario = findOne(id_funcionario);
		funcionario.setFunc_foto(caminhoFoto);
		return funcionarioRepository.save(funcionario);
	}
	
	/**
	 * lista os dados do funcionario, incluindo a foto e o seu cargo
	 * @return lista de funcionario
	 */
	public List<List> funcionarioRegistro(){
		List<List> registroFuncionario = funcionarioRepository.funcionarioRegistro();
		return registroFuncionario;
	}
	

}
