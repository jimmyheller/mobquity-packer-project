package com.mobiquityinc.packer;


import com.mobiquityinc.domain.ContainerPackage;
import com.mobiquityinc.domain.Item;
import com.mobiquityinc.exception.APIException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jimmy Heller on 9/27/2018.
 */
public class Packer {
    final static Logger logger = Logger.getLogger(Packer.class);

    public static void main(String[] args) {
        String inputFile;
        if (args != null && args.length > 0) { //reading file path from the app args
            inputFile = args[0];
        } else {
            inputFile = "sample-input.txt"; // default file for process
        }

        try {
            pack(inputFile);
        } catch (APIException q) {
            logger.error("there is an error in processing the file", q);
        } catch (IOException q) {
            logger.error("your input file is not exist or its name is not correct", q);
        } catch (Exception q) {
            logger.error("unknown error has happened", q);


        }

    }


    protected static String pack(String inputFile) throws APIException, IOException {
        StringBuilder packageResponse = new StringBuilder();
        validateInputFile(inputFile);

        List<String> inputLinesList = readFile(inputFile);
        List<ContainerPackage> packages = makeItemCollection(inputLinesList);

        for (ContainerPackage pack : packages) {
            if (pack.getItems().size() < 1) {
                packageResponse.append("-\n");
                continue;
            } else if (pack.getItems().size() == 1) {
                packageResponse.append(pack.getItems().get(0).getIndex().toString() + "\n");
                continue;
            }
            //this method of sortin is the fastest way to make them ordered by price and weight
            Collections.sort(pack.getItems());
            packageResponse = packageResponse.append(pack.getItems().get(0).getIndex().toString());
            Float packageWeight = pack.getItems().get(0).getWeight();
            for (int i = 1; i < pack.getItems().size(); i++) {
                Item temp = pack.getItems().get(i);
                if (packageWeight + temp.getWeight() <= pack.getMaxWeight()) {
                    packageWeight += packageWeight + temp.getWeight();
                    packageResponse.append(",").append(temp.getIndex());
                }
            }
            packageResponse.append("\n");
        }

        logger.info("final response is:\n" + packageResponse);
        return String.valueOf(packageResponse);

    }

    private static List<ContainerPackage> makeItemCollection(List<String> inputLinesList) throws APIException {
        logger.info("starting to create item collection list");
        //we need to make an ordered list based on reading the file, so linkedList is going to meet the needs
        List<ContainerPackage> packages = new LinkedList<ContainerPackage>();
        for (String line : inputLinesList) {
            logger.info("working on line : " + line);

            ContainerPackage containerPackage = new ContainerPackage();

            Float maxWeight = Float.valueOf(line.substring(0, line.indexOf(":")).trim());
            logger.info("max weight of the line is : " + maxWeight);
            containerPackage.setMaxWeight(maxWeight);

            String items = line.substring(line.indexOf(":") + 1);

            while (items.length() != 0 && items.contains("(")) {
                logger.info("searching through the line for item...");

                String item = items.substring(items.indexOf("(") + 1, items.indexOf(")"));
                String[] itemParams = item.split(",");
                Item tempItem = new Item(itemParams[0].trim(), itemParams[1].trim(), itemParams[2].trim());
                logger.info("found item: " + item.toString());
                items = items.substring(items.indexOf(")") + 1);
                //validating the items in the list here in order to not traverse the list one more time
                if (tempItem.getWeight() > maxWeight || tempItem.getPrice() > 100 || tempItem.getWeight() > 100) {
                    logger.info("item is not valid. moving to next item.");
                    continue;
                }
                containerPackage.getItems().add(tempItem);
            }

            logger.info("adding package: " + containerPackage + " to list of packages. ");
            packages.add(containerPackage);

        }
        return packages;
    }

    private static List<String> readFile(String inputFile) throws APIException, IOException {
        List<String> inputs = new LinkedList<String>();
        File file = new File(inputFile);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        logger.info("reading input file lines:");
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("")) {
                throw new APIException("file has empty line");
            }
            logger.info(line);
            inputs.add(line.replace((char) 65533, ' '));//removing currency sign
        }
        fileReader.close();
        return inputs;
    }


    protected static void validateInputFile(String inputFile) throws APIException {
        File input = new File(inputFile);
        logger.info("validating input file :" + input.getAbsolutePath());
        if (!input.exists()) {
            logger.error("input file does not exist,closing the application");
            throw new APIException();
        }
        logger.info("input file has passed the validation.");

    }

}
