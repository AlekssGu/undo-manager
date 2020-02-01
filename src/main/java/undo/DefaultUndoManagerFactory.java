package undo;

import javax.inject.Inject;

import document.Document;
import undo.validator.ImmutableUndoManagerValidatorParameters;
import undo.validator.UndoManagerValidator;
import undo.validator.UndoManagerValidatorParameters;

class DefaultUndoManagerFactory implements UndoManagerFactory {

	private UndoManagerValidator validator;
	private UndoManagerBuilder undoManagerBuilder;

	@Inject
	DefaultUndoManagerFactory(UndoManagerValidator validator, UndoManagerBuilder undoManagerBuilder) {
		this.validator = validator;
		this.undoManagerBuilder = undoManagerBuilder;
	}

	@Override
	public UndoManager createUndoManager(Document document, int bufferSize) {
		validateParameters(validationParametersOf(document, bufferSize));
		return undoManagerBuilder.build(undoManagerParametersOf(document, bufferSize));
	}

	private void validateParameters(UndoManagerValidatorParameters parameters) {
		validator.validate(parameters);
	}

	private UndoManagerValidatorParameters validationParametersOf(Document document, int bufferSize) {
		return ImmutableUndoManagerValidatorParameters.builder()
				.document(document)
				.bufferSize(bufferSize)
				.build();
	}

	private UndoManagerParameters undoManagerParametersOf(Document document, int bufferSize) {
		return ImmutableUndoManagerParameters.builder()
				.document(document)
				.bufferSize(bufferSize)
				.build();
	}
}
