package music_frontend_project;

import music_frontend_project.actions.Action;
import music_frontend_project.actions.NoAction;

public final class CreatedMenus {

	private Menu mainMenu;

	private Menu exitMenu;

	public void initializeMenus() {
		initializeExitMenu();
		initializeMainMenu();
	}

	private void initializeExitMenu() {
		exitMenu = new Menu();
		exitMenu.setShouldContinue(false);
	}

	private void initializeMainMenu() {
		mainMenu = new Menu();
		mainMenu.addOption("Exit", noAction(), exitMenu);
		mainMenu.addOption("Search", searchAction(), mainMenu);
		mainMenu.addOption("Add New", noAction(), mainMenu);
	}

	private Action noAction() {
		return new NoAction();
	}

	private Action searchAction() {
		return new SearchAction();
	}

	public Menu mainMenu() {
		return mainMenu;
	}

}
