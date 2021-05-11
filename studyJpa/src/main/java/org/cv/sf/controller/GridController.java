package org.cv.sf.controller;

import org.cv.sf.dto.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "grid")
public class GridController {

    @RequestMapping(value = "get")
    public ResponseVo getGrid(){
        List<Map<String,String>> gridList = new ArrayList<>();
        for(int i=0;i<6;i++){
            Map<String,String> map = new HashMap<>();
                map.put("image","http://bpic.588ku.com/element_origin_min_pic/01/61/80/755748dea10cbcb.jpg");
            map.put("text","分类"+i);
            gridList.add(map);
        }
        return new ResponseVo().success(gridList);
    }
}
