package io.github.orionlibs.core.cryptology;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class SHAEncodingKeyProviderTest
{
    @Test
    void testSHAEncodingKey()
    {
        byte[] key = SHAEncodingKeyProvider.loadKey();
        assertThat(key).isNotNull();
        assertThat(key.length).isEqualTo(32);
    }
}
