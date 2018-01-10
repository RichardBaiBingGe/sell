package com.core.utils;

import com.core.VO.ResultVO;

/**
 * Created by Richard on 2017/12/4.
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setDate(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer errorCode,String  msg){

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(errorCode);
        resultVO.setMessage(msg);
        return resultVO;
    }


}
