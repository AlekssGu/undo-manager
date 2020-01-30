package undo.validator;

import com.google.inject.AbstractModule;

public class UndoManagerValidatorModule extends AbstractModule {

	@Override
	protected void configure() {
		bindClasses();
	}

	private void bindClasses() {
		bind(UndoManagerValidator.class).to(UndoManagerParameterValidator.class);
	}
}
