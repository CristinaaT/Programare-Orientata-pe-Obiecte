import java.time.LocalDate;
import java.util.*;

public class Notification {
	
	public enum NotificationType {
		ADD, REMOVE, MODIFY;
	}
	
	LocalDate dataa;
	NotificationType tip;
	int idDepartament;
	int idProdus;
	
	public Notification() {
		dataa = LocalDate.now();
		tip = null;
		idDepartament = 0;
		idProdus = 0;
	}
}