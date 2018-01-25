package com.epam.rd.november2017.vlasenko.service.encryption;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncryptionServiceTest {
    private EncryptionService encryption = new EncryptionService();

    @Test
    public void encryptStringAndThenDecrypt_initialStringEqualsDecrypted() {
        String initStr = "vetall5@mail.ru";

        String encryptedStr = encryption.encrypt(initStr);

        assertEquals(encryption.decrypt(encryptedStr), initStr);
    }
}
