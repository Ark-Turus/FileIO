package ninja.invisiblecode.textify.javaserial;

import java.io.IOException;

public class SerializedObject extends SerializedEntity {

	public final SerializedClassDesc classDesc;

	public SerializedObject(SerializedReader reader) throws IOException {
		classDesc = (SerializedClassDesc) reader.getEntity(reader.in.readByte());
		int handle = reader.newHandle();
		reader.entities.put(handle, this);
	}

	public String toString() {
		return "Object: " + classDesc.className + "\n" + classDesc;
	}
}
