package com.william.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.william.os.domain.Pessoa;
import com.william.os.domain.Tecnico;
import com.william.os.dtos.TecnicoDTO;
import com.william.os.repositories.PessoaRepository;
import com.william.os.repositories.TecnicoRepository;
import com.william.os.services.exceptions.DataIntegrityViolationException;
import com.william.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repo;
	
	@Autowired
	private PessoaRepository pessoaRepo;

	public Tecnico findById(Long id) {
		Optional<Tecnico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado!" + "  Id:" + id + "   Tipo:" + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {

		return repo.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		// Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(),
		// objDTO.getTelefone());

		if (findByCPF(objDTO) != null) { // Se for diferente de null, econtrou tecnico com esse cpf
			throw new DataIntegrityViolationException("CPF já cadastrado!");// Precisa manipulador de exceção
		}
		return repo.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Tecnico update(Long id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		// validar o cpf e tras o cpf associado a um tecnico caso exista
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegrityViolationException("CPF já cadastrado!");
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());

		return repo.save(oldObj);
	}

	public void delete(Long id) {
         Tecnico obj = findById(id);
         if(obj.getList().size() > 0) {
        	 throw new DataIntegrityViolationException("Técnico possui ordens de serviço associadas!");
         }
         repo.deleteById(id);
	}

	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepo.findByCpf(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
