package com.william.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.william.os.domain.Pessoa;
import com.william.os.domain.Cliente;
import com.william.os.dtos.ClienteDTO;
import com.william.os.repositories.PessoaRepository;
import com.william.os.repositories.ClienteRepository;
import com.william.os.services.exceptions.DataIntegrityViolationException;
import com.william.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private PessoaRepository pessoaRepo;

	public Cliente findById(Long id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado!" + "  Id:" + id + "   Tipo:" + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {

		return repo.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		// Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(),
		// objDTO.getTelefone());

		if (findByCPF(objDTO) != null) { // Se for diferente de null, econtrou tecnico com esse cpf
			throw new DataIntegrityViolationException("CPF já cadastrado!");// Precisa manipulador de exceção
		}
		return repo.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Cliente update(Long id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
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
         Cliente obj = findById(id);
         if(obj.getList().size() > 0) {
        	 throw new DataIntegrityViolationException("Cliente possui ordens de serviço associadas!");
         }
         repo.deleteById(id);
	}

	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepo.findByCpf(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}

}
