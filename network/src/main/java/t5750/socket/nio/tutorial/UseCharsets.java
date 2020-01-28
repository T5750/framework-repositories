package t5750.socket.nio.tutorial;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import t5750.socket.util.SocketUtil;

public class UseCharsets {
	static public void main(String args[]) throws Exception {
		String inputFile = SocketUtil.NIO_PATH + "readandshow.txt";
		String outputFile = SocketUtil.NIO_PATH + "sampleout.txt";
		RandomAccessFile inf = new RandomAccessFile(inputFile, "r");
		RandomAccessFile outf = new RandomAccessFile(outputFile, "rw");
		long inputLength = new File(inputFile).length();
		FileChannel inc = inf.getChannel();
		FileChannel outc = outf.getChannel();
		MappedByteBuffer inputData = inc.map(FileChannel.MapMode.READ_ONLY, 0,
				inputLength);
		Charset latin1 = Charset.forName("ISO-8859-1");
		CharsetDecoder decoder = latin1.newDecoder();
		CharsetEncoder encoder = latin1.newEncoder();
		CharBuffer cb = decoder.decode(inputData);
		// Process char data here
		ByteBuffer outputData = encoder.encode(cb);
		outc.write(outputData);
		inf.close();
		outf.close();
	}
}
