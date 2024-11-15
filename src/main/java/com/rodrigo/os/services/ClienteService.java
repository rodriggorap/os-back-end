package com.rodrigo.os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.os.domain.Cliente;
import com.rodrigo.os.domain.Pessoa;
import com.rodrigo.os.dtos.ClienteDTO;
import com.rodrigo.os.mapper.Mapper;
import com.rodrigo.os.repositories.ClienteRepository;
import com.rodrigo.os.repositories.PessoaRepository;
import com.rodrigo.os.resources.exceptions.DataIntegratyViolationException;
import com.rodrigo.os.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public ClienteDTO findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));  
		return Mapper.parseObject(entity, ClienteDTO.class);  
	}
	
	public List<ClienteDTO> findAll() {
		var entity = repository.findAll();
		return Mapper.parseListObjects(entity, ClienteDTO.class); 
	}
	
	public ClienteDTO create(ClienteDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!"); 
		}
		var entity = Mapper.parseObject(objDTO, Cliente.class);
		var dto = repository.save(entity);
		return Mapper.parseObject(dto, ClienteDTO.class);
	}
	
	public ClienteDTO update(Integer id, @Valid ClienteDTO objDTO) {
		Optional<Cliente> obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		entity.setNome(objDTO.getNome());
		entity.setCpf(objDTO.getCpf());
		entity.setTelefone(objDTO.getTelefone());
		var dto = repository.save(entity);
		
		return Mapper.parseObject(dto, ClienteDTO.class); 
	}
	
	public void delete(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
		if(entity.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}

	

	
}
