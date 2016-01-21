package ninja.invisiblecode.textify.javaserial;

import java.io.IOException;

public class SerializedString extends SerializedEntity {

	public final String value;

	public SerializedString(SerializedReader reader) throws IOException {
		String value = reader.in.readShortPascal();
		this.value = value.substring(1, value.length() - 1).replace('/', '.');
	}

	public SerializedString(SerializedReader reader, boolean extraLong) throws IOException {
		String value;
		if (!extraLong)
			value = reader.in.readShortPascal();
		else {
			long length = reader.in.readLong();
			// TODO Read long strings
			if (length > Integer.MAX_VALUE)
				throw new IndexOutOfBoundsException("String length larger than valid array length");
			value = new String(reader.in.readByteSequence(new byte[(int) length]));
		}
		this.value = value.substring(1, value.length() - 1).replace('/', '.');
	}

}
