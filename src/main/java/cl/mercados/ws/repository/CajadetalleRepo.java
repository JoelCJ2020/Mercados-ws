package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.domain.Cajadetalle;

public interface CajadetalleRepo extends CrudRepository<Cajadetalle, Long> {

	List<Cajadetalle> findAllByCaja(Caja caja);

}
