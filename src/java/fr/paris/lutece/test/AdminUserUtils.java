/*
 * Copyright (c) 2002-2025, City of Paris
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public class AdminUserUtils
{

    private static final String ATTRIBUTE_ADMIN_USER = "lutece_admin_user";
    private static final String CORE_RIGHT_CLASS = "fr.paris.lutece.portal.business.right.Right";
    private static final String RIGHT_SETTER_ID = "setId";
    private static final String CORE_ADMINUSER_CLASS = "fr.paris.lutece.portal.business.user.AdminUser";
    private static final String ADMINUSER_SETTER_RIGHTS = "setRights";
    private static final String ADMINUSER_SETTER_LOCALE = "setLocale";

    /**
     * Register an admin user with a given right
     * 
     * @param request
     *            the request to register the user into
     * @param user
     *            The user
     * @param strRight
     *            The right
     */
    public static void registerAdminUserWithRight( HttpServletRequest request, Object user, String strRight )
    {
        try
        {
            Class<?> rightClass = Class.forName( CORE_RIGHT_CLASS );
            Object right = rightClass.getConstructor( ).newInstance( );

            Method setId = rightClass.getMethod( RIGHT_SETTER_ID, String.class );
            setId.invoke( right, strRight );

            Map mapRights = new HashMap<>( );
            mapRights.put( strRight, right );

            Class<?> adminUserClass = Class.forName( CORE_ADMINUSER_CLASS );
            Method setRights = adminUserClass.getMethod( ADMINUSER_SETTER_RIGHTS, Map.class );
            setRights.invoke( user, mapRights );

            Method setLocale = adminUserClass.getMethod( ADMINUSER_SETTER_LOCALE, Locale.class );
            setLocale.invoke( user, new Locale( "fr", "FR", "" ) );
            registerAdminUser( request, user );
        }
        catch( ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException e )
        {
            TestLogService.info( "Could not create Right and register to AdminUser", e );
        }
    }

    /**
     * Register an admin user
     * 
     * @param request
     *            the request to register the user into
     * @param user
     *            The user
     */
    public static void registerAdminUser( HttpServletRequest request, Object user )
    {
        request.getSession( true ).setAttribute( ATTRIBUTE_ADMIN_USER, user );
    }

}
