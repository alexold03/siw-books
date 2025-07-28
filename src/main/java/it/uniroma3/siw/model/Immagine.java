package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Immagine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String nomeFile;
	
	private String tipoMime;
	
	@Column(columnDefinition = "bytea",nullable=true)
	private byte[] contenuto;
	
	
	@ManyToOne
	@JoinColumn(name = "libro_id")
	private Libro libro;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNomeFile() {
		return nomeFile;
	}


	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}


	public String getTipoMime() {
		return tipoMime;
	}


	public void setTipoMime(String tipoMime) {
		this.tipoMime = tipoMime;
	}


	public byte[] getContenuto() {
		return contenuto;
	}


	public void setContenuto(byte[] contenuto) {
		this.contenuto = contenuto;
	}


	public Libro getLibro() {
		return libro;
	}


	public void setLibro(Libro libro) {
		this.libro = libro;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id, libro, nomeFile);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Immagine other = (Immagine) obj;
		return Objects.equals(id, other.id) && Objects.equals(libro, other.libro)
				&& Objects.equals(nomeFile, other.nomeFile);
	}
	
	
	
	


}
