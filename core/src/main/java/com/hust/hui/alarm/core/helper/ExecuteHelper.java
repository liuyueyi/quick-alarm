package com.hust.hui.alarm.core.helper;

import com.hust.hui.alarm.core.execut.spi.NoneExecute;
import com.hust.hui.alarm.core.execut.api.IExecute;
import com.hust.hui.alarm.core.execut.SimpleExecuteFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * Created by yihui on 2017/5/12.
 */
@Getter
@Setter
public class ExecuteHelper {

    public static ExecuteHelper DEFAULT_EXECUTE = new ExecuteHelper(SimpleExecuteFactory.getExecute(NoneExecute.NAME), Collections.emptyList());

    private IExecute iExecute;

    private List<String> users;

    public ExecuteHelper(IExecute iExecute, List<String> users) {
        this.iExecute = iExecute;
        this.users = users;
    }


}
