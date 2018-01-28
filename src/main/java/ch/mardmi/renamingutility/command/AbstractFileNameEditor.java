package ch.mardmi.renamingutility.command;

public abstract class AbstractFileNameEditor implements FileNameEditor {
	
	protected boolean selected;
	
	protected AbstractFileNameEditor(boolean selected) {
		if (!selected) {
			return;
		}
		this.selected = selected;
	}

	@Override
	public String editName(String fileName) {
		if (!selected) {
			return fileName;
		}
		return resultOfEdition(fileName);
	}
	
	protected abstract String resultOfEdition(String fileName);
}
