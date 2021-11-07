package es.udc.ws.app.model.fictripservice;

import es.udc.ws.util.configuration.ConfigurationParametersManager;

public class FicTripServiceFactory
{
    private final static String CLASS_NAME_PARAMETER = "FicTripServiceFactory.className";
    private static FicTripService service = null;

    private FicTripServiceFactory() {
    }

    @SuppressWarnings("rawtypes")
    private static FicTripService getInstance()
    {
        try
        {
            String serviceClassName = ConfigurationParametersManager
                    .getParameter(CLASS_NAME_PARAMETER);
            Class serviceClass = Class.forName(serviceClassName);
            return (FicTripService) serviceClass.getDeclaredConstructor().newInstance();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    public synchronized static FicTripService getService()
    {
        if(service == null)
        {
            service = getInstance();
        }
        return service;

    }
}
