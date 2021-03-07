package soap;

import MyException.RepException;
import impl.RepositoryManagerImpl;
import interfacedef.RepositoryManager;
import model.Logs;

import javax.jws.WebService;
import java.util.ArrayList;

@WebService(name = "LogService", endpointInterface = "soap.LogService")
public class LogServiceImpl implements LogService {

    private static final RepositoryManager manager = RepositoryManagerImpl.getInstance();

    @Override
    public ArrayList<Logs> getChangeLogs(String startDate, String endDate, String type) {
        long startdate = 0;
        long enddate = 0;
        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()) {
            startdate = Long.parseLong(startDate);
            enddate = Long.parseLong(endDate);
        }
        return manager.getLogsWithFilter(type, startdate, enddate);
    }

    @Override
    public void clearLogs() throws RepException {
        manager.clearLogs();
    }
}
