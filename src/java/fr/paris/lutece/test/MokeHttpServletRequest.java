/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
 * The Class is a moke object to simulate a HttpServletRequest
 * @author Mairie de Paris
 * @version 1.0
 */
public class MokeHttpServletRequest
    implements HttpServletRequest
{
    private static final String ATTRIBUTE_ADMIN_USER = "lutece_admin_user";
    private Cookie[] _cookies = null;
    private Map<String, List<String>> _mapParameters = new HashMap<>(  );
    private Map<String, List<String>> _mapHeaders = new HashMap<>(  );
    private MokeHttpSession _session = null;

    public void registerAdminUserWithRigth( AdminUser user, String strRight )
    {
        Map<String, Right> mapRights = new HashMap<String, Right>(  );
        Right right = new Right(  );
        right.setId( strRight );
        mapRights.put( strRight, right );
        user.setRights( mapRights );

        //TODO set locale user
        user.setLocale( new Locale( "fr", "FR", "" ) );
        registerAdminUser( user );
    }

    public void registerAdminUser( AdminUser user )
    {
        getSession( true ).setAttribute( ATTRIBUTE_ADMIN_USER, user );
    }

    /**
     * getAuthType
     *
     * @return String
     */
    @Override
    public String getAuthType(  )
    {
        return "";
    }

    /**
     * getCookies
     *
     * @return Cookie[]
    
     */
    @Override
    public Cookie[] getCookies(  )
    {
        return _cookies;
    }

    /**
     * getDateHeader
     *
     * @param string String
     * @return long
    
     */
    @Override
    public long getDateHeader( String string )
    {
        return 0L;
    }

    /**
     * getHeader
     *
     * @param strHeaderName String
     * @return String
    
     */
    @Override
    public String getHeader( String strHeaderName )
    {
        return (String) _mapHeaders.get( strHeaderName ).get( 0 );
    }

    /**
     * getHeaders
     *
     * @param strHeaderName String
     * @return Enumeration
    
     */
    @Override
    public Enumeration getHeaders( String strHeaderName )
    {
        return Collections.enumeration( _mapHeaders.get( strHeaderName ));
    }

    /**
     * getHeaderNames
     *
     * @return Enumeration
    
     */
    @Override
    public Enumeration getHeaderNames(  )
    {
        return Collections.enumeration( _mapHeaders.keySet() );
    }

    /**
     * getIntHeader
     *
     * @param string String
     * @return int
    
     */
    @Override
    public int getIntHeader( String string )
    {
        return 0;
    }

    /**
     * getMethod
     *
     * @return String
    
     */
    @Override
    public String getMethod(  )
    {
        return "";
    }

    /**
     * getPathInfo
     *
     * @return String
    
     */
    @Override
    public String getPathInfo(  )
    {
        return "";
    }

    /**
     * getPathTranslated
     *
     * @return String
    
     */
    @Override
    public String getPathTranslated(  )
    {
        return "";
    }

    /**
     * getContextPath
     *
     * @return String
    
     */
    @Override
    public String getContextPath(  )
    {
        return "";
    }

    /**
     * getQueryString
     *
     * @return String
    
     */
    @Override
    public String getQueryString(  )
    {
        return "";
    }

    /**
     * getRemoteUser
     *
     * @return String
    
     */
    @Override
    public String getRemoteUser(  )
    {
        return "";
    }

    /**
     * isUserInRole
     *
     * @param string String
     * @return boolean
    
     */
    @Override
    public boolean isUserInRole( String string )
    {
        return false;
    }

    /**
     * getUserPrincipal
     *
     * @return Principal
    
     */
    @Override
    public Principal getUserPrincipal(  )
    {
        return null;
    }

    /**
     * getRequestedSessionId
     *
     * @return String
    
     */
    @Override
    public String getRequestedSessionId(  )
    {
        return "";
    }

    /**
     * getRequestURI
     *
     * @return String
    
     */
    @Override
    public String getRequestURI(  )
    {
        return "";
    }

    /**
     * getRequestURL
     *
     * @return StringBuffer
    
     */
    @Override
    public StringBuffer getRequestURL(  )
    {
        return null;
    }

    /**
     * getServletPath
     *
     * @return String
    
     */
    @Override
    public String getServletPath(  )
    {
        return "";
    }

    /**
     * getSession
     *
     * @param bCreate boolean
     * @return HttpSession
    
     */
    @Override
    public HttpSession getSession( boolean bCreate )
    {
        if ( _session == null )
        {
            _session = new MokeHttpSession(  );
        }

        return _session;
    }

    /**
     * getSession
     *
     * @return HttpSession
    
     */
    @Override
    public HttpSession getSession(  )
    {
        return _session;
    }

    /**
     * isRequestedSessionIdValid
     *
     * @return boolean
    
     */
    @Override
    public boolean isRequestedSessionIdValid(  )
    {
        return false;
    }

    /**
     * isRequestedSessionIdFromCookie
     *
     * @return boolean
    
     */
    @Override
    public boolean isRequestedSessionIdFromCookie(  )
    {
        return false;
    }

    /**
     * isRequestedSessionIdFromURL
     *
     * @return boolean
    
     */
    @Override
    public boolean isRequestedSessionIdFromURL(  )
    {
        return false;
    }

    /**
     * isRequestedSessionIdFromUrl
     *
     * @return boolean
    
     */
    @Override
    public boolean isRequestedSessionIdFromUrl(  )
    {
        return false;
    }

    /**
     * getAttribute
     *
     * @param string String
     * @return Object
     *
     */
    @Override
    public Object getAttribute( String string )
    {
        return "";
    }

    /**
     * getAttributeNames
     *
     * @return Enumeration
     *
     */
    @Override
    public Enumeration getAttributeNames(  )
    {
        return null;
    }

    /**
     * getCharacterEncoding
     *
     * @return String
     *
     */
    @Override
    public String getCharacterEncoding(  )
    {
        return "";
    }

    /**
     * setCharacterEncoding
     *
     * @param string String
     * @throws UnsupportedEncodingException
     *
     */
    @Override
    public void setCharacterEncoding( String string )
                              throws UnsupportedEncodingException
    {
    }

    /**
     * getContentLength
     *
     * @return int
     *
     */
    @Override
    public int getContentLength(  )
    {
        return 0;
    }

    /**
     * getContentType
     *
     * @return String
     *
     */
    @Override
    public String getContentType(  )
    {
        return "";
    }

    /**
     * getInputStream
     *
     * @throws IOException
     * @return ServletInputStream
     *
     */
    @Override
    public ServletInputStream getInputStream(  )
                                      throws IOException
    {
        return null;
    }

    /**
     * getParameter
     *
     * @param strParameterName String
     * @return String
     *
     */
    @Override
    public String getParameter( String strParameterName )
    {
        return (String) _mapParameters.get( strParameterName ).get( 0 );
    }

    /**
     * getParameterNames
     *
     * @return Enumeration
     *
     */
    @Override
    public Enumeration getParameterNames(  )
    {
        return Collections.enumeration(  _mapParameters.keySet(  ) );
    }

    /**
     * getParameterValues
     *
     * @param strParameterName String
     * @return String[]
     *
     */
    @Override
    public String[] getParameterValues( String strParameterName )
    {
        String[] values =  (String[]) _mapParameters.get( strParameterName ).toArray() ;

        return values;
    }

    /**
     * getParameterMap
     *
     * @return Map
     *
     */
    @Override
    public Map getParameterMap(  )
    {
        return _mapParameters;
    }

    /**
     * getProtocol
     *
     * @return String
     *
     */
    @Override
    public String getProtocol(  )
    {
        return "";
    }

    /**
     * getScheme
     *
     * @return String
     *
     */
    @Override
    public String getScheme(  )
    {
        return "";
    }

    /**
     * getServerName
     *
     * @return String
     *
     */
    @Override
    public String getServerName(  )
    {
        return "";
    }

    /**
     * getServerPort
     *
     * @return int
     *
     */
    @Override
    public int getServerPort(  )
    {
        return 0;
    }

    /**
     * getReader
     *
     * @throws IOException
     * @return BufferedReader
     *
     */
    @Override
    public BufferedReader getReader(  )
                             throws IOException
    {
        return null;
    }

    /**
     * getRemoteAddr
     *
     * @return String
     *
     */
    @Override
    public String getRemoteAddr(  )
    {
        return "";
    }

    /**
     * getRemoteHost
     *
     * @return String
     *
     */
    @Override
    public String getRemoteHost(  )
    {
        return "";
    }

    /**
     * setAttribute
     *
     * @param string String
     * @param object Object
     *
     */
    @Override
    public void setAttribute( String string, Object object )
    {
    }

    /**
     * removeAttribute
     *
     * @param string String
     *
     */
    @Override
    public void removeAttribute( String string )
    {
    }

    /**
     * getLocale
     *
     * @return Locale
     *
     */
    @Override
    public Locale getLocale(  )
    {
        return Locale.getDefault(  );
    }

    /**
     * getLocales
     *
     * @return Enumeration
     *
     */
    @Override
    public Enumeration getLocales(  )
    {
        return null;
    }

    /**
     * isSecure
     *
     * @return boolean
     *
     */
    @Override
    public boolean isSecure(  )
    {
        return false;
    }

    /**
     * getRequestDispatcher
     *
     * @param string String
     * @return RequestDispatcher
     *
     */
    @Override
    public RequestDispatcher getRequestDispatcher( String string )
    {
        return null;
    }

    /**
     * getRealPath
     *
     * @param string String
     * @return String
     *
     */
    @Override
    public String getRealPath( String string )
    {
        return "";
    }

    ////////////////////////////////////////////////////////////////////////////
    // Initialize moke values

    /**
     * Initialize moke parameters
     * @param strParameterName The name
     * @param strValue The value
     */
    @Deprecated
    public void addMokeParameters( String strParameterName, String strValue )
    {
        List<String> list = _mapParameters.get( strParameterName );
        if( list == null )
        {
            list = new ArrayList<String>();
            _mapParameters.put( strParameterName, list );
        }
        list.add( strValue );
    }

    /**
     * Initialize moke parameters
     * @param strParameterName The name
     * @param strValue The value
     */
    public void addMokeParameter( String strParameterName, String strValue )
    {
        List<String> list = _mapParameters.get( strParameterName );
        if( list == null )
        {
            list = new ArrayList<String>();
            _mapParameters.put( strParameterName, list );
        }
        list.add( strValue );
    }

    /**
     * Initialize moke parameters
     * @param strHeaderName The name
     * @param strValue The value
     */
    public void addMokeHeader( String strHeaderName, String strValue )
    {
        List<String> list = _mapHeaders.get( strHeaderName );
        if( list == null )
        {
            list = new ArrayList<String>();
            _mapHeaders.put( strHeaderName, list );
        }
        list.add( strValue );
    }

    /**
     * Initialize moke cookies
     * @param cookies Cookie
     */
    public void setMokeCookies( Cookie[] cookies )
    {
        _cookies = cookies;
    }

    /**
     * The remote port
     * @return the remote port 
     */
    @Override
    public int getRemotePort(  )
    {
        return 80;
    }

    /**
     * The locale name
     * @return the local name
     */
    @Override
    public String getLocalName(  )
    {
        return "";
    }

    /**
     * The local Address
     * @return the local Address
     */
    @Override
    public String getLocalAddr(  )
    {
        return "";
    }

    /**
     * The local port
     * @return The local port
     */
    @Override
    public int getLocalPort(  )
    {
        return 80;
    }
}
