package undo.validator;

import javax.annotation.Nullable;

import org.immutables.value.Value;

import document.Document;

@Value.Immutable
public interface UndoManagerValidatorParameters {

	@Nullable
	Document getDocument();

	int getBufferSize();

}
