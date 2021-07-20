package io.boncray.example.flow.param;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/7/20 17:16
 */
@Data
public class TranslateParam {

    private String userName;

    private String nickName;

    private List<Long> userIds;

    private Map<String, String> map;


}
