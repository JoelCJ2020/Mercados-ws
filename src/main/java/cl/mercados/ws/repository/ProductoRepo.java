package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Producto;

public interface ProductoRepo extends CrudRepository<Producto, Long> {

	List<Producto> findAllByCod(String cod);

}
