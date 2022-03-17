package soulCode.enterprise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import soulCode.enterprise.models.Cargo;
import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.repositories.FuncionarioRepository;
import soulCode.enterprise.services.exceptions.ObjectNotFoundException;

//é a classe de serviços do funcionario, ela guardará as atividades que podem ser realizadas
@Service
public class FuncionarioService {
	
	//injeção de dependencia, ou seja, utilizaremos os métodos da interface FuncionarioRepository (igual ao angular)
	// agora podemos usar os 11 metodos ja prontos do JpaRepository, pq o FuncionarioRepository extende ela 
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CargoService cargoService;
	
	//método para listar todos os registros da tabela funcionarios
	public List<Funcionario> findAll(){
		return funcionarioRepository.findAll();
	}
	
	public List<List> buscaFunComCargo(){
		List<List> funcsCargos = funcionarioRepository.funcionarioComCargo();
		return funcsCargos;
	}
	
	//Optional ajuda a evitar erra de NullPointerException (caso o id não seja encontrado), tira a necessidade de testar com if para 
	//verificar se o id existe
	//orElseThrow - se o aluno estiver presente no banco retorna o aluno senão lança uma exceção
	public Funcionario findOne(Integer Id_funcionario) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(Id_funcionario);
		return funcionario.orElseThrow(() -> new ObjectNotFoundException("Funcionario não cadastrado, id buscado: " + Id_funcionario));
	}
	
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
	 * 
	 */
	public Funcionario cadastraFuncNoCargo(Funcionario funcionario, Integer id_cargo) {
		funcionario.setId_funcionario(null);
		Cargo cargo = cargoService.buscaUmCargo(id_cargo);
		funcionario.setCargo(cargo);
		return funcionarioRepository.save(funcionario);
	}
	
	public Funcionario deixarFuncionarioSemCargo(Integer id_funcionario) {
		Funcionario funcionario = findOne(id_funcionario);
		funcionario.setCargo(null);
		return funcionarioRepository.save(funcionario);
	}
	
	//insere um funcionario sem nenhum cargo
	public Funcionario insereFuncionario(Funcionario funcionario) {
		funcionario.setId_funcionario(null);
		return funcionarioRepository.save(funcionario);
	}
	
	public void deleteFuncionario(Integer Id_funcionario) {
		funcionarioRepository.deleteById(Id_funcionario);
	}
	
	//vai receber o objeto já alterado, daí basta salvar no banco
	//altera a turma caso precise
	public Funcionario editFuncionario(Funcionario funcionario) {
		findOne(funcionario.getId_funcionario());
		Integer id_cargo = funcionarioRepository.cargoFuncionario(funcionario.getId_funcionario());
		if(id_cargo != null) {
			Cargo cargo = cargoService.buscaUmCargo(id_cargo);
			funcionario.setCargo(cargo);
		}
		return funcionarioRepository.save(funcionario);
	}
	

	//eu que fiz essa, traz o cargo baseado no seu id
	public String buscarIdCargo(String id_func) {
		String id_cargo = funcionarioRepository.fetchByIdFunc(id_func);
		return id_cargo;
	}
	
	//desvincula todos os funcionarios de um determinado cargo
	public void desvinculaFuncionarios(Integer id_cargo) {
		funcionarioRepository.desvinculaFuncionarios(id_cargo);
	}
	
	public Funcionario salvarFoto(Integer id_funcionario, String caminhoFoto) {
		Funcionario funcionario = findOne(id_funcionario);
		funcionario.setFunc_foto(caminhoFoto);
		return funcionarioRepository.save(funcionario);
	}
	
	public List<List> funcionarioRegistro(){
		List<List> registroFuncionario = funcionarioRepository.funcionarioRegistro();
		return registroFuncionario;
	}
	

}
