package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Test;

import java.io.IOException;


/**
 * Created by Jimmy Heller on 9/28/2018.
 */
public class PackerTest {

    @Test(expected = APIException.class)
    public void testFileNotExist() throws APIException {
        String filePath = "";
        Packer.validateInputFile(filePath);
    }

    @Test(expected = APIException.class)
    public void testFileHasEmptyLine() throws APIException, IOException {
        String filePath = "test1.txt";
        Packer.pack(filePath);
    }
    
}
