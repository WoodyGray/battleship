package org.woody;

import org.woody.endpoints.LoggingServerEndpoint;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LoggingServerEndpoint.startServer(8001);
    }
}
