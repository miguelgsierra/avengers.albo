package mg.albo.avengers.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import mg.albo.avengers.models.Character;

public interface AvengerRepository extends MongoRepository<Character, String> {
    
}
