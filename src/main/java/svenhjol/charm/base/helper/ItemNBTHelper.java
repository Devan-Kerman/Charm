package svenhjol.charm.base.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.UUID;

@SuppressWarnings("unused")
public class ItemNBTHelper {
    public static int getInt(ItemStack stack, String tag, int defaultExpected) {
        return tagExists(stack, tag) ? getNBT(stack).getInt(tag) : defaultExpected;
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return tagExists(stack, tag) ? getNBT(stack).getBoolean(tag) : defaultExpected;
    }

    public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
        return tagExists(stack, tag) ? getNBT(stack).getDouble(tag) : defaultExpected;
    }

    public static long getLong(ItemStack stack, String tag, long defaultExpected) {
        return tagExists(stack, tag) ? getNBT(stack).getLong(tag) : defaultExpected;
    }

    public static String getString(ItemStack stack, String tag, String defaultExpected) {
        return tagExists(stack, tag) ? getNBT(stack).getString(tag) : defaultExpected;
    }

    public static NbtCompound getCompound(ItemStack stack, String tag) {
        return getCompound(stack, tag, false);
    }

    public static NbtCompound getCompound(ItemStack stack, String tag, boolean nullify) {
        return tagExists(stack, tag) ? getNBT(stack).getCompound(tag) : (nullify ? null : new NbtCompound());
    }

    public static UUID getUuid(ItemStack stack, String tag) {
        NbtCompound nbt = getNBT(stack);
        return nbt.containsUuid(tag) ? nbt.getUuid(tag) : null;
    }

    public static NbtList getList(ItemStack stack, String tag) {
        return tagExists(stack, tag) ? getNBT(stack).getList(tag, 10) : new NbtList();
    }

    public static void setInt(ItemStack stack, String tag, int i) {
        getNBT(stack).putInt(tag, i);
    }

    public static void setBoolean(ItemStack stack, String tag, boolean b) {
        getNBT(stack).putBoolean(tag, b);
    }

    public static void setCompound(ItemStack stack, String tag, NbtCompound cmp) {
        getNBT(stack).put(tag, cmp);
    }

    public static void setDouble(ItemStack stack, String tag, double d) {
        getNBT(stack).putDouble(tag, d);
    }

    public static void setLong(ItemStack stack, String tag, long l) {
        getNBT(stack).putLong(tag, l);
    }

    public static void setString(ItemStack stack, String tag, String s) {
        getNBT(stack).putString(tag, s);
    }

    public static void setUuid(ItemStack stack, String tag, UUID uuid) {
        getNBT(stack).putUuid(tag, uuid);
    }

    public static void setList(ItemStack stack, String tag, NbtList list) {
        getNBT(stack).put(tag, list);
    }


    public static boolean tagExists(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.hasTag() && getNBT(stack).contains(tag);
    }

    public static NbtCompound getNBT(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new NbtCompound());
        }
        return stack.getTag();
    }
}
