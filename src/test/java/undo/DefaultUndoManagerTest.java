package undo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import change.Change;
import document.Document;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUndoManagerTest {

	private static final int BUFFER_SIZE_VALUE = 1;

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
	}

	@Test
	public void appliesLatestUndoChangeAndPutsItIntoChangeRegister() {
		undoManager.registerChange(change);

		undoManager.undo();
		undoManager.redo();

		assertThat(undoManager.canRedo()).isFalse();
		assertThat(undoManager.canUndo()).isTrue();
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

}