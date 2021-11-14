package simonw.carwebdev.exception;

public class CarNotFoundException extends Exception {

	private static final long serialVersionUID = 1369777714520827651L;

    public CarNotFoundException(String message) {
        super(message);
    }
}
