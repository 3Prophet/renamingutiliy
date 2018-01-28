package ch.mardmi.renamingutility.command;

public class RemoveFromNameEditor extends AbstractFileNameEditor {
	
	private int firstNrOfSymbols;
	
	private int lastNrOfSymbols;
	
	public RemoveFromNameEditor(boolean selected, int firstNrOfSymbols, int lastNrOfSymbols) {
		super(selected);
		if (!selected) {
			return;
		}
		this.firstNrOfSymbols = firstNrOfSymbols;
		this.lastNrOfSymbols = lastNrOfSymbols;
	}

	@Override
	protected String resultOfEdition(String fileName) {
		return fileName.substring(firstNrOfSymbols, fileName.length() - lastNrOfSymbols);
	}

}
