package io.github.orionlibs.core.cryptology;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AESEncryptionKeyProviderTest
{
    @Test
    void testAESEncryptionKey()
    {
        assertThat(AESEncryptionKeyProvider.loadDataEncryptionKey()).isNotNull();
    }
}
