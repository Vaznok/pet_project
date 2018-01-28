package com.epam.rd.november2017.vlasenko.service.encryption;

public interface EncryptionService {

    String encrypt(String unencryptedString);

    String decrypt(String encryptedString);
}
