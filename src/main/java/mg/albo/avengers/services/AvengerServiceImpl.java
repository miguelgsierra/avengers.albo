package mg.albo.avengers.services;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mg.albo.avengers.exceptions.AvengerException;
import mg.albo.avengers.models.Character;
import mg.albo.avengers.models.Comic;
import mg.albo.avengers.models.Creator;
import mg.albo.avengers.models.Data;
import mg.albo.avengers.models.DataCharacters;
import mg.albo.avengers.models.DataCreators;
import mg.albo.avengers.models.ResponseBody;
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

    @Override
    public void getCreators(String avengerCode) throws AvengerException {
        AvengerType avengerType = Constants.avengersAvailables.get(avengerCode);
        if (avengerType == null)
            throw new AvengerException(AvengerException.NotFoundException(avengerCode));

        syncDataToDatabase(avengerType.getId());
    }

    private void syncDataToDatabase(int marvelID) {
        callApiMarvel(marvelID);
    }

    private void callApiMarvel(int marvelID) {
        String url = getApiMarvelUrl(marvelID);
        ResponseEntity<ResponseBody> response = restTemplate.getForEntity(url, ResponseBody.class);
        ResponseBody body = response.getBody();
        Data data = body.getData();

        for (Comic comic : data.getResults()) {
            System.out.println("Comic: " + comic.getTitle());

            DataCreators creators = comic.getCreators();
            for (Creator creator : creators.getItems()) {
                System.out.println("[" + creator.getRole() + "]: " + creator.getName());
            }

            // DataCharacters characters = comic.getCharacters();
            // for (Character character : characters.getItems()) {

            // }
        }
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
