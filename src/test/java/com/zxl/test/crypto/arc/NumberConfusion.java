package com.zxl.test.crypto.arc;

import org.apache.log4j.Logger;

import com.liepin.common.des.DesPlus;
import com.liepin.common.des.RC4Plus;
import com.liepin.common.des.Vigenere;

public class NumberConfusion {
    private static final Logger logger = Logger.getLogger(NumberConfusion.class);

    private static final char[] LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static final int[][] DIGIT_TABLE = { { 6, 1, 3, 5, 2, 0, 8, 4, 7, 9 }, { 3, 7, 2, 9, 0, 5, 8, 4, 6, 1 },
            { 7, 1, 5, 8, 9, 2, 0, 6, 3, 4 }, { 3, 2, 8, 7, 1, 0, 5, 6, 4, 9 }, { 0, 9, 5, 7, 3, 1, 2, 8, 4, 6 },
            { 6, 4, 0, 7, 9, 3, 8, 5, 1, 2 }, { 6, 3, 7, 4, 1, 8, 0, 2, 9, 5 }, { 0, 1, 3, 2, 7, 9, 6, 8, 5, 4 },
            { 1, 8, 7, 0, 5, 9, 2, 4, 3, 6 }, { 4, 7, 2, 9, 0, 8, 3, 1, 5, 6 }, };

    private static final Vigenere vigenere = Vigenere.getDigitVigenere();

    /**
     * 反混淆方法 -- 抛出异常
     * 
     * @param ciphertext
     * @return
     */
    public static final int decode(String ciphertext) {
        return decodeWithException(ciphertext);
    }

    /**
     * 反混淆方法 -- 不抛出异常
     * <p>
     * 如果简历编号反混淆抛异常，捕获不抛，返回原字符串，并且记日志
     * 
     * @param ciphertext
     * @return
     */
    public static final int decodeWithCompatible(String ciphertext) {
        try {
            return decodeWithException(ciphertext);
        } catch (Exception e) {
            logger.warn(ciphertext + " decode fail", e);
            return Integer.parseInt(ciphertext);
        }
    }

    /**
     * 混淆方法
     * <p>
     * 目前只支持小于1亿的自增长id
     * 
     * @param id
     * @return
     */
    public static final String encode(int id) {
        String planitext = String.valueOf(id);
        // 补齐8位
        planitext = fillup(planitext);

        // 分头尾两部分 保证固定长度
        String head = planitext.substring(0, 4);
        String foot = planitext.substring(4, 8);

        // 数字表置换
        long[] headArray = substitute(head);
        long[] footArray = substitute(foot);

        // 左移合并
        long headLong = encode(headArray);
        long footLong = encode(footArray);

        // 取种子
        int seed = (int) (headLong ^ footLong) % LETTER.length;

        // 维吉尼亚
        return vigenere.encode("" + footLong, "" + headLong) + LETTER[Math.abs(seed)] + footLong;
    }

    /**
     * 反混淆方法
     * 
     * @param ciphertext
     * @return
     * @throws RuntimeException
     */
    private static int decodeWithException(String ciphertext) {
        // 发现种子字母
        char[] charArr = ciphertext.toCharArray();
        int pos = -1;
        char c = (char) -1;
        for (int i = 0; i < charArr.length; i++) {
            if (Character.isLetter(charArr[i])) {
                pos = i;
                c = charArr[i];
                break;
            }
        }
        if (pos == -1 || c == -1) {
            throw new RuntimeException("数据伪造");
        }

        // 校验
        String head = ciphertext.substring(0, pos);
        String foot = ciphertext.substring(pos + 1, ciphertext.length());
        long headLong = Long.parseLong(head);
        long footLong = Long.parseLong(foot);
        // 维吉尼亚
        headLong = Long.parseLong(vigenere.decode(foot, head));
        if (LETTER[Math.abs((int) (headLong ^ footLong) % LETTER.length)] != c) {
            throw new RuntimeException("数据伪造");
        }

        // 右移拆解
        String[] headArray = decode(headLong);
        String[] footArray = decode(footLong);

        // 数字表反置换
        head = unSubstitute(headArray);
        foot = unSubstitute(footArray);

        return Integer.parseInt(head + "" + foot);
    }

    /**
     * 补齐
     * 
     * @param value
     * @return
     */
    private static String fillup(String value) {
        StringBuilder sb = new StringBuilder();
        int i = 8 - value.length();
        for (int j = 0; j < i; j++) {
            sb.append("0");
        }
        sb.append(value);
        return sb.toString();
    }

    /**
     * 头部补零
     * 
     * @param value
     * @return
     */
    private static String fillupZero(String value) {
        int length = value.length();
        switch (length) {
        case 1:
            return "00" + value;
        case 2:
            return "0" + value;
        case 3:
            return value;
        }
        throw new RuntimeException("大于三位");
    }

    /**
     * 置换
     * 
     * @param value
     * @return
     */
    private static long[] substitute(String value) {
        char[] arr = value.toCharArray();
        long[] numArr = new long[4];
        for (int i = 0; i < arr.length; i++) {
            int x = 1;// 占位
            int y = Integer.parseInt("" + arr[i]);
            int z = DIGIT_TABLE[y][(y != 0) ? y - 1 : y];

            numArr[i] = Long.parseLong(x + "" + y + "" + z);
        }
        return numArr;
    }

    /**
     * 反置换
     * 
     * @param arr
     * @return
     */
    private static String unSubstitute(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            char[] _arr = fillupZero(arr[i]).toCharArray();
            @SuppressWarnings("unused")
            int x = -1;
            if (_arr.length == 2) {
                x = 0;
            } else {
                x = Integer.parseInt("" + _arr[0]);
            }

            int y = Integer.parseInt("" + _arr[1]);
            int z = Integer.parseInt("" + _arr[2]);

            if (DIGIT_TABLE[y][(y != 0) ? y - 1 : y] != z) {
                throw new RuntimeException("数据伪造");
            }
            sb.append(y);
        }
        return sb.toString();
    }

    private static long encode(long[] ip) {
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * 长整型转字符串
     * 
     * @param value
     * @return
     */
    private static String[] decode(long value) {
        String[] sb = new String[4];
        // 直接右移24位
        sb[0] = (String.valueOf((value >>> 24)));
        // 将高8位置0，然后右移16位
        sb[1] = (String.valueOf((value & 0x00FFFFFF) >>> 16));
        // 将高16位置0，然后右移8位
        sb[2] = (String.valueOf((value & 0x0000FFFF) >>> 8));
        // 将高24位置0
        sb[3] = (String.valueOf((value & 0x000000FF)));
        return sb;
    }

    private static final Vigenere HEX_VIGENERE = new Vigenere("6493812075ebfcda");
    private static final char[] LETTER_UP = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int MAX_LENGTH = 8;
    /**
     * zookeeper节点名称
     */
    private static final String RESID_NODE_NAME = "security/resId";
    private static final String RES_ID_KEY = "secret_resId_key";
    private static final String HUANXINID_NODE_NAME = "security/huanxinId";
    private static final String HUANXIN_ID_KEY = "secret_huanxinId_key";
    private static volatile String resIdKey = "23we0923";
    private static volatile String huanxinIdKey;
    /**
     * 用户id加密后存cookie名常量
     */
    public static final String USERID_COOKIE_NAME = "__nnn_bad_na_";

    /**
     * 根据userId和密钥对resId进行加密
     * <p>
     * 目前只支持小于1亿的自增长id<br>
     * 加密后的长度固定，17位<br>
     * 
     * @param resId
     * 简历id
     * @param userId
     * 用户id
     * @return
     * @throws Exception
     */
    public static String encodeResId(long resId, long userId) throws Exception {
        // 补齐8位
        String planitext = headFill0(resId, MAX_LENGTH);

        // 分头尾两部分 保证固定长度
        String before = planitext.substring(0, 4);
        String after = planitext.substring(4, 8);

        String key = getKey(userId);
        // 加密
        String _before = RC4Plus.getInstance().encrypt(before, key);
        String _after = RC4Plus.getInstance().encrypt(after, key);

        // 取种子
        int seed = (int) (Integer.parseInt(before) ^ Integer.parseInt(after)) % LETTER_UP.length;

        // 维吉尼亚
        return HEX_VIGENERE.encode(_after, _before) + LETTER_UP[Math.abs(seed)] + _after;
    }

    /**
     * 根据userId和密钥对密文进行解密返回resId
     * <p>
     * 老版本兼容一周，老版本密文长度21位
     * 
     * @param ciphertext
     * 密文
     * @param userId
     * 用户id
     * @return
     * @throws Exception
     */
    public static long decodeResId(String ciphertext, long userId) throws Exception {
        // 兼容模式
        if (ciphertext == null || ciphertext.trim().length() == 0) {
            throw new RuntimeException("密文为空");
        }
        if (ciphertext.length() == 21) {
            logger.warn("兼容老版本混淆简历id, 密文=" + ciphertext + ", 用户id=" + userId);
            return decodeWithException(ciphertext);
        }

        // 发现种子字母
        char[] charArr = ciphertext.toCharArray();
        int pos = -1;
        char c = (char) -1;
        for (int i = 0; i < charArr.length; i++) {
            if (charArr[i] >= 65 && charArr[i] <= 90) {
                pos = i;
                c = charArr[i];
                break;
            }
        }
        if (pos == -1 || c == -1) {
            throw new RuntimeException("简历id解密: 密文=" + ciphertext + ", 用户id=" + userId + " 失败，数据伪造");
        }

        // 校验
        String before = ciphertext.substring(0, pos);
        String after = ciphertext.substring(pos + 1, ciphertext.length());

        // 维吉尼亚
        before = HEX_VIGENERE.decode(after, before);

        String key = getKey(userId);
        // 解密
        String _before = RC4Plus.getInstance().decrypt(before, key);
        String _after = RC4Plus.getInstance().decrypt(after, key);

        char _c = (char) -1;
        try {
            _c = LETTER_UP[Math.abs((int) (Integer.parseInt(_before) ^ Integer.parseInt(_after)) % LETTER_UP.length)];
        } catch (Exception e) {
            throw new RuntimeException("简历id解密: 密文=" + ciphertext + ", 用户id=" + userId + " 失败，数据伪造");
        }

        if (_c != c) {
            throw new RuntimeException("简历id解密: 密文=" + ciphertext + ", 用户id=" + userId + " 失败，数据伪造");
        }

        try {
            return Long.parseLong(_before + "" + _after);
        } catch (Exception e) {
            throw new RuntimeException("简历id解密: 密文=" + ciphertext + ", 用户id=" + userId + " 失败，数据伪造");
        }
    }

    private static String getKey(long userId) {
        return userId + "" + resIdKey;
    }

    private static String headFill0(long value, int length) {
        String tmp = value + "";
        int vlen = String.valueOf(value).length();
        if (vlen < length) {
            int add = length - vlen;
            while (add-- != 0) {
                tmp = "0" + tmp;
            }
        }
        return tmp;
    }

    /**
     * 针对user_id进行加密
     * 
     * @deprecated As of version 1.4.16, {@link #encodeHuanxinId(long)} instead
     * @param userId
     * @return
     */
    @Deprecated
    public static String encodeUserId(long userId) {
        return DesPlus.getInstance().encrypt(userId + "", huanxinIdKey);
    }

    /**
     * 对user_id密文进行解密
     * 
     * @deprecated As of version 1.4.16, {@link #decodeHuanxinId(String)}
     * instead
     * @param ciphertext
     * @return
     */
    @Deprecated
    public static long decodeUserId(String ciphertext) {
        String decrypt = DesPlus.getInstance().decrypt(ciphertext, huanxinIdKey);
        try {
            return Long.parseLong(decrypt);
        } catch (Exception e) {
            throw new RuntimeException("用户id解密: 密文=" + ciphertext + " 失败，数据伪造");
        }
    }

    /**
     * 针对环信id进行加密
     * 
     * @param huanxinId
     * @return
     */
    public static String encodeHuanxinId(long huanxinId) {
        return DesPlus.getInstance().encrypt(huanxinId + "", huanxinIdKey);
    }

    /**
     * 对环信id密文进行解密
     * 
     * @param ciphertext
     * @return
     */
    public static long decodeHuanxinId(String ciphertext) {
        String decrypt = DesPlus.getInstance().decrypt(ciphertext, huanxinIdKey);
        try {
            return Long.parseLong(decrypt);
        } catch (Exception e) {
            throw new RuntimeException("环信id解密: 密文=" + ciphertext + " 失败，数据伪造");
        }
    }

    public static void main(String[] arg) {
        try {
            String encrypt = encodeResId(2222222L, 333333L);
            System.out.println(encrypt);
            long decrypt = decodeResId(encrypt, 333333L);
            System.out.println(decrypt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    };

}
