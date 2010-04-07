package net.djpowell.nioimage;

import java.awt.image.DataBuffer;
import java.nio.ByteBuffer;

/**
 * An AWT data buffer that is backed by a NIO ByteBuffer.
 *
 * @author David Powell
 * @since 22-Mar-2010
 */
public class NioDataBuffer extends DataBuffer {

    private final ByteBuffer bb;
    private final int headerLen;

    /**
     * Construct an AWT data buffer that is backed by a NIO ByteBuffer.
     *
     * @param bb NIO ByteBuffer
     * @param headerLen number of bytes to skip in the ByteBuffer before writing the image data.         
     */
    public NioDataBuffer(ByteBuffer bb, int headerLen) {
        super(DataBuffer.TYPE_BYTE, bb.capacity());
        this.bb = bb;
        this.headerLen = headerLen;
    }

    /**
     * Retrieves the NIO ByteBuffer that backs this DataBuffer.
     * 
     * @return NIO ByteBuffer
     */
    public ByteBuffer getByteBuffer() {
        return bb;
    }

    @Override
    public int getElem(int bank, int i) {
        return bb.get(i + headerLen) & 0xff;
    }

    @Override
    public void setElem(int bank, int i, int val) {
        bb.put(i + headerLen, (byte)(val & 0xff));
    }
}
