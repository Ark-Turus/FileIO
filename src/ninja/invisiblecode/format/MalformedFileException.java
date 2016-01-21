package ninja.invisiblecode.format;

import java.io.IOException;

public class MalformedFileException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839669406946126292L;

	public MalformedFileException(String expected, String received) {
		super("File did not match expected format. Expected [" + expected + "]. Received [" + received + "] instead.");
	}

}
