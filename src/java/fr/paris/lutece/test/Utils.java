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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Utils for tests
 */
public class Utils
{
    private static Utils _singleton = new Utils( );

    private static final Random _rand = new SecureRandom( );

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
