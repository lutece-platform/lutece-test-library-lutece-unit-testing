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

import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * MokeHttpServletRequestTest
 */
public class MokeHttpServletRequestTest
{
    private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String LANGUAGE1 = "en";
    private static final String LANGUAGE2 = "fr";
    private static final String ENCODING1 = "gzip,deflate";

    /**
     * Test of registerAdminUserWithRigth method, of class MokeHttpServletRequest.
     */
    @Test
    public void testMokeHttpRequestHeaders()
    {
        System.out.println( "testMokeHttpRequestHeaders" );
        MokeHttpServletRequest request = new MokeHttpServletRequest();
        request.addMokeHeader( HEADER_ACCEPT_LANGUAGE, LANGUAGE1 );
        request.addMokeHeader( HEADER_ACCEPT_LANGUAGE, LANGUAGE2 );
        request.addMokeHeader( HEADER_ACCEPT_ENCODING, ENCODING1 );
        assertEquals( request.getHeader( HEADER_ACCEPT_LANGUAGE) , LANGUAGE1 );
        List<String> listValues = Collections.list( request.getHeaders( HEADER_ACCEPT_LANGUAGE ));
        assertTrue( listValues.size() == 2 );
        List<String> listHeaderNames = Collections.list( request.getHeaderNames());
        assertTrue( listHeaderNames.size() == 2 );
    }

    
}
