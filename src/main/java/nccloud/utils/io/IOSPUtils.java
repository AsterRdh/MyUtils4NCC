package nccloud.utils.io;

import java.io.Closeable;
import java.io.IOException;

public class IOSPUtils {
    public static void closeAll(Closeable ... streams){
        if (streams!=null){
            for (Closeable stream : streams) {
                try {
                    if (stream!=null){
                        stream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
