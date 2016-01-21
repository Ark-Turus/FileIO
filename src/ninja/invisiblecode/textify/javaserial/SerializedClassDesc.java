package ninja.invisiblecode.textify.javaserial;

import java.io.IOException;
import java.io.ObjectStreamConstants;
import java.util.ArrayList;
import java.util.List;

import ninja.invisiblecode.format.FileFormatInputStream;

public class SerializedClassDesc extends SerializedEntity{

	public final String className;
	public final long uid;
	public final int handle;
	public final List<SerializedField> fields;
	public final SerializedClassDesc parent;

	public SerializedClassDesc(SerializedReader reader) throws IOException {
		className = reader.in.readShortPascal();
		uid = reader.in.readLong();
		handle = reader.newHandle();
		byte flags = reader.in.readByte();
		short fields = reader.in.readShort();
		this.fields = new ArrayList<SerializedField>();
		for (int i = 0; i < fields; i++)
			this.fields.add(new SerializedField(reader));
		byte block;
//		while ((block = reader.in.readByte()) != ObjectStreamConstants.TC_ENDBLOCKDATA)
//			new SerializedObject(this);
		reader.in.readByte();
		byte chain = reader.in.readByte();
		parent = (SerializedClassDesc) reader.getEntity(chain);
		reader.classDescs.put(handle, this);
	}

	public String toString() {
		String s = "class " + className + " {\n";
		for (SerializedField field : fields)
			s += "\t" + field + ";\n";
		return s + "}";
	}
}
