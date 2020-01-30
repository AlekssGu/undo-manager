package undo;

class UndoManagerBuilder {

	UndoManager build(UndoManagerParameters parameters) {
		return new DefaultUndoManager(parameters.getDocument(), parameters.getBufferSize());
	}

}
