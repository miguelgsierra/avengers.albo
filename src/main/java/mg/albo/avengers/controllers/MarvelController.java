package mg.albo.avengers.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.albo.avengers.models.Character;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    @GetMapping("/characters/{name}")
    public ResponseEntity<Character> getCharacters(@PathVariable("name") String name) {

        Character character = new Character("Miguel", null);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }
}
