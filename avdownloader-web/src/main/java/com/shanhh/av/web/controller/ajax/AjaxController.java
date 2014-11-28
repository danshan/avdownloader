package com.shanhh.av.web.controller.ajax;

import com.alibaba.fastjson.JSON;
import com.shanhh.av.web.bean.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.MissingFormatArgumentException;

/**
 * @author dan.shan
 * @since 2014-11-28 11:16 PM
 */
public abstract class AjaxController {

    private static final Logger LOG = LoggerFactory.getLogger(AjaxController.class);

    @ExceptionHandler
    @ResponseBody
    public void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        LOG.warn("Exception: {}, when call PATH {}", ex.getMessage(), request.getPathInfo());
        String callback = request.getParameter("callback");
        ResultBean resultBean;
        if (ex instanceof IllegalArgumentException || ex instanceof MissingServletRequestParameterException) {
            resultBean = new ResultBean(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage(), "");
        } else {
            resultBean = new ResultBean(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage(), "");
        }
        try {
            response.getWriter().write(jsonpBuilder(callback, resultBean));
        } catch (IOException e) {
            LOG.error("ExceptionHandler occurs {}", e.getMessage());
        }
    }

    public String jsonpBuilder(String callback, ResultBean resultBean) {
        if (StringUtils.isEmpty(callback)) {
            return JSON.toJSONString(resultBean);
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(callback).append("(").append(JSON.toJSONString(resultBean)).append(")").toString();
    }
}
