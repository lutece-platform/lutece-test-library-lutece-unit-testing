/*
 * Copyright (c) 2002-2024, City of Paris
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
package fr.paris.lutece.test.mocks;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.servlet.http.Part;

public class MockPart implements Part
{

    private String _strFileName;
    private String _strName;
    private String _strContentType;
    private byte [ ] _bytes;

    public MockPart( String strName, String strContentType, String strFileName, byte [ ] bytes )
    {
        _strFileName = strFileName;
        _strContentType = strContentType;
        _strName = strName;
        _bytes = bytes;
    }

    @Override
    public InputStream getInputStream( ) throws IOException
    {
        return new ByteArrayInputStream( _bytes );
    }

    @Override
    public String getContentType( )
    {
        return _strContentType;
    }

    @Override
    public String getName( )
    {
        return _strName;
    }

    @Override
    public String getSubmittedFileName( )
    {
        return _strFileName;
    }

    @Override
    public long getSize( )
    {
        return _bytes.length;
    }

    @Override
    public void write( String fileName ) throws IOException
    {
        // Do nothing
    }

    @Override
    public void delete( ) throws IOException
    {
        // Do nothing
    }

    @Override
    public String getHeader( String name )
    {
        return null;
    }

    @Override
    public Collection<String> getHeaders( String name )
    {
         return new ArrayList<String>( );
    }

    @Override
    public Collection<String> getHeaderNames( )
    {
        return new ArrayList<String>( );
    }

}
