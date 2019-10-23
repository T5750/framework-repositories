package t5750.dubbox.util;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 */
public class SerializationUtil {
	/**
	 * 序列化方法
	 */
	public static void writeObject(Serializable s,String filename){
		try {
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(s);
			oos.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


}
