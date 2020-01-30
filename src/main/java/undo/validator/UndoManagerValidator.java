package undo.validator;

import document.Document;

public interface UndoManagerValidator {

	void validate(Document document, int bufferSize);

}
