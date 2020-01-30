package undo;

import javax.inject.Inject;

import document.Document;
import undo.validator.UndoManagerValidator;

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
		validator.validate(document, bufferSize);
		return undoManagerBuilder.build(undoManagerParametersOf(document, bufferSize));
	}

	private UndoManagerParameters undoManagerParametersOf(Document document, int bufferSize) {
		return ImmutableUndoManagerParameters.builder()
				.document(document)
				.bufferSize(bufferSize)
				.build();
	}
}
