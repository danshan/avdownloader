package com.shanhh.av.web.bean;

import lombok.Data;
import org.apache.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author harry.chen
 * @since 13-12-6
 */
@Data
public class ResultBean implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public ResultBean() {
    }

    public ResultBean(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * return result bean will fields incorrect message.
     *
     * @param result
     * @return
     */
    public ResultBean generateBadRequestResult(BindingResult result) {
        List<String> errorFields = new ArrayList<String>(result.getFieldErrorCount());
        for (FieldError error : result.getFieldErrors()) {
            errorFields.add(error.getField());
        }
        this.setData(errorFields);
        this.setMsg("one or more fields format incorrect");
        this.setCode(HttpStatus.SC_BAD_REQUEST);
        return this;
    }

}
