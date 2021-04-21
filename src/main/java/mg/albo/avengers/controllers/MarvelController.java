package mg.albo.avengers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.albo.avengers.services.AvengerService;

@RestController
@RequestMapping("/marvel")
public class MarvelController {

    @Autowired
    private AvengerService service;

    @GetMapping("/characters/{name}")
    public ResponseEntity<?> getCharacters(@PathVariable("name") String name) {

        service.getCreators(name);

        return new ResponseEntity<String>("Test call api marvel", HttpStatus.OK);
    }
}
