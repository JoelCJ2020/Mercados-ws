package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Cliente;
import cl.mercados.ws.dto.v10.InputClienteActualizar;
import cl.mercados.ws.dto.v10.InputClienteCrear;
import cl.mercados.ws.repository.ClienteRepo;
import cl.mercados.ws.service.ClienteService;

@Service
public class ClienteImpl implements ClienteService {

	private ClienteRepo clienteRepo;
	
	@Autowired
	public ClienteImpl (final ClienteRepo clienteRepo) {
		this.clienteRepo = clienteRepo;
	}
	
	@Override
	public Cliente crear(InputClienteCrear inputDTO) {
		final Cliente cliente = new Cliente();
		cliente.setNombre(inputDTO.getNombre());
		cliente.setRut(inputDTO.getRut());
		
		return clienteRepo.save(cliente);
	}
	
	@Override
	public List<Cliente> consultar(String nombre) {
		if (nombre == null) {
			return (List<Cliente>) clienteRepo.findAll();
		}
		return clienteRepo.findAllByNombre(nombre);
	}
	
	@Override 
	public Cliente obtener(Long id) {
		final Optional<Cliente> _cliente = clienteRepo.findById(id);
		Assert.isTrue(_cliente.isPresent(), "cliente no existe");
		return _cliente.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputClienteActualizar inputDTO) {
		final Cliente cliente = obtener(id);
		cliente.setNombre(inputDTO.getNombre());
		clienteRepo.save(cliente);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Cliente cliente = obtener(id);
		clienteRepo.delete(cliente);
		return Boolean.TRUE;
	}
}
