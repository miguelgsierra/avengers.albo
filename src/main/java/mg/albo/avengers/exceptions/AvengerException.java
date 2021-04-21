package mg.albo.avengers.exceptions;

public class AvengerException extends Exception {
    private static final long serialVersionUID = 1L;

    public AvengerException(String message) {
        super(message);
    }

    public static String NotFoundException(String code) {
        return "Avenger " + code + " not found!";
    }
}
