package undo.validator;

import javax.inject.Singleton;

import document.Document;

@Singleton
class UndoManagerParameterValidator implements UndoManagerValidator {

	private static final int BUFFER_SIZE_LOWER_LIMIT = 0;

	public void validate(UndoManagerValidatorParameters parameters) {
		validateDocument(parameters.getDocument());
		validateBufferSize(parameters.getBufferSize());
	}

	private void validateDocument(Document document) {
		if (document == null) {
			throw new DocumentIsMandatoryException();
		}
	}

	private void validateBufferSize(int bufferSize) {
		if (bufferSize < BUFFER_SIZE_LOWER_LIMIT) {
			throw new BufferSizeTooSmallException();
		}
	}
}
