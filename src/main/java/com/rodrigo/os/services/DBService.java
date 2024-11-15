package com.rodrigo.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigo.os.domain.Cliente;
import com.rodrigo.os.domain.OS;
import com.rodrigo.os.domain.Tecnico;
import com.rodrigo.os.domain.enuns.Prioridade;
import com.rodrigo.os.domain.enuns.Status;
import com.rodrigo.os.repositories.ClienteRepository;
import com.rodrigo.os.repositories.OSRepository;
import com.rodrigo.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Rodrigo Amaro", "144.785.300-84", "(88) 98888-8881");
		Tecnico t2 = new Tecnico(null, "Marcos Amaro", "829.478.390-05", "(88) 98888-8882");
		Cliente c1 = new Cliente(null, "Rodrigo Amaro", "598.508.200-80", "(88) 98888-7777");
		Tecnico t3 = new Tecnico(null, "Rodrigo Amaro", "759.262.390-01", "(88) 98888-8881");
		Tecnico t4 = new Tecnico(null, "Marcos Amaro", "195.275.120-93", "(88) 98888-8882");
		Cliente c2 = new Cliente(null, "Rodrigo Amaro", "793.144.250-45", "(88) 98888-7777");
		Tecnico t5 = new Tecnico(null, "Rodrigo Amaro", "623.372.810-00", "(88) 98888-8881");
		Tecnico t6 = new Tecnico(null, "Marcos Amaro", "150.761.390-39", "(88) 98888-8882");
		Cliente c3 = new Cliente(null, "Rodrigo Amaro", "150.761.390-39", "(88) 98888-7777");
		Tecnico t7 = new Tecnico(null, "Rodrigo Amaro", "589.963.630-06", "(88) 98888-8881");
		Tecnico t8 = new Tecnico(null, "Marcos Amaro", "838.503.440-45", "(88) 98888-8882");
		Cliente c4 = new Cliente(null, "Rodrigo Amaro", "069.469.520-30", "(88) 98888-7777");
	
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OD", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8));
		clienteRepository.saveAll(Arrays.asList(c1, c2, c3, c4)); 
		osRepository.saveAll(Arrays.asList(os1));

	}

}
