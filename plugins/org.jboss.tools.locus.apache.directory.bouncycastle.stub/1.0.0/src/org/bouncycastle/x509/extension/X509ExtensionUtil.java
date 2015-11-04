/*
* JBoss, Home of Professional Open Source.
*
* See the LICENSE.txt file distributed with this work for information regarding copyright ownership
* and licensing.
*/
package org.bouncycastle.x509.extension;

import org.bouncycastle.asn1.ASN1Object;

/**
 *
 */
public class X509ExtensionUtil {

    /**
     * @param content the content
     * @return the asn1 object for the content
     */
    public static ASN1Object fromExtensionValue(byte[] content) {
        return new ASN1Object(content);
    }
}
