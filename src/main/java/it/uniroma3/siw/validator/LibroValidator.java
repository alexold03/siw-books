package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@Component
public class LibroValidator implements Validator {

    @Autowired
    private LibroService libroService;

    @Override
    public void validate(Object o, Errors errors) {
        Libro libro = (Libro) o;

        if (libro.getTitolo() != null && !libro.getTitolo().trim().isEmpty()
                && libro.getAnnoPubblicazione() != null) {

            boolean exists = this.libroService.existsByTitoloAndAnnoPubblicazione(
                    libro.getTitolo(), libro.getAnnoPubblicazione());

            // Solo se è un libro nuovo (non ha ID)
            if (exists && libro.getId() == null) {
                errors.rejectValue("titolo", "libro.duplicate", "Questo libro esiste già!");
            }
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Libro.class.equals(clazz);
    }
}
