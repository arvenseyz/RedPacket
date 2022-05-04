package com.yangzhuo.redpacket.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 * @author 渣小宇
 */
@Controller
public class StationController {
    @RequestMapping("/station")
    public String station(){

        return "grabRedPacket";
    }
}
