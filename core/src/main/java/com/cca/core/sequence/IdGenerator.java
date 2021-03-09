package com.cca.core.sequence;

/**
 * Id主键生成接口.
 *
 * @author buer
 * @version 2017-08-21 18:24 1.0
 */
public interface IdGenerator {

  /**
   * 下一个唯一id.
   *
   * @return long类型id
   */
  long next();
}
