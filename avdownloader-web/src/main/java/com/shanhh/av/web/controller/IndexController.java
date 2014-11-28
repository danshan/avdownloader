package com.shanhh.av.web.controller;

import com.shanhh.av.service.serial.SerialName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dan.shan
 * @since 2014-11-28 8:25 PM
 */
@Controller
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("serialNames", getSerialNames());

        return "index";
    }

    private List<String> getSerialNames() {
        List<String> serialNames = new ArrayList<String>();
        for (SerialName serialName : SerialName.values()) {
            serialNames.add(serialName.name());
        }
        return serialNames;
    }

}
