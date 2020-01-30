package undo.validator;

class BufferSizeTooSmallException extends RuntimeException {

	BufferSizeTooSmallException() {
		super("The buffer size is too small!");
	}
}
