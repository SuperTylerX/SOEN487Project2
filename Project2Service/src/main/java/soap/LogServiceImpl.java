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
        if (startDate != null && endDate != null && !startDate.isEmpty() && !endDate.isEmpty()
                && !startDate.equals("?") && !endDate.equals("?")) {
            long start = Long.parseLong(startDate);
            long end = Long.parseLong(endDate);
            return manager.getLogsByDate(start, end);
        } else {
            if (type != null && !type.isEmpty() && !type.equals("?")) {
                return manager.getLogsByType(type);
            } else {
                return manager.getAllLogs();
            }
        }
    }

    @Override
    public void clearLogs() throws RepException {
        manager.clearLogs();
    }
}
