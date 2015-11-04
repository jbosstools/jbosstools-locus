/*
* JBoss, Home of Professional Open Source.
*
* See the LICENSE.txt file distributed with this work for information regarding copyright ownership
* and licensing.
*/
package org.bouncycastle.asn1;

/**
 *
 */
public class ASN1Object {

    private byte[] content;

    /**
     * @param content the content
     */
    public ASN1Object(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        if (content == null)
            return new String();

        return new String(encodeHex(content));
    }

    /*
     * ############################################################
     *
     * The following code is taken from org.apache.commons.codec.binary.Hex
     * and licensed as follows:
     *
     * Licensed to the Apache Software Foundation (ASF) under one or more
     * contributor license agreements.  See the NOTICE file distributed with
     * this work for additional information regarding copyright ownership.
     * The ASF licenses this file to You under the Apache License, Version 2.0
     * (the "License"); you may not use this file except in compliance with
     * the License.  You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }
}
