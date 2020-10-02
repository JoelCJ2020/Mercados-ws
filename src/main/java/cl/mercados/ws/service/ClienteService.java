package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Cliente;
import cl.mercados.ws.dto.v10.InputClienteActualizar;
import cl.mercados.ws.dto.v10.InputClienteCrear;

public interface ClienteService {

	Cliente crear(InputClienteCrear inputDTO);

	List<Cliente> consultar(String nombre);

	Cliente obtener(Long id);
	
	Boolean actualizar(Long id, InputClienteActualizar inputDTO);

	Boolean eliminar(Long id);

}
