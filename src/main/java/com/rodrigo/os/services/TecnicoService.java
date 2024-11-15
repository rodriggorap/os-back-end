package com.rodrigo.os.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.os.domain.Pessoa;
import com.rodrigo.os.domain.Tecnico;
import com.rodrigo.os.dtos.TecnicoDTO;
import com.rodrigo.os.mapper.Mapper;
import com.rodrigo.os.repositories.PessoaRepository;
import com.rodrigo.os.repositories.TecnicoRepository;
import com.rodrigo.os.resources.exceptions.DataIntegratyViolationException;
import com.rodrigo.os.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public TecnicoDTO findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));  
		return Mapper.parseObject(entity, TecnicoDTO.class);
	}
	
	public List<TecnicoDTO> findAll() {
		var entity = repository.findAll();
		return Mapper.parseListObjects(entity, TecnicoDTO.class); 
	}
	
	public TecnicoDTO create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		var entity = Mapper.parseObject(objDTO, Tecnico.class);
		var dto = repository.save(entity);
		return Mapper.parseObject(dto, TecnicoDTO.class);
	}
	
	public TecnicoDTO update(Integer id, @Valid TecnicoDTO objDTO) {
		Optional<Tecnico> obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		entity.setNome(objDTO.getNome());
		entity.setCpf(objDTO.getCpf());
		entity.setTelefone(objDTO.getTelefone());
		var dto = repository.save(entity);
		
		return Mapper.parseObject(dto, TecnicoDTO.class);
	}
	
	public void delete(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		var entity = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
		
		if(entity.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}

	

	
}
