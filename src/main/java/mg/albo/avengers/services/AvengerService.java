package mg.albo.avengers.services;

import mg.albo.avengers.documents.Avenger;
import mg.albo.avengers.exceptions.AvengerException;

public interface AvengerService {
    public Avenger getCreators(String avengerCode) throws AvengerException;
    public Avenger getCharacters(String avengerCode) throws AvengerException;
}
