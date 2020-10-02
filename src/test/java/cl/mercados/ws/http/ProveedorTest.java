package cl.mercados.ws.http;

import java.util.List;

import javax.xml.bind.JAXB;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cl.mercados.ws.dto.v10.*;

public class ProveedorTest {
	
	static final String scheme = "http";
	static final int port = 8000;
	static final String host = "localhost";
	static final String path = "/proveedor";
	
	public static void main (String[] args) { 
		ProveedorTest it = new ProveedorTest();
		/*id rut nombre*/
		ProveedorType proveedorType = it.crear("1-2", "mario");
		it.consultar(proveedorType.getNombre());
		it.obtener(proveedorType.getId());
		it.actualizar(proveedorType.getId(), proveedorType.getRut(), "mario2");
		//it.eliminar(proveedorType.getId());
	}
	
	private ProveedorType crear(final String rut, final String nombre) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).build();
		
		InputProveedorCrear inputDTO = new InputProveedorCrear();
		inputDTO.setRut(rut);
		inputDTO.setNombre(nombre);
				
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<InputProveedorCrear> httpentity = new HttpEntity<InputProveedorCrear>(inputDTO);
		ResponseEntity<OutputProveedorCrear> output = restTemplate.exchange(url.toUriString(), HttpMethod.POST, httpentity, OutputProveedorCrear.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
		
		return output.getBody();
	}
	
	private List<ProveedorType> consultar(final String nombre) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).queryParam("nombre", nombre).build();
    	
    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OutputProveedorConsultar> output = restTemplate.exchange(url.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, OutputProveedorConsultar.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
		
		return output.getBody().getRegistro();
	}

	private void obtener(final Long id) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).path("/"+id).build();
		
    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OutputProveedorObtener> output = restTemplate.exchange(url.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, OutputProveedorObtener.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
	}
	
	private void actualizar(final Long id, final String rut, final String nombre) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).path("/"+id).build();
		
		InputProveedorActualizar inputDTO = new InputProveedorActualizar();
		inputDTO.setRut(rut);
		inputDTO.setNombre(nombre);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<InputProveedorActualizar> httpentity = new HttpEntity<InputProveedorActualizar>(inputDTO);
		ResponseEntity<Boolean> output = restTemplate.exchange(url.toUriString(), HttpMethod.PUT, httpentity, Boolean.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
	}
	
	private void eliminar(final Long id) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).path("/"+id).build();

    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> output = restTemplate.exchange(url.toUriString(), HttpMethod.DELETE, HttpEntity.EMPTY, Boolean.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
	}	
}

