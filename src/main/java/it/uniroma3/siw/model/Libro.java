package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
public class Libro {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	private String titolo;

	@NotNull
	@PastOrPresent
	private LocalDate annoPubblicazione;

	@ManyToMany
	@NotEmpty
	private List<Autore> autori;

	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Immagine> immagini;
	
	@OneToOne
	@JoinColumn(name = "copertina_id")
	private Immagine copertina;

	@OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recensione> recensioni;

	public List<Recensione> getRecensioni() {
	    return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
	    this.recensioni = recensioni;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public LocalDate getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(LocalDate annoPubblicazione) {
		this.annoPubblicazione = annoPubblicazione;
	}

	public List<Autore> getAutori() {
		return autori;
	}

	public void setAutori(List<Autore> autori) {
		this.autori = autori;
	}

	public List<Immagine> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}
	

	public Immagine getCopertina() {
		return copertina;
	}

	public void setCopertina(Immagine copertina) {
		this.copertina = copertina;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annoPubblicazione, autori, id, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(annoPubblicazione, other.annoPubblicazione) && Objects.equals(autori, other.autori)
				&& Objects.equals(id, other.id) && Objects.equals(titolo, other.titolo);
	}
	
	
	
	

}
