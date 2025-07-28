package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecensioneRepository;
import jakarta.transaction.Transactional;


@Service
public class RecensioneService {
	@Autowired
	private RecensioneRepository recensioneRepository;
	
	@Transactional
	public Recensione save(Recensione recensione) {
		return recensioneRepository.save(recensione);
	}
	
	public Optional<Recensione> findByid(Long id){
		return recensioneRepository.findById(id);
	}
	
	public Optional<Recensione> giaScritta(User user,Libro libro){
		return recensioneRepository.findByAutoreAndLibro(user, libro);
	}
	
	public void deleteByid(Long id) {
		recensioneRepository.deleteById(id);
	}
	

}
