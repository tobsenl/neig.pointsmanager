package cn.sqhl.neig.pointsmanager.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sun.net.httpserver.Filter.Chain;

public class Checkfilter extends OncePerRequestFilter {

	protected ServletContext scontext;
	
	protected void initFilterBean() throws ServletException {
		super.initFilterBean();
		scontext=this.getServletContext();
		WebApplicationContext context = (WebApplicationContext) scontext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		
	};
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		request.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

}
