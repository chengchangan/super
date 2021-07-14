package io.boncray.core.monitor;

import com.alibaba.fastjson.JSON;
import io.boncray.core.monitor.pojo.*;
import org.springframework.util.CollectionUtils;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cca
 * @version 1.0
 * @date 2021/5/31 15:14
 */
public class SystemMonitor {
    static SystemInfo systemInfo = new SystemInfo();


    public static void main(String[] args) throws Exception {
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CpuInfo cpuInfo = cpuInfo(hardware.getProcessor());
        System.out.println("===========cpu===========");
        System.out.println(JSON.toJSONString(cpuInfo));

        List<DiskInfo> diskInfos = diskInfo(hardware.getDiskStores());
        System.out.println("===========disk===========");
        System.out.println(JSON.toJSONString(diskInfos));

        MemoryInfo memoryInfo = memoryInfo(hardware.getMemory());
        System.out.println("===========memory===========");
        System.out.println(JSON.toJSONString(memoryInfo));

        ProcessInfo processInfo = processInfo(systemInfo);
        System.out.println("===========process===========");
        System.out.println(JSON.toJSONString(processInfo));

        JvmInfo jvmInfo = jvmInfo();
        System.out.println("===========jvm===========");
        System.out.println(JSON.toJSONString(jvmInfo));
    }

    public static JvmInfo jvmInfo() {
        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //jvm最大可申请
        long jvmMaxMoryByte = runtime.maxMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        JvmInfo info = new JvmInfo();
        info.setJvmVersion(jdkVersion);
        info.setTotal(FormatUtil.formatBytes(jvmTotalMemoryByte));
        info.setRemain(FormatUtil.formatBytes(freeMemoryByte));
        info.setUsage(FormatUtil.formatBytes(jvmTotalMemoryByte - freeMemoryByte));
        info.setUsageRate(new DecimalFormat("#.##%").format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        info.setJvmMax(FormatUtil.formatBytes(jvmMaxMoryByte));
        return info;
    }

    private static ProcessInfo processInfo(SystemInfo systemInfo) {
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

        ProcessInfo processInfo = new ProcessInfo();
        int processCount = operatingSystem.getProcessCount();
        int threadCount = operatingSystem.getThreadCount();
        processInfo.setProcessCount(processCount);
        processInfo.setThreadCount(threadCount);

        List<OSProcess> memTopProcesses = operatingSystem.getProcesses(OperatingSystem.ProcessFiltering.ALL_PROCESSES, OperatingSystem.ProcessSorting.RSS_DESC, 10);
        processInfo.setMemoryTop10ProcessList(buildProcessInfo(memTopProcesses, hardware));
        List<OSProcess> cpuTopProcesses = operatingSystem.getProcesses(OperatingSystem.ProcessFiltering.ALL_PROCESSES, OperatingSystem.ProcessSorting.CPU_DESC, 10);
        processInfo.setCpuTop10ProcessList(buildProcessInfo(cpuTopProcesses, hardware));
        return processInfo;
    }

    private static List<ProcessInfo.SingleProcessInfo> buildProcessInfo(List<OSProcess> processList, HardwareAbstractionLayer hardware) {
        List<ProcessInfo.SingleProcessInfo> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(processList)) {
            for (OSProcess process : processList) {
                ProcessInfo.SingleProcessInfo info = new ProcessInfo.SingleProcessInfo();
                info.setPid(process.getProcessID());
                info.setProcessName(process.getName());
                info.setCpuUsageRate(new DecimalFormat("#.##%").format(100d * (process.getKernelTime() + process.getUserTime()) / process.getUpTime()));
                info.setMemUsageRate(new DecimalFormat("#.##%").format(100d * process.getResidentSetSize() / hardware.getMemory().getTotal()));
                //所有的虚拟内存
                info.setVsz(FormatUtil.formatBytes(process.getVirtualSize()));
                // 实际驻留在内存中的大小
                info.setRss(FormatUtil.formatBytes(process.getResidentSetSize()));
                list.add(info);
            }
        }
        return list;
    }


    public static MemoryInfo memoryInfo(GlobalMemory memory) {
        long totalByte = memory.getTotal();
        long acaliableByte = memory.getAvailable();

        MemoryInfo info = new MemoryInfo();
        info.setUsage(FormatUtil.formatBytes(totalByte - acaliableByte));
        info.setRemain(FormatUtil.formatBytes(acaliableByte));
        info.setTotal(FormatUtil.formatBytes(totalByte));
        info.setUsageRate(new DecimalFormat("#.##%").format((totalByte - acaliableByte) * 1.0 / totalByte));
        return info;
    }

    private static List<DiskInfo> diskInfo(List<HWDiskStore> diskStores) {
        File[] disks = File.listRoots();

        Map<String, File> fileMap = new HashMap<>();
        for (File disk : disks) {
            fileMap.put(disk.toString(), disk);
        }
        List<DiskInfo> list = new ArrayList<>();
        for (HWDiskStore disk : diskStores) {
            DiskInfo info = new DiskInfo();
            info.setName(disk.getName().replace("\\", "").trim());
            info.setMode(disk.getModel().trim());
            info.setSerial(disk.getSerial().trim());
            info.setTotal(FormatUtil.formatBytesDecimal(disk.getSize()));
            info.setReads(disk.getReads());
            info.setReadCount(FormatUtil.formatBytes(disk.getReadBytes()));
            info.setWrites(disk.getWrites());
            info.setWriteCount(FormatUtil.formatBytes(disk.getWriteBytes()));
            info.setTransferTime(disk.getTransferTime());
            if (!CollectionUtils.isEmpty(disk.getPartitions())) {
                ArrayList<DiskInfo.Partition> partitions = new ArrayList<>();
                for (HWPartition partition : disk.getPartitions()) {
                    File file = fileMap.get(partition.getMountPoint());
                    DiskInfo.Partition part = new DiskInfo.Partition();
                    part.setName(partition.getName());
                    part.setMountPoint(partition.getMountPoint().replace("\\", "").trim());
                    part.setSize(FormatUtil.formatBytes(partition.getSize()));
                    part.setType(partition.getType());
                    if (file != null) {
                        part.setCanUseSpace(FormatUtil.formatBytes(file.getUsableSpace()));
                        part.setUsedSpace(FormatUtil.formatBytes(partition.getSize() - file.getUsableSpace()));
                    }
                    partitions.add(part);
                }
                info.setPartitions(partitions);
            }
            list.add(info);
        }
        return list;
    }


    private static CpuInfo cpuInfo(CentralProcessor processor) throws InterruptedException {
        CentralProcessor.ProcessorIdentifier processorIdentifier = processor.getProcessorIdentifier();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        TimeUnit.SECONDS.sleep(1);
        long[] ticks = processor.getSystemCpuLoadTicks();

        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setCoreNum(processor.getLogicalProcessorCount());
        cpuInfo.setCpuFrequency(FormatUtil.formatHertz(processorIdentifier.getVendorFreq()));
        cpuInfo.setCpuName(processorIdentifier.getName());
        DecimalFormat decimalFormat = new DecimalFormat("#.##%");
        cpuInfo.setSystemUsageRate(decimalFormat.format(cSys * 1.0 / totalCpu));
        cpuInfo.setUserUsageRate(decimalFormat.format(user * 1.0 / totalCpu));
        cpuInfo.setIoWaitRate(decimalFormat.format(iowait * 1.0 / totalCpu));
        cpuInfo.setCurrentUsageRate(decimalFormat.format(1.0 - (idle * 1.0 / totalCpu)));
        return cpuInfo;
    }


}
