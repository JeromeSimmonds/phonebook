package com.jeromesimmonds.phonebook.core;

/**
 * @author Jerome Simmonds
 *
 */
public class CoreException extends RuntimeException {

	private static final long serialVersionUID = -7204446082722982228L;

	public CoreException() {
	}

	public CoreException(String message) {
		super(message);
	}

	public CoreException(Throwable cause) {
		super(cause);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
	}
}
