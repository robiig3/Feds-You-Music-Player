package com.example.fedsyoubackend.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {

    private final AmazonS3 space;

    public StorageService(){

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials("UUS5YVBFPIE4LEZYTP5I", "tiCa/8tDA+owvIiKgHFMN3KFPjjfj4LtLFUR/Mo+8Sg")
        );

        space = AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("sfo3.digitaloceanspaces.com", "sfo3")
                )
                .build();
    }

    public List<String> getSongsFileName(){

        ListObjectsV2Result result = space.listObjectsV2("fedsyou");
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        return objects.stream()
                .map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    public void uploadSong(MultipartFile file) throws IOException {

        // we dont use try because we throwed in the signature of the method with import
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        space.putObject(new PutObjectRequest("fedsyou", file.getOriginalFilename(), file.getInputStream(), objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // the last thing is to transform from private to public for digitalocean

    }

    public void deleteSong(String filename){
        space.deleteObject(new DeleteObjectRequest("fedsyou", filename));
    }

}
