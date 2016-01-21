package ninja.invisiblecode.textify.javaserial.sample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import ninja.invisiblecode.textify.javaserial.SerializedReader;

public class Test {
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(o);
		out.writeObject(new SerializableCat());
		out.flush();
		ByteArrayInputStream i = new ByteArrayInputStream(o.toByteArray());
		new SerializedReader(i).readFile();
	}
}
