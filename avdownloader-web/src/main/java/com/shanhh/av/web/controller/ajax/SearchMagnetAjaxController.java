package com.shanhh.av.web.controller.ajax;

import com.shanhh.av.service.bean.MagnetItem;
import com.shanhh.av.service.service.MagnetService;
import com.shanhh.av.web.bean.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dan.shan
 * @since 2014-11-28 11:11 PM
 */
@Controller
@RequestMapping("ajax/search")
public class SearchMagnetAjaxController extends AjaxController {

    @Autowired
    private MagnetService magnetService;

    @RequestMapping(value = "magnet", method = RequestMethod.GET)
    @ResponseBody
    public ResultBean list(HttpServletRequest request, @RequestParam("keyword") String keyword) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(200);

        List<MagnetItem> magnetItems = magnetService.search(keyword);
        resultBean.setData(magnetItems);

        return resultBean;
    }

}
