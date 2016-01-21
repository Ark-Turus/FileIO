package ninja.invisiblecode.textify.javaserial;

public enum SerializedFieldType {

	BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR, OBJECT, ARRAY;

	public Object read(){
		return null;
	}
	
	public static SerializedFieldType get(byte b) {
		switch (b) {
			case 'B':
				return SerializedFieldType.BYTE;
			case 'S':
				return SerializedFieldType.SHORT;
			case 'I':
				return SerializedFieldType.INT;
			case 'J':
				return SerializedFieldType.LONG;
			case 'F':
				return SerializedFieldType.FLOAT;
			case 'D':
				return SerializedFieldType.DOUBLE;
			case 'C':
				return SerializedFieldType.CHAR;
			case 'Z':
				return SerializedFieldType.BOOLEAN;
			case 'L':
				return SerializedFieldType.OBJECT;
			case '[':
				return SerializedFieldType.ARRAY;
			default:
				return null;
		}
	}
	
	public String toString(){
		return name().toLowerCase();
	}

}
