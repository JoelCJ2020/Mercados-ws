package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Articulo;

public interface ArticuloRepo extends CrudRepository<Articulo, Long> {

	List<Articulo> findAllByCod(String cod);

}
