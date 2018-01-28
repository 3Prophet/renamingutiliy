package ch.mardmi.renamingutility.command;

public class AddToNameEditor implements FileNameEditor {
	
	private String prefix;
	private String suffix;
	private String insert;
	private int position;
	private boolean selected;
	private String fileName;

	public AddToNameEditor( 
							boolean selected,
							String prefix,
							String suffix, 
							String insert, 
							int position) {
		this(selected);
		if (!selected) {
			return;
		}
		
		this.prefix = prefix;
		this.suffix = suffix;
		this.insert = insert;
		this.position = position;
	}
	
	public AddToNameEditor(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String editName(String fileName) {
		
		if (!selected) {
			return fileName;
		}
		StringBuilder sb = new StringBuilder(fileName);
		sb.insert(0, prefix).append(suffix).insert(position, insert);
		return sb.toString();
	}

}
