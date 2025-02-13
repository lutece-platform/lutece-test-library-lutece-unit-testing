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
package fr.paris.lutece.test.mocks;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import jakarta.annotation.Priority;
import jakarta.enterprise.concurrent.ContextService;
import jakarta.enterprise.concurrent.ManagedScheduledExecutorService;
import jakarta.enterprise.concurrent.Trigger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

/**
 * Stands for a container-provided ManagedScheduledExecutorService when under java SE.
 * 
 */
@ApplicationScoped
@Alternative
@Priority( 10 )
public class MockManagedScheduledExecutorService implements ManagedScheduledExecutorService
{

    private final ScheduledExecutorService _scheduledExecutorService;

    public MockManagedScheduledExecutorService( )
    {
        _scheduledExecutorService = Executors.newScheduledThreadPool( 5 );
    }

    @Override
    public <U> CompletableFuture<U> completedFuture( U value )
    {
        return CompletableFuture.completedFuture( value );
    }

    @Override
    public <U> CompletionStage<U> completedStage( U value )
    {
        return CompletableFuture.completedStage( value );
    }

    @Override
    public <T> CompletableFuture<T> copy( CompletableFuture<T> stage )
    {
        return stage.copy( );
    }

    @Override
    public <T> CompletionStage<T> copy( CompletionStage<T> stage )
    {
        return stage.toCompletableFuture( ).copy( );
    }

    @Override
    public <U> CompletableFuture<U> failedFuture( Throwable ex )
    {
        return CompletableFuture.failedFuture( ex );
    }

    @Override
    public <U> CompletionStage<U> failedStage( Throwable ex )
    {
        return CompletableFuture.failedStage( ex );
    }

    @Override
    public ContextService getContextService( )
    {
        throw new UnsupportedOperationException( "Not supported" );
    }

    @Override
    public <U> CompletableFuture<U> newIncompleteFuture( )
    {
        return new CompletableFuture<U>( );
    }

    @Override
    public CompletableFuture<Void> runAsync( Runnable runnable )
    {
        return CompletableFuture.runAsync( runnable );
    }

    @Override
    public <U> CompletableFuture<U> supplyAsync( Supplier<U> supplier )
    {
        return CompletableFuture.supplyAsync( supplier );
    }

    @Override
    public boolean awaitTermination( long timeout, TimeUnit unit ) throws InterruptedException
    {
        return _scheduledExecutorService.awaitTermination( timeout, unit );
    }

    @Override
    public <T> List<Future<T>> invokeAll( Collection<? extends Callable<T>> tasks ) throws InterruptedException
    {
        return _scheduledExecutorService.invokeAll( tasks );
    }

    @Override
    public <T> List<Future<T>> invokeAll( Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit ) throws InterruptedException
    {
        return _scheduledExecutorService.invokeAll( tasks, timeout, unit );
    }

    @Override
    public <T> T invokeAny( Collection<? extends Callable<T>> tasks ) throws InterruptedException, ExecutionException
    {
        return _scheduledExecutorService.invokeAny( tasks );
    }

    @Override
    public <T> T invokeAny( Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit )
            throws InterruptedException, ExecutionException, TimeoutException
    {
        return _scheduledExecutorService.invokeAny( tasks, timeout, unit );
    }

    @Override
    public boolean isShutdown( )
    {
        return false;
    }

    @Override
    public boolean isTerminated( )
    {
        return false;
    }

    @Override
    public void shutdown( )
    {
        _scheduledExecutorService.shutdown( );
    }

    @Override
    public List<Runnable> shutdownNow( )
    {
        return _scheduledExecutorService.shutdownNow( );
    }

    @Override
    public <T> Future<T> submit( Callable<T> task )
    {
        return _scheduledExecutorService.submit( task );
    }

    @Override
    public Future<?> submit( Runnable task )
    {
        return _scheduledExecutorService.submit( task );
    }

    @Override
    public <T> Future<T> submit( Runnable arg0, T arg1 )
    {
        return _scheduledExecutorService.submit( arg0, arg1 );
    }

    @Override
    public void execute( Runnable arg0 )
    {
        _scheduledExecutorService.execute( arg0 );
    }

    @Override
    public ScheduledFuture<?> schedule( Runnable arg0, long arg1, TimeUnit arg2 )
    {
        return _scheduledExecutorService.schedule( arg0, arg1, arg2 );
    }

    @Override
    public <V> ScheduledFuture<V> schedule( Callable<V> arg0, long arg1, TimeUnit arg2 )
    {
        return _scheduledExecutorService.schedule( arg0, arg1, arg2 );
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate( Runnable arg0, long arg1, long arg2, TimeUnit arg3 )
    {
        return _scheduledExecutorService.scheduleAtFixedRate( arg0, arg1, arg2, arg3 );
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay( Runnable arg0, long arg1, long arg2, TimeUnit arg3 )
    {
        return _scheduledExecutorService.scheduleWithFixedDelay( arg0, arg1, arg2, arg3 );
    }

    @Override
    public ScheduledFuture<?> schedule( Runnable command, Trigger trigger )
    {
        Date referenceDate = new Date( );
        Date nextRunTime = trigger.getNextRunTime( null, referenceDate );
        return scheduleAtFixedRate( command, 1000, nextRunTime.getTime( ) - referenceDate.getTime( ), TimeUnit.MILLISECONDS );
    }

    @Override
    public <V> ScheduledFuture<V> schedule( Callable<V> callable, Trigger trigger )
    {
        Date referenceDate = new Date( );
        Date nextRunTime = trigger.getNextRunTime( null, referenceDate );
        return (ScheduledFuture<V>) scheduleAtFixedRate( new FutureTask( callable ), 1000, nextRunTime.getTime( ) - referenceDate.getTime( ),
                TimeUnit.MILLISECONDS );
    }

}
