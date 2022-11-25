package com.william.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.william.os.domain.Cliente;
import com.william.os.domain.OS;
import com.william.os.domain.Tecnico;
import com.william.os.domain.enums.Prioridade;
import com.william.os.domain.enums.Status;
import com.william.os.repositories.ClienteRepository;
import com.william.os.repositories.OSRepository;
import com.william.os.repositories.TecnicoRepository;

@Service  //Permite injetar instacias da classe no código com responsabilidade do spring
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	
	
	public void instanciaDB() {
		
		Tecnico t1 = new Tecnico(null, "Zabo", "141.517.950-65", "(81)99999-8888");
		Tecnico t2 = new Tecnico(null, "Xerolayne", "453.335.430-03", "(81)96666-8888");
		Cliente c1 = new Cliente(null, "Rebeco", "357.168.190-87", "(81)97777-8888");
		OS os1 = new OS(null, Prioridade.ALTA, "teste", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
		
		//colocar dependencia h2 embaixo de mysql no pom
		
	}

}
