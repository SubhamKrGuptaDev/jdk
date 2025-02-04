/*
 * Copyright (c) 2016, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sax;

import static jaxp.library.JAXPTestUtilities.clearSystemProperty;
import static jaxp.library.JAXPTestUtilities.setSystemProperty;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;
import org.xml.sax.helpers.XMLReaderFactory;

/*
 * @test
 * @bug 8158246 8316383
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm sax.XMLReaderTest
 * @summary This class contains tests that cover the creation of XMLReader.
 */
public class XMLReaderTest {
    private final String SAX_PROPNAME = "org.xml.sax.driver";

    /*
     * Clean up after test
     */
    @AfterClass
    public void cleanUp() throws Exception {
        clearSystemProperty(SAX_PROPNAME);
    }

    /*
     * @bug 8158246
     * Verifies that SAXException is reported when the classname specified can
     * not be found.
     *
     * Except test format, this test is the same as JCK's test Ctor003.
     */
    @Test(expectedExceptions = SAXException.class)
    public void testcreateXMLReader() throws SAXException, ParserConfigurationException {
        String className = SAXParserFactory.newInstance().newSAXParser()
                            .getXMLReader().getClass().getName();
        setSystemProperty(SAX_PROPNAME, className + "nosuch");
        XMLReaderAdapter adapter = new XMLReaderAdapter();
    }

    /*
     * @bug 8316383
     * Verifies that the XMLReader is initialized properly when it's created
     * with XMLReaderFactory.
     */
    @Test
    public void testCreateXMLReaderWithXMLReaderFactory() throws SAXException, ParserConfigurationException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
    }
}
