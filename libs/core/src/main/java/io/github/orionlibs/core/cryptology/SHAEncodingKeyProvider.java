package io.github.orionlibs.core.cryptology;

import java.nio.charset.StandardCharsets;

public final class SHAEncodingKeyProvider
{
    private static final byte[] DUMMY_KEY_BYTES_FOR_SHA_256 = "0123456789ABCDEF0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);


    public static byte[] loadKey()
    {
        // TODO: fetch the wrapped key from Vault/KMS, unwrap it, and return a byte[]
        return DUMMY_KEY_BYTES_FOR_SHA_256;
    }


    private SHAEncodingKeyProvider()
    {
    }
}
