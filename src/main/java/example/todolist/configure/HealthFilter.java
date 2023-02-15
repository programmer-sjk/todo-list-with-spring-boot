package example.todolist.configure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.todolist.health.dto.HealthResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class HealthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);

        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseStr = new String(responseArray, responseWrapper.getCharacterEncoding());

        JsonNode node = new ObjectMapper().readTree(responseStr);
        String applicationStatus = node.get("status").textValue();
        String dbStatus = node.path("components").path("db").get("status").textValue();
        HealthResponse healthResponse = new HealthResponse(applicationStatus, dbStatus);

        String newContent = new ObjectMapper().writeValueAsString(healthResponse);
        response.setContentType("application/json");
        response.setContentLength(newContent.length());
        response.getOutputStream().write(newContent.getBytes());
    }
}
