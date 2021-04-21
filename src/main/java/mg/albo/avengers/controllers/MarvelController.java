package mg.albo.avengers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.albo.avengers.documents.Avenger;
import mg.albo.avengers.exceptions.AvengerException;
import mg.albo.avengers.services.AvengerService;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    @Autowired
    private AvengerService service;

    @GetMapping("/colaborators/{name}")
    public ResponseEntity<?> getColaborators(@PathVariable("name") String name) {

        try {
            Avenger avenger = service.getCreators(name);
            return new ResponseEntity<>(avenger.getColaboratorsResult(), HttpStatus.OK);
        } catch (AvengerException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping("/characters/{name}")
    public ResponseEntity<?> getCharacters(@PathVariable("name") String name) {

        try {
            Avenger avenger = service.getCharacters(name);
            return new ResponseEntity<>(avenger.getCharactersResult(), HttpStatus.OK);
        } catch (AvengerException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
