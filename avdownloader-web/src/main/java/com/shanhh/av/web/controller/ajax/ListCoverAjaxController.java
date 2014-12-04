package com.shanhh.av.web.controller.ajax;

import com.shanhh.av.service.serial.Serial;
import com.shanhh.av.service.serial.SerialFactory;
import com.shanhh.av.service.serial.SerialName;
import com.shanhh.av.web.bean.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * @author dan.shan
 * @since 2014-11-28 11:11 PM
 */
@Controller
@RequestMapping("ajax/serial")
public class ListCoverAjaxController extends AjaxController {

    @Autowired
    private SerialFactory serialFactory;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean list(HttpServletRequest request, @RequestParam("serialName") String name) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(200);

        SerialName serialName = SerialName.valueOf(name);
        Serial serial = serialFactory.getSerial(serialName);
        Collection<String> serialIds = serial.getSerialIdPack();

        return resultBean;
    }

}
