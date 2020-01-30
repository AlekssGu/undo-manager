package undo;

import com.google.inject.AbstractModule;

import undo.validator.UndoManagerValidatorModule;

public class UndoModule extends AbstractModule {

	@Override
	protected void configure() {
		bindClasses();
		installSubmodules();
	}

	private void bindClasses() {
		bind(UndoManager.class).to(DefaultUndoManager.class);
		bind(UndoManagerFactory.class).to(DefaultUndoManagerFactory.class);
		bind(UndoManagerBuilder.class);
	}

	private void installSubmodules() {
		install(new UndoManagerValidatorModule());
	}
}
