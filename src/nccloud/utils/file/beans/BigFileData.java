package nccloud.utils.file.beans;

import java.io.ByteArrayInputStream;

public class BigFileData {
    private ByteArrayInputStream stream;
    private long size;

    public BigFileData(ByteArrayInputStream stream, long size) {
        this.stream = stream;
        this.size = size;
    }

    public ByteArrayInputStream getStream() {
        return stream;
    }

    public long getSize() {
        return size;
    }
}
