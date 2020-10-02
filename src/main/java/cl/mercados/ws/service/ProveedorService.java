package cl.mercados.ws.service;

import java.util.List;

import cl.mercados.ws.domain.Proveedor;
import cl.mercados.ws.dto.v10.InputProveedorActualizar;
import cl.mercados.ws.dto.v10.InputProveedorCrear;

public interface ProveedorService {

	Proveedor crear(InputProveedorCrear inputDTO);

	List<Proveedor> consultar(String nombre);

	Proveedor obtener(Long id);
	
	Boolean actualizar(Long id, InputProveedorActualizar inputDTO);

	Boolean eliminar(Long id);

}
