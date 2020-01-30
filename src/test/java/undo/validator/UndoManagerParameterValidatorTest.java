package undo.validator;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import document.Document;

@RunWith(MockitoJUnitRunner.class)
public class UndoManagerParameterValidatorTest {

	private static final int BUFFER_SIZE_VALUE = 1;
	private static final int BUFFER_SIZE_LOWER_THAN_ALLOWED = -1;
	private static final Document DOCUMENT_EMPTY = null;

	@InjectMocks
	private UndoManagerParameterValidator validator;

	@Test(expected = DocumentIsMandatoryException.class)
	public void throwsExceptionIfDocumentIsNull() {
		validator.validate(DOCUMENT_EMPTY, BUFFER_SIZE_VALUE);
	}

	@Test(expected = BufferSizeTooSmallException.class)
	public void throwsExceptionIfBufferSizeIsTooSmall() {
		validator.validate(mock(Document.class), BUFFER_SIZE_LOWER_THAN_ALLOWED);
	}

	@Test
	public void successfullyPassesValidationIfParametersAreCorrect() {
		validator.validate(mock(Document.class), BUFFER_SIZE_VALUE);
	}
}