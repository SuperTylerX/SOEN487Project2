package MyException;

import javax.xml.ws.WebFault;

@WebFault(name = "RepException", targetNamespace = "http://soap/")
public class RepException extends Exception {
    public RepException() {
        super("This function is not implemented.");
    }
}