package ch.mardmi.renamingutility.command;

public class AddCommand implements Command {
	
	private String prefix;
	
	private String suffix;
	
	private int position;
	
	private String insert;
	
	public AddCommand(String prefix, String suffix, int position, String insert) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.position = position;
		this.insert = insert;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
