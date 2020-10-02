package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Proveedor;

public interface ProveedorRepo extends CrudRepository<Proveedor, Long> {

	List<Proveedor> findAllByNombre(String nombre);

}
