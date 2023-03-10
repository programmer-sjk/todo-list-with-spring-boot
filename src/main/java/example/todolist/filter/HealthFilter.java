package example.todolist.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.todolist.common.ResponseMessage;
import example.todolist.health.dto.HealthResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class HealthFilter implements Filter {
    private final ObjectMapper objectMapper = new ObjectMapper();

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

        if (!isValidaHealthResponse(response, responseWrapper)) {
            responseWrapper.copyBodyToResponse();
            return ;
        }

        JsonNode node = convertJsonNode(responseWrapper);
        String applicationStatus = node.get("status").textValue();
        String dbStatus = node.path("components").path("db").get("status").textValue();
        ResponseMessage<HealthResponse> message = ResponseMessage.ok(new HealthResponse(applicationStatus, dbStatus));

        String newContent = objectMapper.writeValueAsString(message);
        response.setContentType("application/json");
        response.setContentLength(newContent.length());
        response.getOutputStream().write(newContent.getBytes());
    }

    private boolean isValidaHealthResponse(ServletResponse response, ContentCachingResponseWrapper responseWrapper) throws UnsupportedEncodingException, JsonProcessingException {
        if (((HttpServletResponse) response).getStatus() != HttpServletResponse.SC_OK) {
            return false;
        }

        JsonNode statusCodeNode = convertJsonNode(responseWrapper).get("statusCode");

        /**
         * actuator??? ???????????? health ??????????????? ???????????? foramt??? statusCode??? ??????.
         * statusCode??? ????????? ?????? ????????? ??????????????? ??????
         */
        if (statusCodeNode != null) {
            return false;
        }

        return true;
    }

    private JsonNode convertJsonNode(ContentCachingResponseWrapper responseWrapper) throws JsonProcessingException, UnsupportedEncodingException {
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseStr = new String(responseArray, responseWrapper.getCharacterEncoding());
        return objectMapper.readTree(responseStr);
    }
}
