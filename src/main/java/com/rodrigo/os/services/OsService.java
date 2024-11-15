package com.rodrigo.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.rodrigo.os.domain.Cliente;
import com.rodrigo.os.domain.OS;
import com.rodrigo.os.domain.Tecnico;
import com.rodrigo.os.domain.enuns.Prioridade;
import com.rodrigo.os.domain.enuns.Status;
import com.rodrigo.os.dtos.OSDTO;
import com.rodrigo.os.mapper.Mapper;
import com.rodrigo.os.repositories.OSRepository;
import com.rodrigo.os.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class OsService {
	
	
	
	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;
	
	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
	}
	
	public List<OS> findAll() {
		return repository.findAll();
	}
	
	public OS create(@Validated OSDTO obj) {
		return fromDTO(obj);
	}
	
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	public void delete(Integer id) {
		Optional<OS> obj = repository.findById(id);
		obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + OS.class.getName()));
		
		repository.deleteById(id);
	}
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObvervacoes(obj.getObvervacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade().getCod())); 
		newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
		
		var tecDto = tecnicoService.findById(obj.getTecnico());
		Tecnico tec = Mapper.parseObject(tecDto, Tecnico.class);
		
		var cliDto = clienteService.findById(obj.getCliente());
		Cliente cli = Mapper.parseObject(cliDto, Cliente.class); 
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return repository.save(newObj);
		
	}	
}
