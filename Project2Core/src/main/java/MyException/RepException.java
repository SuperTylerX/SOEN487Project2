package MyException;

import javax.xml.ws.WebFault;

@WebFault(name = "RepException", targetNamespace = "http://soap/")
public class RepException extends Exception {
    public RepException(String errorMessage) {
        super(errorMessage);
    }
}