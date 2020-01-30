package undo;

import com.google.inject.AbstractModule;

public class UndoModule extends AbstractModule {

	@Override
	protected void configure() {
		bindClasses();
	}

	private void bindClasses() {
		bind(UndoManager.class).to(DefaultUndoManager.class);
		bind(UndoManagerFactory.class).to(DefaultUndoManagerFactory.class);
	}
}
