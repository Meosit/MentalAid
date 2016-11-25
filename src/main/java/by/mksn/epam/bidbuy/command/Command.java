package by.mksn.epam.bidbuy.command;

import by.mksn.epam.bidbuy.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    String execute(HttpServletRequest request) throws CommandException;

}
