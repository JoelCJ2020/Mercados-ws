package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Cliente;
import cl.mercados.ws.domain.Venta;

public interface VentaRepo extends CrudRepository<Venta, Long> {

	List<Venta> findAllByCliente(Cliente cliente);

}
