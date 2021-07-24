package com.sbx.core.tool.util;

import com.alibaba.excel.EasyExcel;
import com.sbx.core.tool.excel.BaseExcelModel;
import com.sbx.core.tool.excel.CustomCellWriteHandler;
import com.sbx.core.tool.excel.listener.GeneralTreatmentListener;
import com.sbx.core.tool.excel.processor.AbstractGeneralTreatmentProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/9/11
 */
@Slf4j
public class ExcelUtil {


    /**
     * @param response
     * @param fileName    文件名称
     * @param sheetName   sheet name
     * @param rowDataList excel数据
     */
    public static void exportExcel(HttpServletResponse response, String fileName, String sheetName,
                                   List<? extends BaseExcelModel> rowDataList, Class clz) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), clz).autoCloseStream(Boolean.FALSE).head(clz)
                    .registerWriteHandler(new CustomCellWriteHandler()).sheet(sheetName).doWrite(rowDataList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            log.error("导出excel失败{}", e);
            throw new RuntimeException("导出execl异常");
        }
    }

    /**
     * @param response
     * @param fileName    文件名称
     * @param sheetName   sheet name
     * @param rowDataList excel数据
     */
    public static void exportExcel(HttpServletResponse response, String fileName, String sheetName,
                                   List<? extends BaseExcelModel> rowDataList, Class<?> clz,List<List<String>> head) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream()).autoCloseStream(Boolean.FALSE).head(head)
                    .registerWriteHandler(new CustomCellWriteHandler()).sheet(sheetName).doWrite(rowDataList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            log.error("导出excel失败{}", e);
            throw new RuntimeException("导出execl异常");
        }
    }

    /**
     * 上传数据,sheet 第一页,默认分片10条处理
     * @param is        输入流
     * @param processor 上传处理器
     * @param classes   数据类
     * @param <T>       自定义处理器泛型
     * @throws IOException  流异常
     */
    public static <T ,S extends AbstractGeneralTreatmentProcessor<T>> void uploadData(InputStream is, S processor, Class<T> classes) throws IOException {
        EasyExcel.read(is,classes,new GeneralTreatmentListener<>(processor)).sheet().doRead();
    }

    /**
     * 上传数据,sheet 第一页,默认分片10条处理
     * @param is        输入流
     * @param processor 上传处理器
     * @param classes   数据类
     * @param batchCount 分片批处理数量 小于5条按5条分片
     * @param <T>       自定义处理器泛型
     * @throws IOException  流异常
     */
    public static <T ,S extends AbstractGeneralTreatmentProcessor<T>> void uploadData(InputStream is, S processor, int batchCount, Class<T> classes) throws IOException {
        EasyExcel.read(is,classes, new GeneralTreatmentListener<>(processor, batchCount)).sheet().doRead();
    }

}
