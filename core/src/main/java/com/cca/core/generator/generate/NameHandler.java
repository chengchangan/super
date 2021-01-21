package com.cca.core.generator.generate;


import com.cca.core.generator.domain.Configuration;
import com.cca.core.generator.domain.Table;

/**
 * @author cca
 * @version 1.0
 * @date 2021/1/18 11:19
 */
public interface NameHandler {


    void processTableToClass(Configuration configuration, Table table);

}
