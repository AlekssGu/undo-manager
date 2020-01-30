package undo;

import javax.inject.Singleton;

import change.Change;

@Singleton
class DefaultUndoManager implements UndoManager {

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
