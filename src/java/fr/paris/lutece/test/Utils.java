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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.business.right.Right;
import fr.paris.lutece.portal.business.user.AdminUser;

/**
 * Utils for tests
 */
public class Utils
{
    private static Utils _singleton = new Utils( );

    private static final String ATTRIBUTE_ADMIN_USER = "lutece_admin_user";

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
}
