/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lixiaomi.baselibapplication.utils.aes_rsa_utils;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;


/**
 * Abstract superclass for Base-N encoders and decoders.
 *
 * <p>
 * This class is thread-safe.
 * </p>
 *
 * @version $Id: BaseNCodec.java 1811344 2017-10-06 15:19:57Z ggregory $
 */
public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {

    static final int EOF = -1;
    public static final int MIME_CHUNK_SIZE = 76;
    public static final int PEM_CHUNK_SIZE = 64;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 128;
    protected static final int MASK_8BITS = 255;
    protected static final byte PAD_DEFAULT = 61;
    protected final byte pad;
    private final int unencodedBlockSize;
    private final int encodedBlockSize;
    protected final int lineLength;
    private final int chunkSeparatorLength;

    protected BaseNCodec(int unencodedBlockSize, int encodedBlockSize, int lineLength, int chunkSeparatorLength) {
        this(unencodedBlockSize, encodedBlockSize, lineLength, chunkSeparatorLength, (byte)61);
    }

    protected BaseNCodec(int unencodedBlockSize, int encodedBlockSize, int lineLength, int chunkSeparatorLength, byte pad) {
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        boolean useChunking = lineLength > 0 && chunkSeparatorLength > 0;
        this.lineLength = useChunking ? lineLength / encodedBlockSize * encodedBlockSize : 0;
        this.chunkSeparatorLength = chunkSeparatorLength;
        this.pad = pad;
    }

    boolean hasData(BaseNCodec.Context context) {
        return context.buffer != null;
    }

    int available(BaseNCodec.Context context) {
        return context.buffer != null ? context.pos - context.readPos : 0;
    }

    protected int getDefaultBufferSize() {
        return 128;
    }

    private byte[] resizeBuffer(BaseNCodec.Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[this.getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        } else {
            byte[] b = new byte[context.buffer.length * 2];
            System.arraycopy(context.buffer, 0, b, 0, context.buffer.length);
            context.buffer = b;
        }

        return context.buffer;
    }

    protected byte[] ensureBufferSize(int size, BaseNCodec.Context context) {
        return context.buffer != null && context.buffer.length >= context.pos + size ? context.buffer : this.resizeBuffer(context);
    }

    int readResults(byte[] b, int bPos, int bAvail, BaseNCodec.Context context) {
        if (context.buffer != null) {
            int len = Math.min(this.available(context), bAvail);
            System.arraycopy(context.buffer, context.readPos, b, bPos, len);
            context.readPos += len;
            if (context.readPos >= context.pos) {
                context.buffer = null;
            }

            return len;
        } else {
            return context.eof ? -1 : 0;
        }
    }

    protected static boolean isWhiteSpace(byte byteToCheck) {
        switch(byteToCheck) {
            case 9:
            case 10:
            case 13:
            case 32:
                return true;
            default:
                return false;
        }
    }

    /** @deprecated */
    @Override
    @Deprecated
    public Object encode(Object obj) throws EncoderException {
        if (!(obj instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        } else {
            return this.encode((byte[])((byte[])obj));
        }
    }

    public String encodeToString(byte[] pArray) {
        return StringUtils.newStringUtf8(this.encode(pArray));
    }

    public String encodeAsString(byte[] pArray) {
        return StringUtils.newStringUtf8(this.encode(pArray));
    }

    /** @deprecated */
    @Override
    @Deprecated
    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return this.decode((byte[])((byte[])obj));
        } else if (obj instanceof String) {
            return this.decode((String)obj);
        } else {
            throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
        }
    }

    public byte[] decode(String pArray) {
        return this.decode(StringUtils.getBytesUtf8(pArray));
    }

    @Override
    public byte[] decode(byte[] pArray) {
        return this.decode(pArray, 0, pArray.length);
    }

    public byte[] decode(byte[] pArray, int off, int len) {
        if (pArray != null && len != 0) {
            BaseNCodec.Context context = new BaseNCodec.Context();
            this.decode(pArray, off, len, context);
            this.decode(pArray, off, -1, context);
            byte[] result = new byte[context.pos];
            this.readResults(result, 0, result.length, context);
            return result;
        } else {
            return new byte[0];
        }
    }

    @Override
    public byte[] encode(byte[] pArray) {
        return pArray != null && pArray.length != 0 ? this.encode(pArray, 0, pArray.length) : pArray;
    }

    public byte[] encode(byte[] pArray, int offset, int length) {
        if (pArray != null && pArray.length != 0) {
            BaseNCodec.Context context = new BaseNCodec.Context();
            this.encode(pArray, offset, length, context);
            this.encode(pArray, offset, -1, context);
            byte[] buf = new byte[context.pos - context.readPos];
            this.readResults(buf, 0, buf.length, context);
            return buf;
        } else {
            return pArray;
        }
    }

    abstract void encode(byte[] var1, int var2, int var3, BaseNCodec.Context var4);

    abstract void decode(byte[] var1, int var2, int var3, BaseNCodec.Context var4);

    protected abstract boolean isInAlphabet(byte var1);

    public boolean isInAlphabet(byte[] arrayOctet, boolean allowWSPad) {
        byte[] arr$ = arrayOctet;
        int len$ = arrayOctet.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            byte octet = arr$[i$];
            if (!this.isInAlphabet(octet) && (!allowWSPad || octet != this.pad && !isWhiteSpace(octet))) {
                return false;
            }
        }

        return true;
    }

    public boolean isInAlphabet(String basen) {
        return this.isInAlphabet(StringUtils.getBytesUtf8(basen), true);
    }

    protected boolean containsAlphabetOrPad(byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        } else {
            byte[] arr$ = arrayOctet;
            int len$ = arrayOctet.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                byte element = arr$[i$];
                if (this.pad == element || this.isInAlphabet(element)) {
                    return true;
                }
            }

            return false;
        }
    }

    public long getEncodedLength(byte[] pArray) {
        long len = (long)((pArray.length + this.unencodedBlockSize - 1) / this.unencodedBlockSize) * (long)this.encodedBlockSize;
        if (this.lineLength > 0) {
            len += (len + (long)this.lineLength - 1L) / (long)this.lineLength * (long)this.chunkSeparatorLength;
        }

        return len;
    }

    static class Context {
        int ibitWorkArea;
        byte[] buffer;
        int pos;
        int readPos;
        boolean eof;
        int currentLinePos;
        int modulus;

        Context() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", this.getClass().getSimpleName(), HexUtils.toHexString(this.buffer), this.currentLinePos, this.eof, this.ibitWorkArea, this.modulus, this.pos, this.readPos);
        }
    }
}
