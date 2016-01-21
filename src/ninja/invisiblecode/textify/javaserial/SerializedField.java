package ninja.invisiblecode.textify.javaserial;

import java.io.IOException;
import java.io.ObjectStreamConstants;

import ninja.invisiblecode.format.MalformedFileException;

public class SerializedField {

	public final SerializedFieldType type;
	public final String name;
	public final SerializedString className;

	protected SerializedField(SerializedReader reader) throws IOException {
		type = SerializedFieldType.get(reader.in.readByte());
		name = reader.in.readShortPascal();
		className = type == SerializedFieldType.OBJECT || type == SerializedFieldType.ARRAY
				? (SerializedString) reader.getEntity(reader.in.readByte()) : null;
	}

	public String toString() {
		return className != null ? className.value + " " + name : type + " " + name;
	}

}
