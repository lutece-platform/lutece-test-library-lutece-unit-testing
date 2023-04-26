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

import jakarta.servlet.http.Cookie;

import org.springframework.mock.web.MockHttpServletRequest;

import fr.paris.lutece.portal.business.user.AdminUser;

/**
 * The Class is a mock object to simulate a HttpServletRequest
 * @deprecated Use {@link MockHttpServletRequest} instead
 */
@Deprecated
public class MokeHttpServletRequest extends MockHttpServletRequest
{

    /**
     * Register an admin user with a given right
     * @param user The user
     * @param strRight The right
     * @deprecated use {@link Utils#registerAdminUserWithRigth(javax.servlet.http.HttpServletRequest, AdminUser, String)} instead
     */
    @Deprecated
    public void registerAdminUserWithRigth( AdminUser user, String strRight )
    {
        Utils.registerAdminUserWithRigth( this, user, strRight );
    }

    /**
     * Register an admin user
     * @param user The user
     * @deprecated use {@link Utils#registerAdminUser(javax.servlet.http.HttpServletRequest, AdminUser)} instead
     */
    @Deprecated
    public void registerAdminUser( AdminUser user )
    {
        Utils.registerAdminUser( this, user );
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
        addParameter( strParameterName, strValue );
    }

    /**
     * Initialize moke parameters
     * 
     * @param strParameterName
     *            The name
     * @param strValue
     *            The value
     */
    @Deprecated
    public void addMokeParameter( String strParameterName, String strValue )
    {
        addParameter( strParameterName, strValue );
    }

    /**
     * Initialize moke parameters
     * 
     * @param strHeaderName
     *            The name
     * @param strValue
     *            The value
     */
    @Deprecated
    public void addMokeHeader( String strHeaderName, String strValue )
    {
        addHeader( strHeaderName, strValue );
    }

    /**
     * Initialize moke cookies
     * 
     * @param cookies
     *            Cookie
     */
    @Deprecated
    public void setMokeCookies( Cookie [ ] cookies )
    {
        setCookies( cookies );
    }

}
