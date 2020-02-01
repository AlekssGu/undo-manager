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
	public boolean canUndo() {
		return hasChangesToUndo();
	}

	@Override
	public void undo() {
		if (this.canUndo()) {
			Change change = getLatestChangeToRevert();
			revertChange(change);
			putRevertedChangeToRedoRegister(change);
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public boolean canRedo() {
		return hasChangesToRedo();
	}

	@Override
	public void redo() {
		if (this.canRedo()) {
			Change change = getLatestChangeToRedo();
			applyChange(change);
			putAppliedChangeToCurrentChangeRegister(change);
		} else {
			throw new IllegalStateException();
		}
	}

	private void revertChange(Change changeToUndo) {
		try {
			changeToUndo.revert(this.document);
		} catch (Exception exception) {
			throw new IllegalStateException(exception);
		}
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

	private Change getLatestChangeToRevert() {
		return this.currentChanges.poll();
	}

	private Change getLatestChangeToRedo() {
		return this.changesToRedo.poll();
	}

	private void applyChange(Change changeToRedo) {
		try {
			changeToRedo.apply(this.document);
		} catch (Exception exception) {
			throw new IllegalStateException(exception);
		}
	}
}
