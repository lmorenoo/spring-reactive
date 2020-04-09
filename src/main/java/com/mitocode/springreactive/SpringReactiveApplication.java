package com.mitocode.springreactive;

import com.mitocode.springreactive.document.Rol;
import com.mitocode.springreactive.document.Usuario;
import com.mitocode.springreactive.repo.IRolRepo;
import com.mitocode.springreactive.repo.IUsuarioRepo;
import com.mitocode.springreactive.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringReactiveApplication implements  CommandLineRunner{

	@Autowired
	IRolRepo rolrepo;

	@Autowired
	IUsuarioRepo iUsuarioRepo;

	@Value("${springreactive.usuario}")
	private String usuario;

	@Value("${springreactive.pass}")
	private String pass;

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String salt = BCrypt.gensalt(12);
		String hashed_password = BCrypt.hashpw(pass, salt);

		rolrepo.deleteAll().subscribe();
		iUsuarioRepo.deleteAll().subscribe();
		Rol rol = new Rol("ADMIN", "Administrador");
		rolrepo.save(rol).subscribe();
		Usuario user = new Usuario();
		user.setId("001");
		user.setUsuario(usuario);
		user.setClave(hashed_password);
		user.setRoles(Arrays.asList(rol));
		iUsuarioRepo.save(user).subscribe();
	}
}
