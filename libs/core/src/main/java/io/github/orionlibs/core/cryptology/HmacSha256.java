package io.github.orionlibs.core.cryptology;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class HmacSha256
{
    private static final String HMAC_ALGO = "HmacSHA256";


    private HmacSha256()
    {
    }


    /**
     * Computes a Base64‑encoded HMAC‑SHA256 of the given data using the provided key bytes.
     */
    public static String getNewHMACBase64(String data, byte[] key)
    {
        try
        {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            mac.init(new SecretKeySpec(key, HMAC_ALGO));
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        }
        catch(Exception e)
        {
            throw new IllegalStateException("Failed to calculate HMAC‑SHA256", e);
        }
    }
}
