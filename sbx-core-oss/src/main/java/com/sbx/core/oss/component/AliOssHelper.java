package com.sbx.core.oss.component;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.ServiceSignature;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sbx.core.model.exception.CustomException;
import com.sbx.core.oss.component.properties.AliOssProperties;
import com.sbx.core.oss.model.OssFile;
import com.sbx.core.oss.model.Signature;
import com.aliyun.oss.common.utils.DateUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import static com.aliyun.oss.internal.OSSConstants.DEFAULT_CHARSET_NAME;

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
    private OSSClient ossClient;

    @Resource
    private AliOssProperties sbxOssProperties;


    /**
     * 临时授权
     * @param durationSeconds 有效时间 秒
     * @return  返回sts授权数据
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
    public OssFile putFile(String originalFilename, InputStream inputStream){
        String objectName = this.getKey(originalFilename);
        ossClient.putObject(sbxOssProperties.getBucketName(),objectName,inputStream);
        OssFile ossFile = new OssFile();
        ossFile.setFileName(originalFilename);
        ossFile.setFileUrl(sbxOssProperties.getBucketName()+"."+sbxOssProperties.getEndpoint()+"/"+objectName);
        ossFile.setObjectName(objectName);
        return ossFile;
    }

    public Signature computeSignature(String postPolicy, String secretAccessKey){
        try {
            byte[] binaryData = postPolicy.getBytes(DEFAULT_CHARSET_NAME);
            String encPolicy = BinaryUtil.toBase64String(binaryData);
            String signature = ServiceSignature.create().computeSignature(secretAccessKey,encPolicy);
            return new Signature(signature,encPolicy);
        } catch (UnsupportedEncodingException ex) {
            throw new CustomException("Unsupported charset: " + ex.getMessage());
        }
    }

    public String generatePostPolicy(Date expiration, PolicyConditions conds) {
        String formatedExpiration = DateUtil.formatIso8601Date(expiration);
        String jsonizedExpiration = String.format("\"expiration\":\"%s\"", formatedExpiration);
        String jsonizedConds = conds.jsonize();
        return String.format("{%s,%s}", jsonizedExpiration, jsonizedConds);
    }

    /**
     * 获取访问地址
     * @param objectName    oss 对象name
     * @param durationSeconds 有效时间，秒
     * @return  返回有效访问路径
     */
    public String visitUrl(String objectName,long durationSeconds){
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + durationSeconds * 1000);
        // 生成PUT方式的签名URL。
        URL signedUrl = ossClient.generatePresignedUrl(sbxOssProperties.getBucketName(), objectName, expiration);
        // 关闭OSSClient。
        return signedUrl.toString();
    }


    /**
     * 删除阿里云文件
     * @param objectName    对象名称
     */
    public void deleteFile(String objectName){
        // 删除文件 filename。

        ossClient.deleteObject(sbxOssProperties.getBucketName(), objectName);
        // 关闭OSSClient。
    }




    /**
     * 根据上传的文件名生成key
     * @param fileName  文件名称
     * @return  返回文件名称
     */
    public String getKey(String fileName){
        String id = UUID.randomUUID().toString().replace("-","");
        //获取文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String realName=String.format("%s.%s", id, suffix);
        return org.springframework.util.StringUtils.cleanPath(DateFormatUtils.format(new Date(),"yyyy/MM/dd"))+"/"+realName;
    }


}
