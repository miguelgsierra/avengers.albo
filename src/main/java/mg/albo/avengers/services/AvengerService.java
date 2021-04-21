package mg.albo.avengers.services;

import mg.albo.avengers.exceptions.AvengerException;

public interface AvengerService {
    public void getCreators(String avengerCode) throws AvengerException;
}
