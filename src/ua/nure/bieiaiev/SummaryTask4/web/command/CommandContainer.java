package ua.nure.bieiaiev.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import ua.nure.bieiaiev.SummaryTask4.web.command.admin.BanUnbanCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.DeleteAnswerCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.DeleteQuestionCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.RemoveTestCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.SubmitCreateTestCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.SubmitEditTestCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.ViewAllUsersCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.ViewCreateTestCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.admin.ViewEditTestCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.commons.IncorrectCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.commons.LogoutCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.commons.ViewAllTestsCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.commons.ViewTestsBySubjectCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.student.SubmitPassTestCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.student.ViewTestResultCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.student.ViewTestToPassCommand;
import ua.nure.bieiaiev.SummaryTask4.web.command.student.ViewUsersProfileCommand;

/**
 * Container for commands
 * 
 * @author D.Bieliaiev
 *
 */
public abstract class CommandContainer {
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {

		commands.put("logout", new LogoutCommand());
		commands.put("viewAllTests", new ViewAllTestsCommand());
		commands.put("viewTestsBySubject", new ViewTestsBySubjectCommand());
		commands.put("incorrectCommand", new IncorrectCommand());

		commands.put("viewTestResult", new ViewTestResultCommand());
		commands.put("submitPassTest", new SubmitPassTestCommand());
		commands.put("viewTestToPass", new ViewTestToPassCommand());
		commands.put("viewUsersProfile", new ViewUsersProfileCommand());

		commands.put("banUnban", new BanUnbanCommand());
		commands.put("viewAllUsers", new ViewAllUsersCommand());
		commands.put("viewCreateTest", new ViewCreateTestCommand());
		commands.put("viewEditTest", new ViewEditTestCommand());
		commands.put("submitCreateTest", new SubmitCreateTestCommand());
		commands.put("submitEditTest", new SubmitEditTestCommand());
		commands.put("removeTest", new RemoveTestCommand());
		commands.put("deleteAnswer", new DeleteAnswerCommand());
		commands.put("deleteQuestion", new DeleteQuestionCommand());

		commands.put("submitRegister", new SubmitRegisterCommand());
		commands.put("viewRegister", new ViewRegisterCommand());
		commands.put("login", new LoginCommand());

	}

	/**
	 * Gets command if exists
	 * 
	 * @param commandName
	 * @return {@link Command}
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("incorrectCommand");
		}

		return commands.get(commandName);
	}

}
