import com.google.inject.AbstractModule;

import undo.UndoModule;

public class TextEditorModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new UndoModule());
	}
}
