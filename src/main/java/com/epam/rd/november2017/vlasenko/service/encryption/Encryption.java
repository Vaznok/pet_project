package com.epam.rd.november2017.vlasenko.service.encryption;

public interface Encryption {

    String encrypt(String unencryptedString);

    String decrypt(String encryptedString);
}
