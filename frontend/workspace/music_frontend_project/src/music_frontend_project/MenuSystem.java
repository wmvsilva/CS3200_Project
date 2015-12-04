package music_frontend_project;

import java.io.IOException;
import java.sql.SQLException;

public final class MenuSystem {

	DBConnector dbConn;

	public MenuSystem(DBConnector c) {
		dbConn = c;
	}

	public void mainMenu() throws IOException, SQLException {
		UserInteraction.printOptions("Search", "Add New", "Exit");
		Integer choice = UserInteraction.provideUserPick(2);
		switch (choice) {
		case (0):
			SearchMenu searchMenu = new SearchMenu(dbConn);
			searchMenu.searchMenu();
			break;
		case (1):
			AddNewMenu addNewMenu = new AddNewMenu(dbConn);
			addNewMenu.addNewEntityMenu();
			break;
		case (2):
			return;
		}
	}
}
