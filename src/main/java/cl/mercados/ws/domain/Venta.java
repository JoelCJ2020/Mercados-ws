package cl.mercados.ws.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Venta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int total;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CLIENTE_ID")
	private Cliente cliente;	
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CAJA_ID")
	private Caja caja;	

}
