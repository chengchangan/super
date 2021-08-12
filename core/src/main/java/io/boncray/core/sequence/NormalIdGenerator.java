package io.boncray.core.sequence;

import java.util.UUID;

public class NormalIdGenerator implements IdGenerator{

    public static String[] chars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * 存在重复
     * 经过测试 100万，大概重复50个
     */
    @Override
    public long next() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 10; i++) {
            String str = uuid.substring(i * 3, i * 3 + 3);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 10]);
        }
        return Long.parseLong(shortBuffer.toString());
    }

}
