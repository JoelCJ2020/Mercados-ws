package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Articulo;
import cl.mercados.ws.domain.Producto;
import cl.mercados.ws.dto.v10.InputArticuloActualizar;
import cl.mercados.ws.dto.v10.InputArticuloCrear;
import cl.mercados.ws.repository.ArticuloRepo;
import cl.mercados.ws.service.ArticuloService;
import cl.mercados.ws.service.ProductoService;

@Service
public class ArticuloImpl implements ArticuloService {

	private ArticuloRepo articuloRepo;
	private ProductoService ProductoService;
	
	@Autowired
	public ArticuloImpl(final ArticuloRepo articuloRepo, final ProductoService productoService) {
		this.articuloRepo = articuloRepo;
		this.ProductoService = productoService;
	}
	
	@Override
	public Articulo crear(InputArticuloCrear inputDTO) {
		final Producto producto = ProductoService.obtener(inputDTO.getProductoId());
		final Articulo articulo = new Articulo();
		articulo.setCod(inputDTO.getCod());
		articulo.setNombre(inputDTO.getNombre());
		articulo.setProducto(producto);
		
		return articuloRepo.save(articulo);
	}
	
	@Override
	public List<Articulo> consultar(String cod) {
		if (cod == null) {
			return (List<Articulo>) articuloRepo.findAll();
		}
		return articuloRepo.findAllByCod(cod);
	}
	
	@Override 
	public Articulo obtener(Long id) {
		final Optional<Articulo> _articulo = articuloRepo.findById(id);
		Assert.isTrue(_articulo.isPresent(), "articulo no existe");
		return _articulo.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputArticuloActualizar inputDTO) {
		final Articulo articulo = obtener(id);
		articulo.setNombre(inputDTO.getNombre());
		articuloRepo.save(articulo);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Articulo articulo = obtener(id);
		articuloRepo.delete(articulo);
		return Boolean.TRUE;
	}
}
