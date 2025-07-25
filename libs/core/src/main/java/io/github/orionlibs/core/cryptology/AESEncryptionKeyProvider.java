package io.github.orionlibs.core.cryptology;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AESEncryptionKeyProvider
{
    private static final byte[] DUMMY_KEY_BYTES_FOR_AES_256 = "0123456789ABCDEF0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);


    // 32 bytes → AES‑256
    public static SecretKey loadDataEncryptionKey()
    {
        // TODO: fetch the wrapped key from Vault/KMS, unwrap it, and return a SecretKey
        return new SecretKeySpec(DUMMY_KEY_BYTES_FOR_AES_256, "AES");
    }


    private AESEncryptionKeyProvider()
    {
    }
}
