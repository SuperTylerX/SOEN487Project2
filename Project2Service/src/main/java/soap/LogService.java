package soap;

import MyException.RepException;
import model.Logs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;

@WebService
public interface LogService {

    @WebMethod
    ArrayList<Logs> getChangeLogs(@WebParam(name="startDate") String startDate, @WebParam(name = "endDate") String endDat,
                                  @WebParam(name="type") String type );

    @WebMethod
    void clearLogs() throws RepException;

}
