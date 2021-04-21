package mg.albo.avengers.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mg.albo.avengers.documents.Avenger;

public interface AvengerRepository extends MongoRepository<Avenger, String> {
    
    @Query("{'marvelID': ?0}")
    public Optional<Avenger> findByMarvelId(int marvelID);
    
    @Query(value = "{'marvelID': ?0}", fields = "{'lastSync': 1, 'editors': 1, 'writers': 1, 'colorists': 1}")
    public Optional<Avenger> getColaborators(int marvelID);
    
    @Query(value = "{'marvelID': ?0}", fields = "{'lastSync': 1, 'characters': 1}")
    public Optional<Avenger> getCharacters(int marvelID);
}