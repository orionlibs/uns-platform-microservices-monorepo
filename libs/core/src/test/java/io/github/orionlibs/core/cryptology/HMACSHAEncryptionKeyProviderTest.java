package io.github.orionlibs.core.cryptology;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class HMACSHAEncryptionKeyProviderTest
{
    @Test
    void testAESEncryptionKey()
    {
        String key = HMACSHAEncryptionKeyProvider.getNewHMACBase64("sometest", SHAEncodingKeyProvider.loadKey());
        assertThat(key.length()).isGreaterThan(10);
    }
}
