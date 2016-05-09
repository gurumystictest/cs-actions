package io.cloudslang.content.jclouds.execute.images;

import io.cloudslang.content.jclouds.entities.constants.Constants;
import io.cloudslang.content.jclouds.entities.inputs.CommonInputs;
import io.cloudslang.content.jclouds.entities.inputs.CustomInputs;
import io.cloudslang.content.jclouds.factory.ImageFactory;
import io.cloudslang.content.jclouds.services.ImageService;
import io.cloudslang.content.jclouds.utils.InputsUtil;
import io.cloudslang.content.jclouds.utils.OutputsUtil;
import org.jclouds.ec2.domain.Image;

import java.util.Map;
import java.util.Set;

/**
 * Created by Mihai Tusa.
 * 5/6/2016.
 */
public class DescribeImagesInRegionExecutor {
    public Map<String, String> execute(CommonInputs commonInputs, CustomInputs customInputs) throws Exception {
        ImageService imageService = ImageFactory.getImageService(commonInputs);

        String[] imageIds = InputsUtil.getStringsArray(customInputs.getImageIdsString(), Constants.Miscellaneous.EMPTY,
                commonInputs.getDelimiter());
        String[] owners = InputsUtil.getStringsArray(customInputs.getOwnersString(), Constants.Miscellaneous.EMPTY,
                commonInputs.getDelimiter());

        Set<? extends Image> response = imageService.describeImagesInRegion(customInputs.getRegion(),
                customInputs.getIdentityId(), imageIds, owners);

        return OutputsUtil.getResultsMap(response.toString());
    }
}