package org.masonio.enthrallment.enthrallments;

import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * A custom data type to create persistent metadata keys for the specific enthrallment on an item
 */
public class EnthrallmentDataType implements PersistentDataType<byte[], Enthrallment> {
    @Override
    public @NotNull Class getPrimitiveType() {
        return Enthrallment.class;
    }

    @Override
    public @NotNull Class getComplexType() {
        return byte[].class;
    }

    @Override
    public @NotNull byte @NotNull [] toPrimitive(@NotNull Enthrallment complex, @NotNull PersistentDataAdapterContext context) {
        return SerializationUtils.serialize(complex);
    }

    @Override
    public @NotNull Enthrallment fromPrimitive(@NotNull byte[] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            InputStream inputStream = new ByteArrayInputStream(primitive);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (Enthrallment) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
