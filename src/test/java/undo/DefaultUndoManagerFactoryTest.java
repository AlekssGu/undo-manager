package undo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import document.Document;
import undo.validator.UndoManagerValidator;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUndoManagerFactoryTest {

	private static final int BUFFER_SIZE_VALUE = 1;

	@Mock
	private UndoManagerValidator validator;
	@Mock
	private UndoManagerBuilder undoManagerBuilder;
	@Mock
	private Document document;

	@Captor
	private ArgumentCaptor<UndoManagerParameters> managerParametersArgumentCaptor;

	@InjectMocks
	private DefaultUndoManagerFactory undoManagerFactory;

	@Test
	public void validatesUndoManagerParameters() {
		int bufferSize = BUFFER_SIZE_VALUE;
		undoManagerFactory.createUndoManager(document, bufferSize);
		verify(validator).validate(document, bufferSize);
	}

	@Test
	public void buildsUndoManagerWithPassedParameters() {
		int bufferSize = BUFFER_SIZE_VALUE;

		undoManagerFactory.createUndoManager(document, bufferSize);

		verify(undoManagerBuilder).build(managerParametersArgumentCaptor.capture());
		assertThatParametersAreEqualToPassed(bufferSize);
	}

	private void assertThatParametersAreEqualToPassed(int bufferSize) {
		UndoManagerParameters capturedParameters = managerParametersArgumentCaptor.getValue();
		assertThat(capturedParameters.getDocument()).isEqualTo(document);
		assertThat(capturedParameters.getBufferSize()).isEqualTo(bufferSize);
	}
}
