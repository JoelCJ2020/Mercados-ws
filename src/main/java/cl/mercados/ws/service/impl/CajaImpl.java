package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.domain.Cliente;
import cl.mercados.ws.dto.v10.InputCajaActualizar;
import cl.mercados.ws.dto.v10.InputCajaCrear;
import cl.mercados.ws.repository.CajaRepo;
import cl.mercados.ws.service.CajaService;
import cl.mercados.ws.service.ClienteService;

@Service
public class CajaImpl implements CajaService {

	private CajaRepo cajaRepo;
	private ClienteService ClienteService;
	
	@Autowired
	public CajaImpl(final CajaRepo cajaRepo, final ClienteService clienteService) {
		this.cajaRepo = cajaRepo;
		this.ClienteService = clienteService;
	}
	
	@Override
	public Caja crear(InputCajaCrear inputDTO) {
		final Cliente cliente = ClienteService.obtener(inputDTO.getClienteId());
		final Caja caja = new Caja();
		caja.setCliente(cliente);
		
		return cajaRepo.save(caja);
	}
	
	@Override
	public List<Caja> consultar(Long clienteId) {
		if (clienteId == null) {
			return (List<Caja>) cajaRepo.findAll();
		}
		Cliente cliente = ClienteService.obtener(clienteId);
		return cajaRepo.findAllByCliente(cliente);
	}
	
	@Override 
	public Caja obtener(Long id) {
		final Optional<Caja> _caja = cajaRepo.findById(id);
		Assert.isTrue(_caja.isPresent(), "caja no existe");
		return _caja.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputCajaActualizar inputDTO) {
		final Caja caja = obtener(id);
		cajaRepo.save(caja);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Caja caja = obtener(id);
		cajaRepo.delete(caja);
		return Boolean.TRUE;
	}
}
