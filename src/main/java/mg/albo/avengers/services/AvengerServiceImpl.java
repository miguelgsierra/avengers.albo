package mg.albo.avengers.services;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mg.albo.avengers.documents.Avenger;
import mg.albo.avengers.exceptions.AvengerException;
import mg.albo.avengers.models.Character;
import mg.albo.avengers.models.Comic;
import mg.albo.avengers.models.Creator;
import mg.albo.avengers.models.Data;
import mg.albo.avengers.models.DataCharacters;
import mg.albo.avengers.models.DataCreators;
import mg.albo.avengers.models.ResponseBody;
import mg.albo.avengers.repositories.AvengerRepository;
import mg.albo.avengers.utils.Constants;
import mg.albo.avengers.utils.Constants.AvengerType;

@Service
public class AvengerServiceImpl implements AvengerService {

    @Value("${marvel.api}")
    private String marvelApi;

    @Value("${marvel.private.key}")
    private String privateKey;

    @Value("${marvel.public.key}")
    private String publicKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AvengerRepository repository;

    @Override
    public void getCreators(String avengerCode) throws AvengerException {
        AvengerType avengerType = Constants.avengersAvailables.get(avengerCode);
        if (avengerType == null)
            throw new AvengerException(AvengerException.NotFoundException(avengerCode));

        syncDataToDatabase(avengerType.getId(), avengerType.getName());
    }

    private void syncDataToDatabase(int marvelID, String avengerName) {
        Avenger avenger = new Avenger(marvelID, avengerName);
        Comic[] comics = callApiMarvel(marvelID);

        for (Comic comic : comics) {
            System.out.println("Comic: " + comic.getTitle());

            DataCreators creators = comic.getCreators();
            for (Creator creator : creators.getItems()) {
                avenger.addCreator(creator);
            }

            DataCharacters characters = comic.getCharacters();
            for (Character character : characters.getItems()) {
                if (!character.getName().equals(avengerName)) {
                    avenger.addCharacter(character.getName(), comic.getTitle());
                }
            }
        }

        save(marvelID, avenger);
    }

    private void save(int marvelID, Avenger body) {
        Optional<Avenger> avenger = repository.findByMarvelId(marvelID);

        long lastSync = new Date().getTime();
        if(avenger.isPresent()) {
            Avenger avengerToUpdate = avenger.get();
            avengerToUpdate.setLast_sync(lastSync);
            avengerToUpdate.setEditors(body.getEditors());
            avengerToUpdate.setWriters(body.getWriters());
            avengerToUpdate.setColorists(body.getColorists());
            avengerToUpdate.setCharacters(body.getCharacters());

            repository.save(avengerToUpdate);
        } else {
            body.setLast_sync(lastSync);
            repository.save(body);
        }
    }

    private Comic[] callApiMarvel(int marvelID) {
        String url = getApiMarvelUrl(marvelID);
        ResponseEntity<ResponseBody> response = restTemplate.getForEntity(url, ResponseBody.class);
        ResponseBody body = response.getBody();
        Data data = body.getData();

        return data.getResults();
    }

    private String getHash(long ts) {
        String password = ts + privateKey + publicKey;
        return DigestUtils.md5Hex(password).toString();
    }

    private String getApiMarvelUrl(int marvelID) {
        long ts = new Date().getTime();
        String hash = getHash(ts);

        return String.format("%s/characters/%s/comics?ts=%s&apikey=%s&hash=%s&orderBy=title&limit=20", marvelApi,
                marvelID, ts, publicKey, hash);
    }
}
