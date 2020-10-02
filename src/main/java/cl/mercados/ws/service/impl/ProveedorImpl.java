package cl.mercados.ws.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import cl.mercados.ws.domain.Proveedor;
import cl.mercados.ws.dto.v10.InputProveedorActualizar;
import cl.mercados.ws.dto.v10.InputProveedorCrear;
import cl.mercados.ws.repository.ProveedorRepo;
import cl.mercados.ws.service.ProveedorService;

@Service
public class ProveedorImpl implements ProveedorService {

	private ProveedorRepo proveedorRepo;
	
	@Autowired
	public ProveedorImpl (final ProveedorRepo proveedorRepo) {
		this.proveedorRepo = proveedorRepo;
	}
	
	@Override
	public Proveedor crear(InputProveedorCrear inputDTO) {
		final Proveedor proveedor = new Proveedor();
		proveedor.setNombre(inputDTO.getNombre());
		proveedor.setRut(inputDTO.getRut());
		
		return proveedorRepo.save(proveedor);
	}
	
	@Override
	public List<Proveedor> consultar(String nombre) {
		if (nombre == null) {
			return (List<Proveedor>) proveedorRepo.findAll();
		}
		return proveedorRepo.findAllByNombre(nombre);
	}
	
	@Override 
	public Proveedor obtener(Long id) {
		final Optional<Proveedor> _proveedor = proveedorRepo.findById(id);
		Assert.isTrue(_proveedor.isPresent(), "proveedor no existe");
		return _proveedor.get();
	}
	
	@Override
	public Boolean actualizar(Long id, InputProveedorActualizar inputDTO) {
		final Proveedor proveedor = obtener(id);
		proveedor.setNombre(inputDTO.getNombre());
		proveedorRepo.save(proveedor);
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean eliminar(Long id) {
		final Proveedor proveedor = obtener(id);
		proveedorRepo.delete(proveedor);
		return Boolean.TRUE;
	}
}
