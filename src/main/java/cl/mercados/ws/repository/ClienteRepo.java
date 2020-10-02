package cl.mercados.ws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.mercados.ws.domain.Cliente;

public interface ClienteRepo extends CrudRepository<Cliente, Long> {

	List<Cliente> findAllByNombre(String nombre);

}
