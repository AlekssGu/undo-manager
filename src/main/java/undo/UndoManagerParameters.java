package undo;

import org.immutables.value.Value.Immutable;

import document.Document;

@Immutable
interface UndoManagerParameters {

	Document getDocument();

	int getBufferSize();

}
