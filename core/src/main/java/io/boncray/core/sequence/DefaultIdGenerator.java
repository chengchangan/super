package io.boncray.core.sequence;

import java.text.MessageFormat;


/**
 * 默认ID生成器
 *
 * @author Administrator
 */
public class DefaultIdGenerator implements IdGenerator {

  private static final long TWEPOCH = 687888001020L;
  private final long workerId;
  private long sequence = 0L;
  private final int workerIdBits = 4;
  private long maxWorkerId = -1L ^ -1L << workerIdBits;
  private final int sequenceBits = 10;
  private int workerIdShift = sequenceBits;
  private int timestampLeftShift = sequenceBits + workerIdBits;
  private long sequenceMask = -1L ^ -1L << sequenceBits;
  private long lastTimestamp = -1L;
  /**
   * 异常时间差（回流时间大于5秒）.
   */
  private static final long ERROR_TIME_DIFFERENCE = 5000;

  /**
   * 默认构造器，workerid为1.
   */
  public DefaultIdGenerator() throws Exception {
    this(1L);
  }

  /**
   * 构造器.
   *
   * @param workerId 机器id
   */
  public DefaultIdGenerator(long workerId) {
    if (workerId > maxWorkerId || workerId < 0) {
      throw new RuntimeException(
          String.format("worker Id can't be greater than %s or less than 0 ", workerId));
    }
    this.workerId = workerId;
  }

  private long tillNextMillis(long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  private long timeGen() {
    return System.currentTimeMillis();
  }

  @Override
  public synchronized long next() {
    long timestamp = timeGen();
    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & sequenceMask;
      if (sequence == 0) {
        timestamp = tillNextMillis(lastTimestamp);
      }
    } else if (lastTimestamp > timestamp) {
      long timeDifference = lastTimestamp - timestamp;
      if (timeDifference > ERROR_TIME_DIFFERENCE) {
        throw new RuntimeException(MessageFormat
            .format("Clock moved backwards.  Refusing to generate id for {0} milliseconds",
                timeDifference));
      } else {
        try {
          this.wait(timeDifference);
        } catch (InterruptedException e) {
          throw new RuntimeException("Thread InterruptedException occurred during generate id", e);
        }
        this.next();
      }
    } else {
      sequence = 0;
    }
    lastTimestamp = timestamp;
    return (timestamp - TWEPOCH << timestampLeftShift) | workerId << workerIdShift | sequence;
  }
}
