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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpHeaders;

import fr.paris.lutece.test.TestLogService;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConnection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.Part;

public class MockHttpServletRequest implements HttpServletRequest
{
    public MockHttpServletRequest()
    {
        this(null);
    }

    public MockHttpServletRequest(ServletContext servletContext)
    {
        locales.add(Locale.ENGLISH);
        this.servletContext = (servletContext != null ? servletContext : new MockServletContext());
    }

    private Map<String, String[]> parameters = new HashMap<>();

    public void setParameter(String name, String value)
    {
        setParameter(name, new String[] { value });
    }

    public void setParameter(String name, String... values)
    {
        this.parameters.put(name, values);
    }

    public String getParameter(String name)
    {
        String[] arr = this.parameters.get(name);
        return (arr != null && arr.length > 0 ? arr[0] : null);
    }

    public void addParameter(String name, String value)
    {
        addParameter(name, new String[] { value });
    }

    private String protocol = "HTTP/1.1";
    private String scheme = "http";
    private String serverName = "localhost";
    private int serverPort = 80;
    private String remoteAddr = "127.0.0.1";
    private String contextPath = "";

    public void setContextPath(String contextPath)
    {
        this.contextPath = contextPath;
    }

    @Override
    public String getContextPath()
    {
        return contextPath;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }

    @Override
    public String getServerName()
    {
        String host = getHeader("Host");
        if (host != null)
        {
            host = host.trim();
            if (host.startsWith("["))
            {
                int indexOfClosingBracket = host.indexOf(']');
                host = host.substring(0, indexOfClosingBracket + 1);
            } else if (host.contains(":"))
                host = host.substring(0, host.indexOf(':'));
            return host;
        }
        return serverName;
    }

    public void addParameter(String name, String... values)
    {
        String[] oldArr = this.parameters.get(name);
        if (oldArr != null)
        {
            String[] newArr = new String[oldArr.length + values.length];
            System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);
            System.arraycopy(values, 0, newArr, oldArr.length, values.length);
            setParameter(name, newArr);
        } else
        {
            setParameter(name, values);
        }
    }

    /**
     * Add all provided parameters <strong>without</strong> replacing any existing values. To replace existing values, use
     * {@link #setParameters(java.util.Map)}.
     */
    public void addParameters(Map<String, ?> params)
    {
        params.forEach((key, value) ->
        {
            if (value instanceof String str)
            {
                addParameter(key, str);
            } else if (value instanceof String[] strings)
            {
                addParameter(key, strings);
            } else
            {
                throw new IllegalArgumentException("Parameter map value must be single value " + " or array of type [" + String.class.getName() + "]");
            }
        });
    }

    @Override
    public Enumeration<String> getParameterNames()
    {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name)
    {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap()
    {
        return Collections.unmodifiableMap(this.parameters);
    }

    private byte[] content;
    private String contentType, method;
    private String characterEncoding = "ISO-8859-1";

    public void setContent(byte[] content)
    {
        this.content = content;
//        this.inputStream = null;
//        this.reader = null;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    @Override
    public String getMethod()
    {
        return this.method;
    }

    @Override
    public String getCharacterEncoding()
    {
        return characterEncoding;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException
    {
        TestLogService.info("" + new Object()
        {
            public String toString()
            {
                return "MockHttpServletRequest:" + getClass().getEnclosingMethod().getName();
            }
        });

    }

    @Override
    public int getContentLength()
    {
        return (content != null ? content.length : -1);
    }

    @Override
    public long getContentLengthLong()
    {
        return getContentLength();
    }

    @Override
    public String getContentType()
    {
        return contentType;
    }

    private ServletInputStream inputStream;

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        inputStream = new MockServletInputStream(this.content != null ? new ByteArrayInputStream(this.content) : InputStream.nullInputStream());
        return inputStream;
    }

    @Override
    public String getProtocol()
    {
        return protocol;
    }

    @Override
    public String getScheme()
    {
        return scheme;
    }

    @Override
    public int getServerPort()
    {
        return serverPort;
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getRemoteAddr()
    {
        return remoteAddr;
    }

    public void setRemoteAddr( String remoteAddr )
    {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public String getRemoteHost()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    private final Map<String, Object> attributes = new LinkedHashMap<>();

    @Override
    public void setAttribute(String name, Object value)
    {
        if (value != null)
            attributes.put(name, value);
        else
            attributes.remove(name);
    }

    @Override
    public void removeAttribute(String name)
    {
        attributes.remove(name);
    }

    @Override
    public Object getAttribute(String name)
    {
        return attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames()
    {
        return Collections.enumeration(new LinkedHashSet<>(this.attributes.keySet()));
    }

    private final LinkedList<Locale> locales = new LinkedList<>();

    public void addPreferredLocale(Locale locale)
    {
        locales.addFirst(locale);
    }

    public void setPreferredLocales(List<Locale> locales)
    {
        this.locales.clear();
        this.locales.addAll(locales);
    }

    @Override
    public Locale getLocale()
    {
        return locales.getFirst();
    }

    @Override
    public Enumeration<Locale> getLocales()
    {
        return Collections.enumeration(locales);
    }

    @Override
    public boolean isSecure()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path)
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public int getRemotePort()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return 0;
    }

    @Override
    public String getLocalName()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getLocalAddr()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public int getLocalPort()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return 0;
    }

    @Override
    public ServletContext getServletContext()
    {
        return servletContext;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public boolean isAsyncStarted()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public boolean isAsyncSupported()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public AsyncContext getAsyncContext()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public DispatcherType getDispatcherType()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getRequestId()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getProtocolRequestId()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public ServletConnection getServletConnection()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getAuthType()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public Cookie[] getCookies()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public long getDateHeader(String name)
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return 0;
    }

    private final Map<String, HeaderVal> headers = new LinkedHashMap<>();

    @Override
    public String getHeader(String name)
    {
        HeaderVal header = this.headers.get(name);
        return (header != null ? header.getStringValue() : null);
    }

    @Override
    public Enumeration<String> getHeaders(String name)
    {
        HeaderVal header = this.headers.get(name);
        return Collections.enumeration(header != null ? header.getStringValues() : new LinkedList<>());
    }

    @Override
    public Enumeration<String> getHeaderNames()
    {
        return Collections.enumeration(this.headers.keySet());
    }

    @Override
    public int getIntHeader(String name)
    {
        HeaderVal header = this.headers.get(name);
        Object value = (header != null ? header.getValue() : null);
        if (value instanceof Number number)
        {
            return number.intValue();
        } else if (value instanceof String str)
        {
            return Integer.parseInt(str);
        } else if (value != null)
        {
            throw new NumberFormatException("Value for header '" + name + "' is not a Number: " + value);
        } else
        {
            return -1;
        }
    }

    public void addHeader(String name, Object value)
    {
        if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(name) && !this.headers.containsKey(HttpHeaders.CONTENT_TYPE))
            setContentType(value.toString());
        else
            doAddHeaderValue(name, value, false);
    }

    private void doAddHeaderValue(String name, Object value, boolean replace)
    {
        HeaderVal header = headers.get(name);
        if (header == null || replace)
        {
            header = new HeaderVal();
            headers.put(name, header);
        }
        if (value instanceof Collection)
        {
            header.addValues((Collection<?>) value);
        } else if (value.getClass().isArray())
        {
            header.addValueArray(value);
        } else
        {
            header.addValue(value);
        }
    }

    @Override
    public String getPathInfo()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getPathTranslated()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getQueryString()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getRemoteUser()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public boolean isUserInRole(String role)
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public Principal getUserPrincipal()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getRequestedSessionId()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getRequestURI()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public StringBuffer getRequestURL()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public String getServletPath()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    private MockHttpSession session;
    private ServletContext servletContext;

    @Override
    public HttpSession getSession(boolean create)
    {
        if (session == null && create)
            session = new MockHttpSession();
        return session;
    }

    @Override
    public HttpSession getSession()
    {
        return getSession(true);
    }

    @Override
    public String changeSessionId()
    {
        return ((MockHttpSession) session).changeSessionId();
    }

    @Override
    public boolean isRequestedSessionIdValid()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL()
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void logout() throws ServletException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException
    {
        TestLogService.info("MockHttpServletRequest." + new Object()
        {
        }.getClass().getEnclosingMethod().getName());
        return null;
    }

    private static class HeaderVal
    {
        private final List<Object> values = new LinkedList<>();

        void addValue(Object value)
        {
            this.values.add(value);
        }

        void addValues(Collection<?> values)
        {
            this.values.addAll(values);
        }

        void addValueArray(Object values)
        {
            if (values != null && values.getClass().isArray())
            {
                int length = Array.getLength(values);
                for (int i = 0; i < length; i++)
                    this.values.add(Array.get(values, i));
            }
        }
        List<String> getStringValues()
        {
            return this.values.stream().map(Object::toString).toList();
        }

        Object getValue()
        {
            return (!this.values.isEmpty() ? this.values.get(0) : null);
        }

        String getStringValue()
        {
            return (!this.values.isEmpty() ? String.valueOf(this.values.get(0)) : null);
        }

        @Override
        public String toString()
        {
            return this.values.toString();
        }
    }
}
