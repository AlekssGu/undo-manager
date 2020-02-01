package undo;

import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;

import change.Change;
import document.Document;

class DefaultUndoManager implements UndoManager {

	private Document document;
	private Queue<Change> currentChanges;
	private Queue<Change> changesToRedo;

	DefaultUndoManager(Document document, int bufferSize) {
		this.document = document;
		this.currentChanges = new CircularFifoQueue<>(bufferSize);
		this.changesToRedo = new CircularFifoQueue<>(bufferSize);
	}

	@Override
	public void registerChange(Change change) {
		this.currentChanges.add(change);
	}

	@Override
	public void undo() {
		if (canUndo()) {
			Change change = getLatestChangeToUndo();
			undoChange(change);
			putRevertedChangeToRedoRegister(change);
		} else {
			operationNotAllowed();
		}
	}

	@Override
	public boolean canUndo() {
		return hasChangesToUndo();
	}

	@Override
	public void redo() {
		if (canRedo()) {
			Change change = getLatestChangeToRedo();
			applyChange(change);
			putAppliedChangeToCurrentChangeRegister(change);
		} else {
			operationNotAllowed();
		}
	}

	@Override
	public boolean canRedo() {
		return hasChangesToRedo();
	}

	private void operationNotAllowed() {
		throw new IllegalStateException();
	}

	private void undoChange(Change changeToUndo) {
		try {
			changeToUndo.revert(this.document);
		} catch (Exception exception) {
			throw new IllegalStateException(exception);
		}
	}

	private void applyChange(Change changeToRedo) {
		try {
			changeToRedo.apply(this.document);
		} catch (Exception exception) {
			operationFailed(exception);
		}
	}

	private void operationFailed(Exception exception) {
		throw new IllegalStateException(exception);
	}

	private void putRevertedChangeToRedoRegister(Change change) {
		this.changesToRedo.add(change);
	}

	private void putAppliedChangeToCurrentChangeRegister(Change change) {
		this.currentChanges.add(change);
	}

	private boolean hasChangesToUndo() {
		return !this.currentChanges.isEmpty();
	}

	private boolean hasChangesToRedo() {
		return !this.changesToRedo.isEmpty();
	}

	private Change getLatestChangeToUndo() {
		return this.currentChanges.poll();
	}

	private Change getLatestChangeToRedo() {
		return this.changesToRedo.poll();
	}
}
