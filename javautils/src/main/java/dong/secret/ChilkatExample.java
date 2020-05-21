package dong.secret;

import com.chilkatsoft.CkCrypt2;
import com.chilkatsoft.CkPublicKey;
import com.chilkatsoft.CkRsa;
import com.chilkatsoft.CkStringBuilder;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/2/26 18:21
 **/
public class ChilkatExample {
    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        {
            //  Note: This example requires a feature introduced in Chilkat v9.5.0.66

            //  This example requires the Chilkat API to have been previously unlocked.
            //  See Global Unlock Sample for sample code.

            CkPublicKey pubkey = new CkPublicKey();

            CkStringBuilder sbPem = new CkStringBuilder();
            boolean bCrlf = true;
            sbPem.AppendLine("-----BEGIN PUBLIC KEY-----", bCrlf);
            sbPem.AppendLine("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA33TqqLR3eeUmDtHS89qF", bCrlf);
            sbPem.AppendLine("3p4MP7Wfqt2Zjj3lZjLjjCGDvwr9cJNlNDiuKboODgUiT4ZdPWbOiMAfDcDzlOxA", bCrlf);
            sbPem.AppendLine("04DDnEFGAf+kDQiNSe2ZtqC7bnIc8+KSG/qOGQIVaay4Ucr6ovDkykO5Hxn7OU7s", bCrlf);
            sbPem.AppendLine("Jp9TP9H0JH8zMQA6YzijYH9LsupTerrY3U6zyihVEDXXOv08vBHk50BMFJbE9iwF", bCrlf);
            sbPem.AppendLine("wnxCsU5+UZUZYw87Uu0n4LPFS9BT8tUIvAfnRXIEWCha3KbFWmdZQZlyrFw0buUE", bCrlf);
            sbPem.AppendLine("f0YN3/Q0auBkdbDR/ES2PbgKTJdkjc/rEeM0TxvOUf7HuUNOhrtAVEN1D5uuxE1W", bCrlf);
            sbPem.AppendLine("SwIDAQAB", bCrlf);
            sbPem.AppendLine("-----END PUBLIC KEY-----", bCrlf);

            //  Load the public key object from the PEM.
            boolean success = pubkey.LoadFromString(sbPem.getAsString());
            if (success != true) {
                System.out.println(pubkey.lastErrorText());
                return;
            }

            String originalData = "This is the original data to be SHA-256 hashed and RSA encrypted.";

            //  First we SHA-256 hash the original data to get the hash in base64 format:
            CkCrypt2 crypt = new CkCrypt2();
            crypt.put_HashAlgorithm("SHA-256");
            crypt.put_EncodingMode("base64");
            String hashBase64 = crypt.hashStringENC(originalData);

            //  Setup RSA to use OAEP padding with SHA-1 for the mask function.
            CkRsa rsa = new CkRsa();
            rsa.put_OaepPadding(true);
            rsa.put_OaepHash("SHA1");
            rsa.ImportPublicKeyObj(pubkey);
            rsa.put_EncodingMode("base64");

            //  Starting in v9.5.0.66, we can provide a binary encoding mode, such as "base64", "hex", "base64url", etc.
            //  for the Charset property.   The Charset property was previously limited to character encodings, such as
            //  "utf-8", "iso-8859-1", etc.  If a binary encoding is used, then the string passed in is decoded to the binary
            //  bytes as indicated.  (If an actual charset, such as "utf-8" is used, then the input string is converted to the
            //  byte representation of the charset, and then encrypted.)

            //  Given that a hash is composed of non-text binary bytes, we'll set the Charset property equal to "base64"
            //  (because we have the base64 hash from above).
            rsa.put_Charset("base64");

            //  Note: The OAEP padding uses random bytes in the padding, and therefore each time encryption happens,
            //  even using the same data and key, the result will be different --  but still valid.  One should not expect
            //  to get the same output.
            boolean bUsePrivateKey = false;
            String encryptedStr = rsa.encryptStringENC(hashBase64, bUsePrivateKey);
            if (rsa.get_LastMethodSuccess() != true) {
                System.out.println(rsa.lastErrorText());
                return;
            }

            System.out.println("Base64 RSA encrypted output: " + encryptedStr);
        }
    }
}
