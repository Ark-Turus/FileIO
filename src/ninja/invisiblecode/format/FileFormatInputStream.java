package ninja.invisiblecode.format;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileFormatInputStream {

	private InputStream source;

	private int bytesRead = 0;

	public FileFormatInputStream(InputStream in) throws FileNotFoundException {
		source = in;
	}

	public FileFormatInputStream(File file) throws FileNotFoundException {
		source = new FileInputStream(file);
	}

	public int available() throws IOException {
		return source.available();
	}

	public int bytesRead() {
		int read = bytesRead;
		bytesRead = 0;
		return read;
	}

	public byte[] readByteSequence(byte[] buffer) throws IOException {
		int i = 0;
		while (i < buffer.length)
			i += source.read(buffer, i, buffer.length - i);
		bytesRead += i;
		return buffer;
	}

	public boolean validateByteSequence(byte[] sequence) throws IOException {
		byte[] val = readByteSequence(new byte[sequence.length]);
		// System.out.println(val[0]);
		for (int i = 0; i < sequence.length; i++)
			if (val[i] != sequence[i]) {
				System.out.println(val[i] + " " + sequence[i]);
				return false;
			}
		return true;
	}

	public byte readByte() throws IOException {
		int val = source.read();
		if (val == -1)
			throw new EOFException();
		bytesRead++;
		return (byte) val;
	}

	public boolean validateByte(byte b) throws IOException {
		return b == readByte();
	}

	public short readShort() throws IOException {
		byte[] data = readByteSequence(new byte[2]);
		return (short) (((data[0] & 0xFF) << 8) + (data[1] & 0xFF));
	}

	public boolean validateShort(short s) throws IOException {
		return s == readShort();
	}

	public int readInt() throws IOException {
		byte[] data = readByteSequence(new byte[4]);
		return ((data[0] & 0xFF) << 24) + ((data[1] & 0xFF) << 16) + ((data[2] & 0xFF) << 8) + (data[3] & 0xFF);
	}

	public boolean validateInt(int i) throws IOException {
		return i == readInt();
	}

	public long readLong() throws IOException {
		byte[] data = readByteSequence(new byte[8]);
		long l = data[0];
		for (int i = 1; i < 8; i++)
			l = (l << 8) | (data[i] & 0xFF);
		return l;
	}

	public boolean validateLong(long l) throws IOException {
		return l == readLong();
	}

	public String readPascal() throws IOException {
		return new String(readByteSequence(new byte[readByte()]));
	}

	public String readShortPascal() throws IOException {
		return new String(readByteSequence(new byte[readShort()]));
	}

	public String readIntPascal() throws IOException {
		return new String(readByteSequence(new byte[readInt()]));
	}

}
