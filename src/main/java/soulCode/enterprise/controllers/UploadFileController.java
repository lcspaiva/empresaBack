package soulCode.enterprise.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import soulCode.enterprise.models.Funcionario;
import soulCode.enterprise.models.Supervisor;
import soulCode.enterprise.services.FuncionarioService;
import soulCode.enterprise.services.SupervisorService;
import soulCode.enterprise.util.UploadFileUtil;

@RestController
@RequestMapping("empresa")
@CrossOrigin
/**
 * classe para fazer o upload do arquivo referente a foto
 * @author lucas
 * @author tatiana
 * 
 */
public class UploadFileController {
	
	@Autowired
	private SupervisorService supervisorService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	/**
	 * faz o upload da foto em uma pasta predeterminada, mediante as opçoes faz o upload de uma foto para um supervisor ou funcionario
	 * @param id_destino id do registro que receberá a foto
	 * @param dest flag que determina o tipo de registro que está recebendo a foto, 1-supervisor 2-funcionario
	 * @param foto arquivo referente a foto
	 * @param nome nome para o arquivo
	 * @return
	 */
	@PostMapping("/envio/{id_destino}/{dest}")
	public ResponseEntity<String> enviarDados(@PathVariable Integer id_destino, @PathVariable Integer dest, MultipartFile foto, @RequestParam(value="nome") String nome){
		String fileName = nome;
		String uploadDir = "D:\\Estudos\\SoulCode\\Extensão Java\\empresaFront\\src\\assets\\fotos";
		String nomeMaisCaminho = "/assets/fotos/" + nome;
		
		//ID 1 = a tabela de destino é a supervisor
		//ID 2 = a tabela de destino é a funcionario 
		
		System.out.println("ID: " + id_destino);
		System.out.println("Dest: " + dest);
		
		
		if(dest == 1) {
			supervisorService.buscarSupervisor(id_destino);
			Supervisor supervisor = supervisorService.salvarFoto(id_destino, nomeMaisCaminho);
		}else{
			funcionarioService.findOne(id_destino);
			Funcionario funcionario = funcionarioService.salvarFoto(id_destino, nomeMaisCaminho);
		}
		/**/	
		try {
			//chamando o "serviço"
			UploadFileUtil.salvarArquivo(uploadDir, fileName, foto);
		}catch(Exception e){
			System.out.println("O arquivo nao pode ser enviado" + e);
		}
		System.out.println("O envio de arquivo foi bem sucedido");
		return ResponseEntity.ok("Arquivo enviado");
	}
}
