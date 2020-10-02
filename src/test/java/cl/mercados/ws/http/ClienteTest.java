package cl.mercados.ws.http;

import java.util.List;

import javax.xml.bind.JAXB;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cl.mercados.ws.dto.v10.*;

public class ClienteTest {
	
	static final String scheme = "http";
	static final int port = 8000;
	static final String host = "localhost";
	static final String path = "/cliente";
	
	public static void main (String[] args) { 
		ClienteTest it = new ClienteTest();
		/*id rut nombre*/
		ClienteType clienteType = it.crear("1-1", "luis");
		it.consultar(clienteType.getNombre());
		it.obtener(clienteType.getId());
		it.actualizar(clienteType.getId(), clienteType.getRut(), "luis2");
		it.eliminar(clienteType.getId());
	}
	
	private ClienteType crear(final String rut, final String nombre) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).build();
		
		InputClienteCrear inputDTO = new InputClienteCrear();
		inputDTO.setRut(rut);
		inputDTO.setNombre(nombre);
				
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<InputClienteCrear> httpentity = new HttpEntity<InputClienteCrear>(inputDTO);
		ResponseEntity<OutputClienteCrear> output = restTemplate.exchange(url.toUriString(), HttpMethod.POST, httpentity, OutputClienteCrear.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
		
		return output.getBody();
	}
	
	private List<ClienteType> consultar(final String nombre) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).queryParam("nombre", nombre).build();
    	
    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OutputClienteConsultar> output = restTemplate.exchange(url.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, OutputClienteConsultar.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
		
		return output.getBody().getRegistro();
	}

	private void obtener(final Long id) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).path("/"+id).build();
		
    	RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OutputClienteObtener> output = restTemplate.exchange(url.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, OutputClienteObtener.class);
		
		if (output.getStatusCode().is2xxSuccessful()) {
			JAXB.marshal(output.getBody(), System.out);
		}
	}
	
	private void actualizar(final Long id, final String rut, final String nombre) {
		UriComponentsBuilder url = UriComponentsBuilder.newInstance();
		url.scheme(scheme).host(host).port(port).path(path).path("/"+id).build();
		
		InputClienteActualizar inputDTO = new InputClienteActualizar();
		inputDTO.setRut(rut);
		inputDTO.setNombre(nombre);

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<InputClienteActualizar> httpentity = new HttpEntity<InputClienteActualizar>(inputDTO);
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

