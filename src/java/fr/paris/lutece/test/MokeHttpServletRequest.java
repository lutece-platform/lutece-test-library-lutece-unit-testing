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

import fr.paris.lutece.portal.business.right.Right;
import fr.paris.lutece.portal.business.user.AdminUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The Class is a mock object to simulate a HttpServletRequest
 */
public class MokeHttpServletRequest implements HttpServletRequest
{
    private static final String ATTRIBUTE_ADMIN_USER = "lutece_admin_user";
    private Cookie [ ] _cookies = null;
    private Map<String, List<String>> _mapParameters = new HashMap<>( );
    private Map<String, List<String>> _mapHeaders = new HashMap<>( );
    private MokeHttpSession _session = null;

    /**
     * Register an admin user with a given right
     * @param user The user
     * @param strRight The right
     */
    public void registerAdminUserWithRigth( AdminUser user, String strRight )
    {
        Map<String, Right> mapRights = new HashMap<String, Right>( );
        Right right = new Right( );
        right.setId( strRight );
        mapRights.put( strRight, right );
        user.setRights( mapRights );

        // TODO set locale user
        user.setLocale( new Locale( "fr", "FR", "" ) );
        registerAdminUser( user );
    }

    /**
     * Register an admin user
     * @param user The user
     */
    public void registerAdminUser( AdminUser user )
    {
        getSession( true ).setAttribute( ATTRIBUTE_ADMIN_USER, user );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAuthType( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Cookie [ ] getCookies( )
    {
        return _cookies;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long getDateHeader( String string )
    {
        return 0L;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getHeader( String strHeaderName )
    {
        List<String> listValues = _mapHeaders.get( strHeaderName );
        return listValues == null ? null : listValues.get( 0 );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Enumeration getHeaders( String strHeaderName )
    {
        List<String> listValues = _mapHeaders.get( strHeaderName );
        return listValues == null ? null : Collections.enumeration( listValues );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Enumeration getHeaderNames( )
    {
        return Collections.enumeration( _mapHeaders.keySet( ) );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getIntHeader( String string )
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMethod( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getPathInfo( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getPathTranslated( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getContextPath( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getQueryString( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRemoteUser( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isUserInRole( String string )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Principal getUserPrincipal( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRequestedSessionId( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRequestURI( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public StringBuffer getRequestURL( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getServletPath( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public HttpSession getSession( boolean bCreate )
    {
        if ( _session == null )
        {
            _session = new MokeHttpSession( );
        }

        return _session;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public HttpSession getSession( )
    {
        return _session;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isRequestedSessionIdValid( )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isRequestedSessionIdFromCookie( )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isRequestedSessionIdFromURL( )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isRequestedSessionIdFromUrl( )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getAttribute( String string )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Enumeration getAttributeNames( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getCharacterEncoding( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setCharacterEncoding( String string ) throws UnsupportedEncodingException
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getContentLength( )
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getContentType( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ServletInputStream getInputStream( ) throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getParameter( String strParameterName )
    {
        List<String> listValues = _mapParameters.get( strParameterName );
        return listValues == null ? null : listValues.get( 0 );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Enumeration getParameterNames( )
    {
        return Collections.enumeration( _mapParameters.keySet( ) );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String [ ] getParameterValues( String strParameterName )
    {
        List<String> listValues = _mapParameters.get( strParameterName );
        String [ ] values;
        if ( listValues != null )
        {
            values = new String [ listValues.size( )];
            listValues.toArray( values );
        }
        else
        {
            values = null;
        }

        return values;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map getParameterMap( )
    {
        return _mapParameters;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getProtocol( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getScheme( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getServerName( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getServerPort( )
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public BufferedReader getReader( ) throws IOException
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRemoteAddr( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRemoteHost( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setAttribute( String string, Object object )
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeAttribute( String string )
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Locale getLocale( )
    {
        return Locale.getDefault( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Enumeration getLocales( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isSecure( )
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public RequestDispatcher getRequestDispatcher( String string )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getRealPath( String string )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getRemotePort( )
    {
        return 80;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getLocalName( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getLocalAddr( )
    {
        return "";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getLocalPort( )
    {
        return 80;
    }
    
    // //////////////////////////////////////////////////////////////////////////
    // Initialize moke values

    /**
     * Initialize moke parameters
     * 
     * @param strParameterName
     *            The name
     * @param strValue
     *            The value
     */
    @Deprecated
    public void addMokeParameters( String strParameterName, String strValue )
    {
        List<String> list = _mapParameters.get( strParameterName );
        if ( list == null )
        {
            list = new ArrayList<String>( );
            _mapParameters.put( strParameterName, list );
        }
        list.add( strValue );
    }

    /**
     * Initialize moke parameters
     * 
     * @param strParameterName
     *            The name
     * @param strValue
     *            The value
     */
    public void addMokeParameter( String strParameterName, String strValue )
    {
        List<String> list = _mapParameters.get( strParameterName );
        if ( list == null )
        {
            list = new ArrayList<String>( );
            _mapParameters.put( strParameterName, list );
        }
        list.add( strValue );
    }

    /**
     * Initialize moke parameters
     * 
     * @param strHeaderName
     *            The name
     * @param strValue
     *            The value
     */
    public void addMokeHeader( String strHeaderName, String strValue )
    {
        List<String> list = _mapHeaders.get( strHeaderName );
        if ( list == null )
        {
            list = new ArrayList<String>( );
            _mapHeaders.put( strHeaderName, list );
        }
        list.add( strValue );
    }

    /**
     * Initialize moke cookies
     * 
     * @param cookies
     *            Cookie
     */
    public void setMokeCookies( Cookie [ ] cookies )
    {
        _cookies = cookies;
    }

}
