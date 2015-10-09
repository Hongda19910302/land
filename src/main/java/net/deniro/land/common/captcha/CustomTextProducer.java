package net.deniro.land.common.captcha;

import nl.captcha.text.producer.TextProducer;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 自定义字符生成器
 *
 * @author deniro
 *         2015/10/9
 */
public class CustomTextProducer implements TextProducer {
    private static final Random RAND = new SecureRandom();
    /**
     * 默认长度
     */
    private static final int DEFAULT_LENGTH = 4;
    /**
     * 字符集
     */
    private static final char[] DEFAULT_CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y', '2', '3', '4', '5', '6', '7', '8'};
    private final int _length;
    private final char[] _srcChars;

    public CustomTextProducer() {
        this(DEFAULT_LENGTH, DEFAULT_CHARS);
    }

    public CustomTextProducer(int length, char[] srcChars) {
        this._length = length;
        this._srcChars = copyOf(srcChars, srcChars.length);
    }

    public String getText() {
        String capText = "";

        for (int i = 0; i < this._length; ++i) {
            capText = capText + this._srcChars[RAND.nextInt(this._srcChars.length)];
        }

        return capText;
    }

    private static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
