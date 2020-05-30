package org.sbx.core.oss.component;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.SneakyThrows;
import org.sbx.core.oss.component.properties.SbxOssProperties;
import org.sbx.core.oss.model.OssFile;
import org.sbx.core.tool.util.date.DateStyle;
import org.sbx.core.tool.util.date.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/5/4
 */
@Component
public class AliOssHelper {

    @Resource
    private OSS ossClient;

    @Resource
    private SbxOssProperties sbxOssProperties;


    /**
     * 临时授权
     * @param durationSeconds 有效时间 秒
     * @return
     */
    public AssumeRoleResponse.Credentials sts(long durationSeconds){
        String endpoint = "sts.aliyuncs.com";
        try {
            // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
            DefaultProfile.addEndpoint("", "Sts", endpoint);
            // 构造default profile（参数留空，无需添加region ID）
            IClientProfile profile = DefaultProfile.getProfile("", sbxOssProperties.getAccessKeyId(), sbxOssProperties.getAccessKeySecret());
            // 用profile构造client
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setRoleArn(sbxOssProperties.getRoleArn());
            request.setRoleSessionName(sbxOssProperties.getRoleSessionName());
            request.setPolicy(null);
            request.setDurationSeconds(durationSeconds);
            AssumeRoleResponse response = client.getAcsResponse(request);
            return response.getCredentials();
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
        return null;
    }

    @SneakyThrows
    public OssFile putFile(MultipartFile file){
        String objectName = this.getKey(file.getOriginalFilename());
        ossClient.putObject(sbxOssProperties.getBucketName(),objectName,file.getInputStream());
        OssFile ossFile = new OssFile();
        ossFile.setFileName(file.getOriginalFilename());
        ossFile.setFileUrl(sbxOssProperties.getBucketName()+"."+sbxOssProperties.getEndpoint()+"/"+objectName);
        ossFile.setObjectName(objectName);
        return ossFile;
    }

    /**
     * 获取访问地址
     * @param objectName
     * @param durationSeconds 有效时间，秒
     * @return
     */
    public String visitUrl(String objectName,long durationSeconds){
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + durationSeconds * 1000);
        // 生成PUT方式的签名URL。
        URL signedUrl = ossClient.generatePresignedUrl(sbxOssProperties.getBucketName(), objectName, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl.toString();
    }


    /**
     * 删除阿里云文件
     * @param objectName
     */
    public void deleteFile(String objectName){
        // 删除文件。
        ossClient.deleteObject(sbxOssProperties.getBucketName(), objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }




    /**
     * 根据上传的文件名生成key
     * @param fileName
     * @return
     */
    private String getKey(String fileName){
        String id = UUID.randomUUID().toString().replace("-","");
        //获取文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String realName=String.format("%s.%s", new Object[]{id, suffix});
        return org.springframework.util.StringUtils.cleanPath(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_EN))+"/"+realName;
    }


}
