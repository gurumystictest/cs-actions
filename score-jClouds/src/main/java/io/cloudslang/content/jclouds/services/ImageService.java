package io.cloudslang.content.jclouds.services;

import org.jclouds.ec2.domain.Image;

import java.util.Set;

/**
 * Created by Mihai Tusa.
 * 5/4/2016.
 */
public interface ImageService {
    String createImageInRegion(String region, String name, String serverId, String imageDescription, boolean imageNoReboot)
            throws Exception;

    String deregisterImageInRegion(String region, String imageId) throws Exception;

    Set<? extends Image> describeImagesInRegion(String region, String identityId, String[] imageIds, String[] owners)
            throws Exception;
}