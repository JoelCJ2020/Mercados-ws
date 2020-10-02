package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Caja;
import cl.mercados.ws.domain.Cliente;

public interface CajaRepo extends CrudRepository<Caja, Long> {

	List<Caja> findAllByCliente(Cliente cliente);

}
