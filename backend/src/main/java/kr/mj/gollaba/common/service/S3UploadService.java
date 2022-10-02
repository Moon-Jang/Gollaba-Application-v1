package kr.mj.gollaba.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class S3UploadService {

    private final String bucket;
    private final AmazonS3 amazonS3;

    public S3UploadService(@Value("${cloud.aws.s3.bucket}") String bucket, AmazonS3 amazonS3) {
        this.bucket = bucket;
        this.amazonS3 = amazonS3;
    }

    public String upload(String filePath, String fileName, MultipartFile multipartFile) {
        ObjectMetadata objMeta = new ObjectMetadata();
        InputStream inputStream;
        int contentLength;

        try {
            inputStream = multipartFile.getInputStream();
            contentLength = inputStream.available();
        } catch (IOException e) {
            throw new GollabaException(GollabaErrorCode.IO_EXCEPTION);
        }

        objMeta.setContentLength(contentLength);
        amazonS3.putObject(bucket, filePath + "/" + fileName, inputStream, objMeta);

        return amazonS3.getUrl(bucket, filePath + "/" + fileName).toString();
    }

    public String generateFileName(long id, String contentType) {
        return id + "_" + UUID.randomUUID() + "." + contentType.replace("image/", "");
    }
}
