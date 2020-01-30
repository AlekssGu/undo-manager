package undo;

import change.Change;
import document.Document;

class DefaultUndoManager implements UndoManager {

	private Document document;
	private int bufferSize;

	DefaultUndoManager(Document document, int bufferSize) {
		this.document = document;
		this.bufferSize = bufferSize;
	}

	@Override
	public void registerChange(Change change) {

	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public void undo() {

	}

	@Override
	public boolean canRedo() {
		return false;
	}

	@Override
	public void redo() {

	}
}
