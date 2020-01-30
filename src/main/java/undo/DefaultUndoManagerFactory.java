package undo;

import javax.inject.Singleton;

import document.Document;

@Singleton
class DefaultUndoManagerFactory implements UndoManagerFactory {

	@Override
	public UndoManager createUndoManager(Document document, int bufferSize) {
		return null;
	}
}
