package com.exm;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;


/**
 * Created by Anna Kuranda on 6/16/2017.
 */
public class FileUtil {


    public List<String> readFileFromClasspath(final String fileName)  {



           List<String>result = Lists.newArrayList();

            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());

            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(!StringUtils.isEmpty(line)){
                        result.add(line);
                    }
                }

                scanner.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;

    }





}
