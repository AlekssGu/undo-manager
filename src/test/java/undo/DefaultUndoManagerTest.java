package undo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import change.Change;
import document.Document;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUndoManagerTest {

	private static final int BUFFER_SIZE_VALUE = 3;

	@Mock
	private Document document;
	@Mock
	private Change change;

	@InjectMocks
	private DefaultUndoManager undoManager = new DefaultUndoManager(document, BUFFER_SIZE_VALUE);

	@Test
	public void registersChangesMadeToDocumentInUndoRegister() {
		undoManager.registerChange(change);
		assertThat(undoManager.canUndo()).isTrue();
	}

	@Test(expected = IllegalStateException.class)
	public void throwsExceptionIfUndoInvokedOnEmptyUndoRegister() {
		undoManager.undo();
	}

	@Test
	public void revertsLatestChangeAndPutsItIntoRedoRegister() {
		undoManager.registerChange(change);

		undoManager.undo();

		assertThat(undoManager.canRedo()).isTrue();
		assertThat(undoManager.canUndo()).isFalse();
	}

	@Test
	public void revertsMultipleChanges() {
		registerAndUndoMultipleChanges();

		assertThat(undoManager.canRedo()).isTrue();
		assertThat(undoManager.canUndo()).isFalse();
	}

	@Test
	public void redoesMultipleChanges() {
		registerAndUndoMultipleChanges();

		undoManager.redo();
		undoManager.redo();
		undoManager.redo();

		assertThat(undoManager.canRedo()).isFalse();
		assertThat(undoManager.canUndo()).isTrue();
	}

	@Test
	public void continuesToRegisterChangesAfterLimitIsReached() {
		registerAndUndoMultipleChanges();
		undoManager.registerChange(mock(Change.class));
		undoManager.registerChange(mock(Change.class));

		undoManager.redo();
		undoManager.redo();
		undoManager.redo();

		assertThat(undoManager.canRedo()).isFalse();
		assertThat(undoManager.canUndo()).isTrue();
	}

	@Test
	public void appliesLatestUndoChangeAndPutsItIntoChangeRegister() {
		undoManager.registerChange(change);

		undoManager.undo();
		undoManager.redo();

		assertThat(undoManager.canUndo()).isTrue();
		assertThat(undoManager.canRedo()).isFalse();
	}

	@Test(expected = IllegalStateException.class)
	public void throwsExceptionWhenRevertChangeFailed() {
		willThrow(RuntimeException.class).given(change).revert(document);
		undoManager.registerChange(change);

		undoManager.undo();
	}

	@Test(expected = IllegalStateException.class)
	public void throwsExceptionWhenApplyChangeFailed() {
		willThrow(RuntimeException.class).given(change).apply(document);
		undoManager.registerChange(change);
		undoManager.undo();

		undoManager.redo();
	}

	@Test(expected = IllegalStateException.class)
	public void throwsExceptionIfRedoInvokedOnEmptyRedoRegister() {
		undoManager.redo();
	}

	private void registerAndUndoMultipleChanges() {
		Change firstChange = Mockito.mock(Change.class);
		Change secondChange = Mockito.mock(Change.class);
		Change thirdChange = Mockito.mock(Change.class);

		undoManager.registerChange(firstChange);
		undoManager.registerChange(secondChange);
		undoManager.registerChange(thirdChange);

		undoManager.undo();
		undoManager.undo();
		undoManager.undo();
	}

}