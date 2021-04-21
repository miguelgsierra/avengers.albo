package mg.albo.avengers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.albo.avengers.models.Character;
import mg.albo.avengers.repositories.AvengerRepository;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    @Autowired
    private AvengerRepository repository;

    @GetMapping("/characters/{name}")
    public ResponseEntity<?> getCharacters(@PathVariable("name") String name) {
        List<Character> characters = repository.findAll();

        return new ResponseEntity<List<Character>>(characters, HttpStatus.OK);
    }
}
