package ninja.invisiblecode.textify.javaserial;

import java.io.IOException;

public class SerializedArray extends SerializedEntity{

	public final SerializedClassDesc classDesc;

	public SerializedArray(SerializedReader reader) throws IOException {
		classDesc = (SerializedClassDesc) reader.getEntity(reader.in.readByte());
		int handle = reader.newHandle();
		reader.entities.put(handle, this);
	}
}
