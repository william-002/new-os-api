package com.william.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.william.os.domain.Cliente;
import com.william.os.domain.OS;
import com.william.os.domain.Tecnico;
import com.william.os.domain.enums.Prioridade;
import com.william.os.domain.enums.Status;
import com.william.os.dtos.OSDTO;
import com.william.os.repositories.OSRepository;
import com.william.os.services.exceptions.ObjectNotFoundException;

@Service
public class OSService {
	
	@Autowired
	private OSRepository repo;
	
	@Autowired
	private TecnicoService tecService;
	
	@Autowired
	private ClienteService cliService;
	
	public OS findById(Long id) {
		Optional<OS> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado" + "  Id:" + id + "   Tipo:" + OS.class.getName()));
	}
	
	
	public List<OS> findAll(){
		return repo.findAll();
	}


	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}
	
	
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		
		return fromDTO(obj);
	}
	
	
	private  OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade().getCod()));
		newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
		
		Tecnico tec = tecService.findById(obj.getTecnico());
	    Cliente cli = cliService.findById(obj.getCliente());
	    
        newObj.setTecnico(tec);
        newObj.setCliente(cli);
        
        if(newObj.getStatus().getCod().equals(2)) {
        	newObj.setDataFechamento(LocalDateTime.now());
        }
        
        return repo.save(newObj);
	}


	

}
