package com.es.jointexpensetracker.web.service;

import javax.servlet.http.HttpServletRequest;

public class URLpatternHandler
{
    // returns string after */UUID_START_FLAG/<uuid>
    public static String getURLafterUUID(HttpServletRequest request) throws StringIndexOutOfBoundsException
    {
        String uri = request.getRequestURI();
        System.out.println(uri);
        uri = uri.substring(uri.indexOf(ExpenseGroupUUIDService.UUID_START_FLAG));
        uri = uri.substring(uri.indexOf("/") + 1);
        uri = uri.substring(uri.indexOf("/"));
        return uri;
    }

    // returns string <uuid> from */UUID_START_FLAG/<uuid>/*
    public static String getUUIDfromURI(String uri)
    {
        uri = uri.substring(uri.indexOf(ExpenseGroupUUIDService.UUID_START_FLAG));
        uri = uri.substring(uri.indexOf("/") + 1);
        uri = uri.substring(0, uri.indexOf("/"));
        return uri;
    }


}
