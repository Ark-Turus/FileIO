package ninja.invisiblecode.textify.javaserial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.rmi.CORBA.ClassDesc;

import ninja.invisiblecode.format.FileFormatException;
import ninja.invisiblecode.format.FileFormatInputStream;
import ninja.invisiblecode.format.MalformedFileException;

public class SerializedReader {
	protected FileFormatInputStream in;
	private int handle = ObjectStreamConstants.baseWireHandle;

	protected HashMap<Integer, SerializedClassDesc> classDescs = new HashMap<Integer, SerializedClassDesc>();
	protected HashMap<Integer, SerializedEntity> entities = new HashMap<Integer, SerializedEntity>();
	protected HashMap<Integer, SerializedArray> arrays = new HashMap<Integer, SerializedArray>();

	public SerializedReader(InputStream in) throws FileNotFoundException {
		this.in = new FileFormatInputStream(in);
	}

	public void readFile() throws IOException {
		if (!in.validateShort(ObjectStreamConstants.STREAM_MAGIC))
			throw new FileFormatException("File does not contain a Serializable stream.");
		if (!in.validateShort(ObjectStreamConstants.STREAM_VERSION))
			throw new FileFormatException("Serializable Stream must be version 5.");
		while (in.available() > 0) {
			readObject(in.readByte());
		}
	}

	private void readObject(byte type) throws IOException {
		switch (type) {
			case ObjectStreamConstants.TC_OBJECT:
				System.out.println(new SerializedObject(this));
				break;
			case ObjectStreamConstants.TC_CLASS:
				break;
			case ObjectStreamConstants.TC_ARRAY:
				System.out.println(new SerializedArray(this));
				break;
			case ObjectStreamConstants.TC_STRING:
				System.out.println(new SerializedString(this));
				break;
			case ObjectStreamConstants.TC_LONGSTRING:
				System.out.println(new SerializedString(this, true));
				break;
			case ObjectStreamConstants.TC_ENUM:
				break;
			case ObjectStreamConstants.TC_CLASSDESC:
				System.out.println(new SerializedClassDesc(this));
				break;
			case ObjectStreamConstants.TC_PROXYCLASSDESC:

			case ObjectStreamConstants.TC_REFERENCE:
				break;
			case ObjectStreamConstants.TC_NULL:
				break;
			case ObjectStreamConstants.TC_EXCEPTION:
				break;
			case ObjectStreamConstants.TC_RESET:
				return;
			default:
				throw new MalformedFileException("newObject", "Code: " + type);
		}
	}

	private byte[] flags = new byte[] { ObjectStreamConstants.SC_BLOCK_DATA, ObjectStreamConstants.SC_ENUM,
			ObjectStreamConstants.SC_EXTERNALIZABLE, ObjectStreamConstants.SC_SERIALIZABLE,
			ObjectStreamConstants.SC_WRITE_METHOD };

	protected int newHandle() {
		return handle++;
	}

	protected SerializedEntity getEntity(byte type) throws IOException {
		switch (type) {
			case ObjectStreamConstants.TC_REFERENCE:
				return entities.get(in.readInt());
			case ObjectStreamConstants.TC_OBJECT:
				return new SerializedObject(this);
			case ObjectStreamConstants.TC_STRING:
				return new SerializedString(this);
			case ObjectStreamConstants.TC_LONGSTRING:
				return new SerializedString(this, true);
			case ObjectStreamConstants.TC_CLASSDESC:
				return new SerializedClassDesc(this);
			case ObjectStreamConstants.TC_PROXYCLASSDESC:
				throw new FileFormatException("Proxies not supported.");
			case ObjectStreamConstants.TC_NULL:
				return null;
			default:
				throw new MalformedFileException("newObject", "Code: " + type);
		}
	}
}
