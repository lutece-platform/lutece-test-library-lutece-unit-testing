/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.SAXException;

import fr.paris.lutece.portal.business.right.Right;
import fr.paris.lutece.portal.business.user.AdminUser;
import nu.validator.client.EmbeddedValidator;

/**
 * Utils for tests
 */
public class Utils
{
    private static Utils _singleton = new Utils( );

    private static final String ATTRIBUTE_ADMIN_USER = "lutece_admin_user";

    private static final Random _rand = new SecureRandom( );

    /**
     * Register an admin user with a given right
     * @param request the request to register the user into
     * @param user The user
     * @param strRight The right
     */
    public static void registerAdminUserWithRigth( HttpServletRequest request, AdminUser user, String strRight )
    {
        Map<String, Right> mapRights = new HashMap<String, Right>( );
        Right right = new Right( );
        right.setId( strRight );
        mapRights.put( strRight, right );
        user.setRights( mapRights );

        // TODO set locale user
        user.setLocale( new Locale( "fr", "FR", "" ) );
        registerAdminUser( request, user );
    }
    
    /**
     * Register an admin user
     * @param request the request to register the user into
     * @param user The user
     */
    public static void registerAdminUser( HttpServletRequest request, AdminUser user )
    {
        request.getSession( true ).setAttribute( ATTRIBUTE_ADMIN_USER, user );
    }
    
    /**
     * Gets the content of a file as a string
     * 
     * @param strFilename
     *            The filename (ie: myfile.text for file stored in src/test/resources)
     * @return The file's content
     * @throws IOException
     *             If an IO error occurs
     */
    public static String getFileContent( String strFilename ) throws IOException
    {
        InputStream is = _singleton.getClass( ).getResourceAsStream( "/" + strFilename );
        InputStreamReader isr = new InputStreamReader( is );
        BufferedReader in = new BufferedReader( isr );
        Writer writer = new StringWriter( );

        if ( in != null )
        {
            char [ ] buffer = new char [ 1024];

            try
            {
                int n;

                while ( ( n = in.read( buffer ) ) != -1 )
                {
                    writer.write( buffer, 0, n );
                }
            }
            finally
            {
                isr.close( );
            }

            return writer.toString( );
        }
        else
        {
            return "";
        }
    }

    /**
     * Validate an html fragment, failing on warning
     * 
     * @param html
     *            the fragment to validate
     * @throws IOException
     *             in case of error
     * @throws SAXException
     *             in case of error
     * @throws AssertionError
     *             if the fragment is invalid or produces warnings
     */
    public static void validateHtmlFragment( String html ) throws IOException, SAXException
    {
        validateHtml( "<!DOCTYPE html><title>junit</title>" + html, true );
    }

    /**
     * Validate an html fragment
     * 
     * @param html
     *            the fragment to validate
     * @param failOnWarning
     *            whether to fail on warning
     * @throws IOException
     *             in case of error
     * @throws SAXException
     *             in case of error
     * @throws AssertionError
     *             if the fragment is invalid or produces warnings
     */
    public static void validateHtmlFragment( String html, boolean failOnWarning ) throws IOException, SAXException
    {
        validateHtml( "<!DOCTYPE html><title>junit</title>" + html, failOnWarning );
    }

    /**
     * Validate an html document
     * 
     * @param html
     *            the document to validate
     * @throws IOException
     *             in case of error
     * @throws SAXException
     *             in case of error
     * @throws AssertionError
     *             if the document is invalid or produces warnings
     */
    public static void validateHtml( String html ) throws IOException, SAXException
    {
        validateHtml( html, true );
    }

    /**
     * Validate an html document
     * 
     * @param html
     *            the document to validate
     * @param failOnWarning
     *            whether to fail on warning
     * @throws IOException
     *             in case of error
     * @throws SAXException
     *             in case of error
     * @throws AssertionError
     *             if the document is invalid or produces warnings
     */
    public static void validateHtml( String html, boolean failOnWarning ) throws IOException, SAXException
    {
        EmbeddedValidator validator = new EmbeddedValidator( );
        String res = validator.validate( new ByteArrayInputStream( html.getBytes( StandardCharsets.UTF_8 ) ) );
        ObjectMapper mapper = new ObjectMapper( );
        JsonNode node = mapper.readTree( res );
        JsonNode messages = node.get( "messages" );
        if ( messages != null )
        {
            messages.forEach( message -> {
                JsonNode type = message.get( "type" );
                if ( type != null )
                {
                    switch ( type.asText( ) )
                    {
                    case "error":
                        fail( "Invalid HTML : " + message.toString( ) );
                        break;
                    case "info":
                        JsonNode subtype = message.get( "subType" );
                        if ( subtype != null && subtype.asText( ).equals( "warning" ) )
                        {
                            if ( failOnWarning )
                            {
                                fail( "HTML with warning : " + message.toString( ) );
                            }
                        }
                    default:
                        break;
                    }

                }
            } );
        }
    }

    /**
     * Get a random name for use in tests
     * 
     * @return a random name with prefix <q>junit</q> and 128 bits of randomness
     */
    public static String getRandomName( )
    {
        return getRandomName( "junit", 128 );
    }

    /**
     * Get a random name for use in tests
     * 
     * @param strPrefix
     *            the prefix to use
     * @return a random name with the specified prefix and 128 bits of
     *         randomness
     */
    public static String getRandomName( String strPrefix )
    {
        return getRandomName( strPrefix, 128 );
    }

    /**
     * Get a random name for use in tests
     * 
     * @param strPrefix
     *            the prefix to use
     * @param nRandomBits
     *            the numnber of bits of randomness to use
     * @return a random name with the specified prefix and the specified number
     *         of bits of randomness
     */
    public static String getRandomName( String strPrefix, int nRandomBits )
    {
        BigInteger bigInt = new BigInteger( nRandomBits, _rand );
        return strPrefix + bigInt.toString( 36 );
    }
}
