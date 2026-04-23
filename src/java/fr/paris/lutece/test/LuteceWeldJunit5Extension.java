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
package fr.paris.lutece.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.junit5.ExtensionContextUtils;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.InjectionTarget;

/**
 * Lutece Weld5 Junit extension that allows to reuse the same Weld container throughout the tests
 */
public class LuteceWeldJunit5Extension extends WeldJunit5Extension
{

    private static final String OFFHEAP_CACHE = "lutece.cache.default.offheap";
    private static WeldContainer _container;
    private static WeldInitiator _initiator;

    @Override
    public void beforeAll( ExtensionContext context )
    {
        if ( null == _container )
        {
            // Prevent cache offheap
            System.setProperty( OFFHEAP_CACHE, "-1" );

            // Let WeldJUnit5Extension initialize the weld container
            super.beforeAll( context );

            // Get weld container from WeldJUnit5Extension and store it
            _container = ExtensionContextUtils.getContainerFromStore( context );
            _initiator = ExtensionContextUtils.getInitiatorFromStore( context );
        }
        else
        {
            // Store the previously initialized weld container in the extension context
            ExtensionContextUtils.setContainerToStore( context, _container );
            ExtensionContextUtils.setInitiatorToStore( context, _initiator );

            List<Object> allTestInstances = new ArrayList<>( context.getRequiredTestInstances( ).getAllInstances( ) );
            Collections.reverse( allTestInstances );

            // Resolve injection points inside test class, only the first test class is managed by weld extension
            for ( Object instance : allTestInstances )
            {
                BeanManager beanManager = _container.getBeanManager( );
                CreationalContext<Object> ctx = beanManager.createCreationalContext( null );
                @SuppressWarnings( "unchecked" )
                InjectionTarget<Object> injectionTarget = (InjectionTarget<Object>) beanManager
                        .getInjectionTargetFactory( beanManager.createAnnotatedType( instance.getClass( ) ) )
                        .createInjectionTarget( null );
                injectionTarget.inject( instance, ctx );
            }
        }
    }

    @Override
    public void beforeEach( ExtensionContext extensionContext )
    {
    }

    @Override
    public void afterEach( ExtensionContext context )
    {
        // In weld-junit5 5.x + JUnit 5.13+, the WeldContainer is registered as AutoCloseable in the
        // per-test-class Store (namespaced by WeldJunit5Extension + testClass). Since we share a single
        // static container across ALL test classes for performance, we must remove it from the Store
        // BEFORE JUnit disposes the Store and auto-closes every AutoCloseable resource it holds.
        // Without this, the very first test class's afterEach/afterAll would close the shared
        // container, and every subsequent test class would fail with
        // "WELD-ENV-002002: Weld SE container was already shut down".
        ExtensionContextUtils.removeContainerFromStore( context );
    }

    @Override
    public void afterAll( ExtensionContext context )
    {
        // Same rationale as afterEach: remove the shared container from the class-scoped Store so
        // JUnit's automatic AutoCloseable cleanup (introduced in JUnit 5.13) does not shut it down.
        // The container remains referenced by the static _container field and is reused by the next
        // test class in beforeAll().
        ExtensionContextUtils.removeContainerFromStore( context );
    }

}
