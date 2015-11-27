package music_frontend_project;

import music_frontend_project.actions.Action;

public class Token {

	String choiceDescription = null;

	Action action;

	Menu nextMenu;

	Token(String c, Action a, Menu m) {
		choiceDescription = c;
		action = a;
		nextMenu = m;
	}

	public void executeAction() {
		action.execute();
	}

	public Menu nextMenu() {
		return nextMenu;
	}

	public String getDescription() {
		return choiceDescription;
	}

}
