package undo.validator;

import org.immutables.value.Value;

import document.Document;

@Value.Immutable
public interface UndoManagerValidatorParameters {

	Document getDocument();

	int getBufferSize();

}
