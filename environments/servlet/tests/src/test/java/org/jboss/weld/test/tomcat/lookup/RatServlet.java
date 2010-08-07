package org.jboss.weld.test.tomcat.lookup;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RatServlet extends HttpServlet
{
   
   @Inject Sewer sewer;
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      if (sewer.getName().equals(Sewer.NAME))
      {
         resp.setStatus(HttpServletResponse.SC_OK);
      }
      else
      {
         resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
   }
   
}
