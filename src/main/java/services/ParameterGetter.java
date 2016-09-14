package services;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;


public class ParameterGetter {
    public static Map handleRequest(HttpServletRequest req) throws IOException {


        Enumeration<String> parameterNames = req.getParameterNames();
        Map returnedMap = new HashMap();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = req.getParameterValues(paramName);

            for (String paramValue : paramValues) {
                returnedMap.put(paramName,paramValue);
            }

        }

        return returnedMap;
    }

}
