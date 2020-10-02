package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.domain.Venta;
import cl.mercados.ws.domain.Cliente;
import cl.mercados.ws.dto.v10.InputVentaActualizar;
import cl.mercados.ws.dto.v10.InputVentaCrear;
import cl.mercados.ws.repository.VentaRepo;
import cl.mercados.ws.service.CajaService;
import cl.mercados.ws.service.ClienteService;
import cl.mercados.ws.service.VentaService;

@Service
public class VentaImpl implements VentaService {

	private VentaRepo ventaRepo;
	private CajaService CajaService;
	private ClienteService ClienteService;
	
	@Autowired
	public VentaImpl(final VentaRepo cajaRepo, final CajaService cajaService, final ClienteService ClienteService) {
		this.ventaRepo = cajaRepo;
		this.CajaService = cajaService;
		this.ClienteService = ClienteService;
	}
	
	@Override
	public Venta crear(InputVentaCrear inputDTO) {
		final Caja caja = CajaService.obtener(inputDTO.getCajaId());
		final Cliente Cliente = ClienteService.obtener(inputDTO.getClienteId());
		final Venta venta = new Venta();
		venta.setTotal(inputDTO.getTotal());
		venta.setCaja(caja);
		venta.setCliente(Cliente);
		
		return ventaRepo.save(venta);
	}
	
	@Override
	public List<Venta> consultar(Long clienteId) {
		if (clienteId == null) {
			return (List<Venta>) ventaRepo.findAll();
		}
		Cliente cliente = ClienteService.obtener(clienteId);
		return ventaRepo.findAllByCliente(cliente);
	}
	
	@Override 
	public Venta obtener(Long id) {
		final Optional<Venta> _venta = ventaRepo.findById(id);
		Assert.isTrue(_venta.isPresent(), "venta no existe");
		return _venta.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputVentaActualizar inputDTO) {
		final Venta venta = obtener(id);
		final Caja caja = CajaService.obtener(inputDTO.getCajaId());
		final Cliente Cliente = ClienteService.obtener(inputDTO.getClienteId());
		ventaRepo.save(venta);
		venta.setTotal(inputDTO.getTotal());
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Venta venta = obtener(id);
		ventaRepo.delete(venta);
		return Boolean.TRUE;
	}
}
