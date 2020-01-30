package undo.validator;

class DocumentIsMandatoryException extends RuntimeException {

	DocumentIsMandatoryException() {
		super("Document is mandatory!");
	}
}
