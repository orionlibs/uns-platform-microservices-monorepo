package io.github.orionlibs.user.model;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptionKeyProvider
{
    private static final byte[] DUMMY_KEY_BYTES = "0123456789ABCDEF0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);


    // 32 bytes → AES‑256
    public static SecretKey loadDataEncryptionKey()
    {
        // TODO: fetch the wrapped key from Vault/KMS, unwrap it, and return a SecretKey
        return new SecretKeySpec(DUMMY_KEY_BYTES, "AES");
    }


    private EncryptionKeyProvider()
    {
    }
}
