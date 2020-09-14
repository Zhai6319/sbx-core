package com.sbx.core.tool.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.sbx.core.tool.excel.listener.GeneralTreatmentListener;
import com.sbx.core.tool.excel.processor.AbstractGeneralTreatmentProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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
     * @param response    请求响应
     * @param fileName    文件名称
     * @param sheetName   sheet name
     * @param rowDataList excel数据
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, String sheetName,
                                   List<T> rowDataList, Class<T> clz) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), clz)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet(sheetName)
                    .doWrite(rowDataList);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = Maps.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 上传数据,sheet 第一页,默认分片10条处理
     * @param excel     excel文件
     * @param processor 上传处理器
     * @param classes   数据类
     * @param <T>       自定义处理器泛型
     * @throws IOException  流异常
     */
    public static <T ,S extends AbstractGeneralTreatmentProcessor<T>> void uploadData(MultipartFile excel, S processor, Class<T> classes) throws IOException {
        EasyExcel.read(excel.getInputStream(),classes,new GeneralTreatmentListener<>(processor)).sheet().doRead();
    }

    /**
     * 上传数据,sheet 第一页,默认分片10条处理
     * @param excel     excel文件
     * @param processor 上传处理器
     * @param classes   数据类
     * @param batchCount 分片批处理数量 小于5条按5条分片
     * @param <T>       自定义处理器泛型
     * @throws IOException  流异常
     */
    public static <T ,S extends AbstractGeneralTreatmentProcessor<T>> void uploadData(MultipartFile excel, S processor, int batchCount, Class<T> classes) throws IOException {
        EasyExcel.read(excel.getInputStream(),classes, new GeneralTreatmentListener<>(processor, batchCount)).sheet().doRead();
    }

}
