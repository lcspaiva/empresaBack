package soulCode.enterprise.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtil {
	 
	//função que fará o salvamento do arquivo, é meio que o service
	public static void salvarArquivo(String uploadDir, String fileName, MultipartFile file) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		//testando se o caminho existe, caso não, cria ele
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		//pegando os dados enviados, no caso a foto.
		// o envio é uma stream de dados
		try(InputStream inputStream = file.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException e) {
			throw new IOException("Não foi possivel enviar seu arquivo" + fileName, e);
		}
	}
}
