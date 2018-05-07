package org.dora.jdbc.grammar.unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * Created by SDE on 2018/5/7.
 */
public final class Lock {
    private static final Unsafe unsafe;
    private final long valueOffset;

    private Lock(long valueOffset) {
        this.valueOffset = valueOffset;
    }

    static {
        try {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe)field.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public Lock create(Class clazz, String field) throws NoSuchFieldException {
        long offset = unsafe.objectFieldOffset(clazz.getDeclaredField(field));
        return new Lock(offset);
    }

    public boolean lock(Object owner, int expect, int update) {
        return unsafe.compareAndSwapInt(owner, valueOffset, expect, update);
    }

}
