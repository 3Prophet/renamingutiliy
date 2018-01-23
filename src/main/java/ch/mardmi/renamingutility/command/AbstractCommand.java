package ch.mardmi.renamingutility.command;

import ch.mardmi.renamingutility.model.DirectoryContentModel;

public abstract class AbstractCommand implements Command {
	
	protected static DirectoryContentModel model;

	@Override
	public abstract void execute();

	@Override
	public abstract void reset();
	
	public static void setDirectoryModel(DirectoryContentModel model) {
		AbstractCommand.model = model;
	}
}
