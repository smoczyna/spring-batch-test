package eu.squadd.batch.domain.casandra.exceptions;

public enum ErrorEnum {
	
	SINGLE_ROW(0, "This query returned single row."), 
	NO_ROWS(1, "This query returned no rows."), 
	MULTIPLE_ROWS(2, "This query returned multiple rows."), 
	DATABASE_ERROR(3, "A database error has occured.");

	private final int code;
	private final String description;

	private ErrorEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}