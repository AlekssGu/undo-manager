package undo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import document.Document;
import undo.validator.ImmutableUndoManagerValidatorParameters;
import undo.validator.UndoManagerValidator;
import undo.validator.UndoManagerValidatorParameters;

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

	private UndoManagerValidatorParameters validatorParameters;

	@Before
	public void setup() {
		validatorParameters = ImmutableUndoManagerValidatorParameters.builder()
				.document(document)
				.bufferSize(BUFFER_SIZE_VALUE)
				.build();
	}

	@Test
	public void validatesUndoManagerParameters() {
		undoManagerFactory.createUndoManager(document, BUFFER_SIZE_VALUE);
		verify(validator).validate(validatorParameters);
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
