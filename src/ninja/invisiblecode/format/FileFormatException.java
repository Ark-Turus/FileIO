package ninja.invisiblecode.format;

import java.io.IOException;

public class FileFormatException extends IOException {
	public FileFormatException(String message){
		super(message);
	}
	public FileFormatException(FileFormatChunk chunk){
		super("Failed to read chunk: [" + chunk.getClass() + "]");
	}
	
}
