package mg.albo.avengers.services;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mg.albo.avengers.models.Comic;
import mg.albo.avengers.models.Data;
import mg.albo.avengers.models.ResponseBody;

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
    public void getCreators(String avengerCode) {
        syncDataToDatabase();
    }

    private void syncDataToDatabase() {
        callApiMarvel(1009368);
    }

    private void callApiMarvel(int marvelID) {
        String url = getApiMarvelUrl(marvelID);
        ResponseEntity<ResponseBody> response = restTemplate.getForEntity(url, ResponseBody.class);
        ResponseBody body = response.getBody();
        Data data = body.getData();

        for(Comic comic : data.getResults()) {
            System.out.println("Comic: " + comic.getTitle());
        }
    }

    private String getHash(long ts) {
        String password = ts + privateKey + publicKey;
        return DigestUtils.md5Hex(password).toString();
    }

    private String getApiMarvelUrl(int marvelID) {
        long ts = new Date().getTime();
        String hash = getHash(ts);

        return String.format("%s/characters/%s/comics?ts=%s&apikey=%s&hash=%s&orderBy=title&limit=20", marvelApi, marvelID, ts, publicKey, hash);
    }
}
