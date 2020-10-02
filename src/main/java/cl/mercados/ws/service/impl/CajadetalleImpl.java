package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.domain.Cajadetalle;
import cl.mercados.ws.domain.Producto;
import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.dto.v10.InputCajadetalleActualizar;
import cl.mercados.ws.dto.v10.InputCajadetalleCrear;
import cl.mercados.ws.repository.CajadetalleRepo;
import cl.mercados.ws.service.CajaService;
import cl.mercados.ws.service.CajadetalleService;
import cl.mercados.ws.service.ProductoService;

@Service
public class CajadetalleImpl implements CajadetalleService {

	private CajadetalleRepo cajadetalleRepo;
	private CajaService CajaService;
	private ProductoService productoService;
	
	@Autowired
	public CajadetalleImpl(final CajadetalleRepo cajaRepo, final CajaService cajaService, final ProductoService productoService) {
		this.cajadetalleRepo = cajaRepo;
		this.CajaService = cajaService;
		this.productoService = productoService;
	}
	
	@Override
	public Cajadetalle crear(InputCajadetalleCrear inputDTO) {
		final Caja caja = CajaService.obtener(inputDTO.getCajaId());
		final Producto producto = productoService.obtener(inputDTO.getProductoId());
		final Cajadetalle cajadetalle = new Cajadetalle();
		cajadetalle.setCantidad(inputDTO.getCantidad());
		cajadetalle.setCaja(caja);
		cajadetalle.setProducto(producto);
		
		return cajadetalleRepo.save(cajadetalle);
	}
	
	@Override
	public List<Cajadetalle> consultar(Long cajaId) {
		if (cajaId == null) {
			return (List<Cajadetalle>) cajadetalleRepo.findAll();
		}
		Caja caja = CajaService.obtener(cajaId);
		return cajadetalleRepo.findAllByCaja(caja);
	}
	
	@Override 
	public Cajadetalle obtener(Long id) {
		final Optional<Cajadetalle> _cajadetalle = cajadetalleRepo.findById(id);
		Assert.isTrue(_cajadetalle.isPresent(), "cajadetalle no existe");
		return _cajadetalle.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputCajadetalleActualizar inputDTO) {
		final Cajadetalle cajadetalle = obtener(id);
		final Caja caja = CajaService.obtener(inputDTO.getCajaId());
		final Producto producto = productoService.obtener(inputDTO.getProductoId());
		cajadetalleRepo.save(cajadetalle);
		cajadetalle.setCantidad(inputDTO.getCantidad());
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Cajadetalle cajadetalle = obtener(id);
		cajadetalleRepo.delete(cajadetalle);
		return Boolean.TRUE;
	}
}
