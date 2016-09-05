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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * Moke object to simulate HttpSession
 *
 */
public class MokeHttpSession implements HttpSession
{
    Map<String, Object> _mapAttributes = new HashMap<String, Object>( );

    /**
     * {@inheritDoc }
     */
    @Override
    public long getCreationTime( )
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getId( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long getLastAccessedTime( )
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ServletContext getServletContext( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setMaxInactiveInterval( int interval )
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getMaxInactiveInterval( )
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public HttpSessionContext getSessionContext( )
    {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getAttribute( String strName )
    {
        return _mapAttributes.get( strName );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getValue( String strName )
    {
        return _mapAttributes.get( strName );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Enumeration getAttributeNames( )
    {
        return Collections.enumeration( _mapAttributes.keySet() );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String [ ] getValueNames( )
    {
        return (String[]) _mapAttributes.keySet().toArray();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setAttribute( String strName, Object value )
    {
        _mapAttributes.put( strName, value );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void putValue( String name, Object value )
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeAttribute( String strName )
    {
        _mapAttributes.remove( strName );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeValue( String strName )
    {
        _mapAttributes.remove( strName );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void invalidate( )
    {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isNew( )
    {
        return true;
    }
}
